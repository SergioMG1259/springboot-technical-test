package com.sergio.technical_test.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class TokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.validity-in-seconds}")
    private long jwtValidityInSeconds;

    private Key key;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        // Generar la clave para firmar el JWT a partir del secreto configurado
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        // Inicializar el parser JWT con la clave generada para firmar y validar los tokens
        jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String createAccessToken(Authentication authentication) {
        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow() // TODO: implementar la excepción RoleNotFoundException
                .getAuthority();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts
                .builder()
                .setSubject(userPrincipal.getUsername()) // El sujeto del token es el username
                .claim("role", role) // El rol se incluye como claim en el token
                .claim("userId", userPrincipal.getUserId()) // El id del usuario de incluye como claim en el token
                .claim("email", userPrincipal.getEmail())
                .signWith(key, SignatureAlgorithm.HS512) // Firmar el token con el algoritmo HS512 y la clave
                .setExpiration(new Date(System.currentTimeMillis() + jwtValidityInSeconds * 1000)) // Configurar la fecha de expiración
                .compact();
    }

    public Authentication getAuthentication(String token) {
        // Extrae todos los claims del token JWT
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        String userName = claims.getSubject();
        Long userId = Long.parseLong(claims.get("userId").toString());
        String role = claims.get("role").toString();
        String email = claims.get("email").toString();
        // Lista de autoridades (roles) para el usuario
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        // El principal del contexto de seguridad será el userName (subject) extraído del token
        // UserPrincipal se utiliza para representar al usuario autenticado en lugar del User de Spring Security
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUserId(userId);
        userPrincipal.setUserName(userName);
        userPrincipal.setEmail(email);
        // Crear el objeto de autenticación con los detalles del usuario
        return new UsernamePasswordAuthenticationToken(userPrincipal, token, authorities);
    }

    // Validar tokens JWT:
    // Al recibir una solicitud, el TokenProvider verifica la validez del token JWT.
    // Esto incluye comprobar si el token no ha sido alterado y si no ha expirado.
    // Si el token es válido, se extrae la información de autenticación del usuario del token.
    public boolean validateToken(String token) {
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            // Verificar si el token ha expirado
            Date expirationDate = claims.getExpiration();
            return !expirationDate.before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }
}
