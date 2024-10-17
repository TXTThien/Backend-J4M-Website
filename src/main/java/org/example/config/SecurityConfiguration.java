package org.example.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.example.config.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher.Builder;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


import java.util.ArrayList;
import java.util.List;

import static org.example.entity.enums.Role.*;
import static org.springframework.security.config.http.SessionCreationPolicy.ALWAYS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/payments/viewgiohang/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/resources/**",
            "/j4m/**",
            "/search/**",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {
                    req
                            .requestMatchers("/").permitAll()
                            .requestMatchers(WHITE_LIST_URL).permitAll()
                            .requestMatchers("/api/v1/product/**").permitAll()
                            .requestMatchers("/api/v1/admin/product/**").hasAnyAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/news/**").hasAnyAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/account/**").hasAnyAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/origin/**").hasAnyAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/productsize/**").hasAnyAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/producttype/**").hasAnyAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/review/**").hasAnyAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/size/**").hasAnyAuthority(admin.name())
                            .requestMatchers("/account/**").hasAnyAuthority(user.name())

                            .requestMatchers("/user").hasAnyAuthority(user.name())
                            .requestMatchers("/dashboard").hasAnyAuthority(user.name())
                            .anyRequest().authenticated();
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/j4m", true)
                        .loginProcessingUrl("/login")
                        .failureForwardUrl("/register")
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/j4m")
                );

        return http.build();
    }

}
