package org.example.config;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.cors.CorsConfiguration;
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
            "/product/**",
            "/j4m/**",
            "/search/**",
            "/detail/**",
            "/homepage/**",
            "/news/**",
            "/info",
            "/cart",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of("http://localhost:8000"));
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            config.setAllowedHeaders(List.of("*"));
                            config.setExposedHeaders(List.of("Authorization"));
                            config.setAllowCredentials(true);
                            config.setMaxAge(3600L);
                            return config;
                        })
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                        })
                )
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
                            .requestMatchers("/api/v1/admin/news/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/account/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/origin/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/productsize/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/producttype/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/review/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/admin/size/**").hasAuthority(admin.name())
                            .requestMatchers("/api/v1/upload/**").hasAuthority(admin.name())
                            .requestMatchers("/account/**").hasAuthority(user.name())
                            .requestMatchers("/review/**").hasAuthority(user.name())
                            .requestMatchers("/addToPrebuy/**").hasAuthority(user.name())
                            .requestMatchers("/prebuy/**").hasAuthority(user.name())
                            .requestMatchers("/payment_info").permitAll()

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

