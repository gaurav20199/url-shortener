package com.project.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(Customizer.withDefaults()
                ).formLogin(form ->form.
                        loginPage("/login").
                        defaultSuccessUrl("/").
                        permitAll()
                ).authorizeHttpRequests(authorize ->authorize.
                        requestMatchers("/error","/css/*","/js/*","/","/register","/login","/webjars/**").
                        permitAll().
                        requestMatchers("/admin/**").
                        hasRole("ADMIN").
                        anyRequest().authenticated()
                ).build();
    }
}
