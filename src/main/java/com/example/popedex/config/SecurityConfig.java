package com.example.popedex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/webjars/htmx.org/1.9.10/dist/htmx.min.js",
                                "/webjars/hyperscript.org/0.9.12/dist/_hyperscript.min.js",
                                "/login", "/users/new", "/users/new/**").permitAll()
                        .requestMatchers("/statues/**", "/picture/**", "/users/**", "/secure", "/secure/**").authenticated()
                        .anyRequest().denyAll())
                .oauth2Login(withDefaults())
                .formLogin(withDefaults())
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }
}