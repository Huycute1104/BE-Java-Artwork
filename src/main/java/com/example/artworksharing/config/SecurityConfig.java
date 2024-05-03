package com.example.artworksharing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.example.artworksharing.enums.Permission.*;
import static com.example.artworksharing.enums.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configure(http))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authentication -> authentication
                        .requestMatchers("/api/v1/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
//                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html")
                        .permitAll()
                        .requestMatchers("/api/v1/super_admin/**")
                        .permitAll()
                        //Admin_controller role
                        .requestMatchers("/api/v1/admin/**").hasAnyRole(ADMIN.name())
                        .requestMatchers(GET, "/api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name())
                        .requestMatchers(POST, "/api/v1/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/admin/**").hasAnyAuthority(ADMIN_DELETE.name())

                        //Creator controller role
                        .requestMatchers("/api/v1/**").hasAnyRole(CREATOR.name(),AUDIENCE.name())
                        .requestMatchers(GET, "/api/v1/**").hasAnyAuthority(CREATOR_READ.name(),AUDIENCE_READ.name())
                        .requestMatchers(POST, "/api/v1/**").hasAnyAuthority(CREATOR_CREATE.name(),AUDIENCE_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/**").hasAnyAuthority(CREATOR_UPDATE.name(),AUDIENCE_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/**").hasAnyAuthority(CREATOR_DELETE.name(),AUDIENCE_DELETE.name())
                        .requestMatchers(DELETE, "/api/v1/**").hasAnyAuthority(AUDIENCE_BUY_ARTWORK.name())

                        // Audience controller role

                        .requestMatchers("/api/v1/user/**").hasAnyRole(AUDIENCE.name())
                        .requestMatchers(GET, "/api/v1/user/**").hasAnyAuthority(AUDIENCE_READ.name())
                        .requestMatchers(POST, "/api/v1/user/**").hasAnyAuthority(AUDIENCE_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/user/**").hasAnyAuthority(AUDIENCE_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/user/**").hasAnyAuthority(AUDIENCE_DELETE.name())
                        .requestMatchers(DELETE, "/api/v1/user/**").hasAnyAuthority(AUDIENCE_BUY_ARTWORK.name())

                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(logoutRequest -> logoutRequest
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(((request, response, authentication) ->
                                SecurityContextHolder.clearContext()))
                )
        ;
        return http.build();

    }
}
