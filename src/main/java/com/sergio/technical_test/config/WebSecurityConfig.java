package com.sergio.technical_test.config;

import com.sergio.technical_test.security.JWTConfigurer;
import com.sergio.technical_test.security.JWTFilter;
import com.sergio.technical_test.security.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurityConfig(TokenProvider tokenProvider, AuthenticationEntryPoint authenticationEntryPoint) {
        this.tokenProvider = tokenProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Proporcionar el AuthenticationManager que gestionará la autenticación basada en los detalles de usuario y contraseña
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.cors(Customizer.withDefaults()) // Permite solicitudes cors desde otros dominios
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("*")); // URL del frontend
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowCredentials(true); // Permitir cookies
                    //config.addExposedHeader("Message");
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    return config;
                }))
                // CSRF no es necesaria en aplicaciones que utilizan tokens JWT para autenticación
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                // Permite el acceso sin autenticación a las rutas de registro y autenticación.
                                .requestMatchers(antMatcher("/auth/login")).permitAll()
                                .requestMatchers(antMatcher("/auth/createPatient")).permitAll()
                                //// Cualquier otra solicitud requiere autenticación (JWT u otra autenticación configurada)
                                .anyRequest().authenticated()
                )
                // Permite la autenticación básica (por ejemplo, para testing con postman)
                // .httpBasic(Customizer.withDefaults())
                // Desactiva el formulario de inicio de sesión predeterminado, ya que se usa JWT
                .formLogin(AbstractHttpConfigurer::disable)
                // Configura le manejo de excepciones para autenticación. Usa JwtAuthenticationEntryPoint para manejar errores 401
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint))
                // Configura la política de sesiones como "sin estado" (stateless),
                // ya que JWT maneja la autenticación, no las sesiones de servidor
                .sessionManagement(h -> h.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Agrega la configuración para JWT en el filtro antes de los filtros predeterminados de Spring Security
                .with(new JWTConfigurer(tokenProvider), Customizer.withDefaults());

        return http.build();
    }
}
