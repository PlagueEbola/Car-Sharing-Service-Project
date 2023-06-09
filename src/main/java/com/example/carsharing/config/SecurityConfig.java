package com.example.carsharing.config;

import com.example.carsharing.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/register", "/login",
                                        "/swagger-ui/**", "/v3/api-docs/**",
                                        "/payments/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/cars/**").permitAll()
                                .requestMatchers("/users/me")
                                .hasAnyRole("CUSTOMER", "MANAGER")
                                .requestMatchers(HttpMethod.GET, "/rentals/**")
                                .hasAnyRole("CUSTOMER", "MANAGER")
                                .requestMatchers(HttpMethod.PUT,
                                        "/users/{id}/role",
                                        "/cars/{id}")
                                .hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/cars/{id}")
                                .hasRole("MANAGER")
                                .requestMatchers(HttpMethod.POST,
                                        "/cars",
                                        "/rentals/**")
                                .hasRole("MANAGER")
                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .build();
    }
}
