package io.authreporttooltest.examplarapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filter to handle API key authentication for the "/api/products" endpoint.
 * This filter checks for a valid API key in the request header.
 */
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    // The header name for the API key
    private static final String API_KEY_HEADER = "X-API-Key";

    // List of valid API keys (in a real application, these would be stored securely)
    private static final List<String> VALID_API_KEYS = Arrays.asList("key1", "key2");

    /**
     * Performs the filtering for the API key authentication.
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @param filterChain The filter chain
     * @throws ServletException if an error occurs during filtering
     * @throws IOException if an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Only apply this filter to the "/api/products" endpoint
        if (!request.getRequestURI().equals("/api/products")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the API key from the request header
        String apiKey = request.getHeader(API_KEY_HEADER);

        // Check if the API key is valid
        if (apiKey == null || !VALID_API_KEYS.contains(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // If API key is valid, set an authentication object in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(new ApiKeyAuthentication(apiKey));

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}