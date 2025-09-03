package com.pettour.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pettour.api.security.JwtTokenFilter; // Importar a classe do filtro

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenFilter jwtTokenFilter) throws Exception {
    return http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                    // --- REGRAS PÚBLICAS (Qualquer um pode acessar) ---
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/servicos").permitAll()
                    
                    // --- REGRAS DE ADMIN (Apenas quem tem a ROLE_ADMIN) ---
                    .requestMatchers(HttpMethod.POST, "/servicos").hasRole("ADMIN") 
                    .requestMatchers(HttpMethod.PUT, "/servicos/**").hasRole("ADMIN") 
                    .requestMatchers(HttpMethod.DELETE, "/servicos/**").hasRole("ADMIN") 
                    .requestMatchers(HttpMethod.PATCH, "/agendamentos/{id}/concluir").hasRole("ADMIN")
                    
                    // --- REGRAS DE USUÁRIO LOGADO (Qualquer usuário autenticado) ---
                    .anyRequest().authenticated() 
            )
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
