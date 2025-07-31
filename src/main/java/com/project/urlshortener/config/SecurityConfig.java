package com.project.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(Customizer.withDefaults()
                ).formLogin(form ->form.
                        loginPage("/login").
                        defaultSuccessUrl("/").
                        permitAll()
                ).authorizeHttpRequests(authorize ->authorize.
                        requestMatchers("/error","/css/*","/js/*","/","/register","/login","/webjars/**",
                                "/s/**","/create/**").
                        permitAll().
                        requestMatchers("/admin/**").
                        hasRole("ADMIN").
                        anyRequest().authenticated()
                ).build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        /*
        this is required since when admin will log in by default they will not see my url page(url created by them)
        as that url can be accessible by only ROLE USER. Spring security doesn't know directly that admin is also a user
        that has all user privileges and some extra privileges. Role hierarchy resolves this.
        Instead of this code:
        <li class="nav-item" sec:authorize="isAuthenticated()">
                    <a class="nav-link" href="/user/urls">My URLs</a>
                </li>
         let(s) suppose we have this code
           <li class="nav-item" sec:authorize="hasRole('USER')">
                    <a class="nav-link" href="/user/urls">My URLs</a>
                </li>
        */
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
    }

}
