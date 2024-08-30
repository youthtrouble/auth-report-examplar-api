package io.authreporttooltest.examplarapi.config;

import io.authreporttooltest.examplarapi.security.ApiKeyAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configures the SecurityFilterChain for the application.
     * This method sets up API key authentication for the "/api/products" endpoint
     * and basic authentication for all other endpoints.
     *
     * @param http The HttpSecurity object to configure
     * @return The configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection as it's not needed for our stateless API
                .csrf(csrf -> csrf.disable())

                // Configure session management to be stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add the API key filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(new ApiKeyAuthFilter(), UsernamePasswordAuthenticationFilter.class)

                // Configure authorization rules
                .authorizeHttpRequests(authz -> authz
                        // All requests require authentication
                        .anyRequest().authenticated()
                )

                // Enable HTTP Basic authentication
                .httpBasic(httpBasic -> {});

        return http.build();
    }

    /**
     * Configures the UserDetailsService with in-memory users for testing purposes.
     * In a production environment, this would typically be replaced with a database-backed user service.
     *
     * @return A UserDetailsService containing test users
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // Create an admin user
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN")
                .build();

        // Create a regular user
        UserDetails regularUser = User.builder()
                .username("user")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER")
                .build();

        // Return an InMemoryUserDetailsManager containing both users
        return new InMemoryUserDetailsManager(adminUser, regularUser);
    }

    /**
     * Configures the PasswordEncoder for the application.
     * BCrypt is a strong hashing function suitable for password storage.
     *
     * @return A BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}