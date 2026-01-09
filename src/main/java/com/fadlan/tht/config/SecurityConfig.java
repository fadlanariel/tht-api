package com.fadlan.tht.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fadlan.tht.security.JwtAuthenticationFilter;
import com.fadlan.tht.security.SecurityExceptionHandler;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityExceptionHandler securityExceptionHandler;

    public SecurityConfig(
        JwtAuthenticationFilter jwtAuthenticationFilter,
        SecurityExceptionHandler securityExceptionHandler) {
            this.jwtAuthenticationFilter = jwtAuthenticationFilter;
            this.securityExceptionHandler = securityExceptionHandler;
        }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .exceptionHandling(ex -> 
                    ex.authenticationEntryPoint(securityExceptionHandler)
                )

                .authorizeHttpRequests(auth -> auth
                        // Auth endpoints (PUBLIC)
                        .requestMatchers(
                                "/registration",
                                "/login",
                                "/banner")
                        .permitAll()

                        // Swagger (PUBLIC)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                        .permitAll()

                        // Error endpoint
                        .requestMatchers("/error").permitAll()

                        // Others must be authenticated
                        .anyRequest().authenticated())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
