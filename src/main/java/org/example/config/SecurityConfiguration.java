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
import static org.springframework.security.config.Customizer.withDefaults;
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
            "/j4m/**"

    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(req -> {
                    req
                            .requestMatchers("/").permitAll()
                            .requestMatchers(WHITE_LIST_URL).permitAll()
                            .requestMatchers("/api/v1/admin/product/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/bill/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/billinfo/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/brand/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/cart/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/category/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/discount/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/image**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/news/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/account/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/origin/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/banner/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/productsize/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/producttype/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/review/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/size/**").hasAuthority(admin.name())
                            .requestMatchers("/account/**").hasAuthority(admin.name())
                            .requestMatchers("/review/**").hasAuthority(admin.name())
                            .requestMatchers("/user").hasAuthority(admin.name())
                            .requestMatchers("/dashboard").hasAuthority(admin.name())
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