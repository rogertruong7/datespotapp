package com.datespot.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * A custom filter that checks the validity of a JWT token in the request.
 * It intercepts incoming HTTP requests, verifies if the JWT token is present in
 * the
 * "Authorization" header, validates the token, and sets the authentication in
 * the security context if the token is valid.
 * 
 * This filter ensures that only requests with valid JWT tokens can access
 * protected endpoints.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    // private final TokenRepository tokenRepository;

    /**
     * This method processes the incoming request, verifies the JWT token in the
     * "Authorization" header,
     * and if valid, sets the authentication details in the security context.
     * 
     * The filter is applied to all requests except those that are part of the
     * authentication process
     * (e.g., login or registration).
     *
     * @param request     The HTTP request being processed.
     * @param response    The HTTP response to be sent.
     * @param filterChain The filter chain that allows the request to proceed after
     *                    this filter is applied.
     * @throws ServletException If a servlet error occurs during processing.
     * @throws IOException      If an I/O error occurs during processing.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Skip authentication check for authentication-related requests
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Retrieve the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // If the header is missing or doesn't start with "Bearer ", skip this filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); // Extract the username (email) from the JWT token

        // If the username from the token exists and the authentication is not already
        // set, authenticate the user
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Uncomment this line if token persistence (e.g., checking expiration or
            // revocation) is needed
            // var isTokenValid = tokenRepository.findByToken(jwt)
            // .map(t -> !t.isExpired() && !t.isRevoked())
            // .orElse(false);

            boolean isTokenValid = true; // Assuming token is valid for now (implement actual validation logic)

            // If the token is valid, set the authentication in the security context
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // No credentials are needed because we're using a JWT
                        userDetails.getAuthorities());

                // Set the request details for the authentication token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication object in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
