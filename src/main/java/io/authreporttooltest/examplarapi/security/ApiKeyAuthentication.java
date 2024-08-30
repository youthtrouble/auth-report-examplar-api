package io.authreporttooltest.examplarapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * Custom Authentication implementation for API key-based authentication.
 * This class represents an authenticated principal that has been validated via an API key.
 */
public class ApiKeyAuthentication implements Authentication {

    private final String apiKey;
    private boolean authenticated = true;

    /**
     * Constructs a new ApiKeyAuthentication with the given API key.
     *
     * @param apiKey The API key used for authentication
     */
    public ApiKeyAuthentication(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Returns an empty list of GrantedAuthority as we're not using role-based authorization for API keys.
     *
     * @return An empty list of GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * Returns the API key as the credentials.
     *
     * @return The API key
     */
    @Override
    public Object getCredentials() {
        return apiKey;
    }

    /**
     * Returns null as we don't have additional details for API key authentication.
     *
     * @return null
     */
    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * Returns the API key as the principal.
     *
     * @return The API key
     */
    @Override
    public Object getPrincipal() {
        return apiKey;
    }

    /**
     * Checks if this authentication is authenticated.
     *
     * @return true if authenticated, false otherwise
     */
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Sets the authenticated status.
     *
     * @param isAuthenticated the authentication status to set
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    /**
     * Returns the API key as the name of the principal.
     *
     * @return The API key
     */
    @Override
    public String getName() {
        return apiKey;
    }
}
