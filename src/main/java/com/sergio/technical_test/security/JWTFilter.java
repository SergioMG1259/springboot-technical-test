package com.sergio.technical_test.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class JWTFilter extends GenericFilterBean {
    private final TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // Extrae el token JWT de la cabecera de autorización HTTP (Authorization Header)
        String bearerToken = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

        // Comprobar que el token no sea nulo o vació, y si empieza con el prefijo "Bearer "
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // Eliminar el prefijo "Bearer " para obtener solo en token
            String token = bearerToken.substring(7);

            if (tokenProvider.validateToken(token)) {
                // Utilizar el token provider para obtener la autenticación a partir del toke JWT
                Authentication authentication = tokenProvider.getAuthentication(token);
                // Establecer la autenticación en el contexto de seguridad de Spring para la solicitud actual
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        // Continuar con la cadena de filtros, permitiendo que la solicitud siga su curso
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
