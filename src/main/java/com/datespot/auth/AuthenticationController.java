package com.datespot.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller for handling authentication and user registration operations.
 * Provides endpoints for user registration and authentication.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Endpoint for registering a new user.
     * 
     * @param request The registration request containing the user details.
     * @return Response entity with the authentication response, including a JWT
     *         token.
     * @throws jakarta.validation.ConstraintViolationException If the validation
     *                                                         fails for the
     *                                                         request.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Endpoint for authenticating an existing user.
     * 
     * @param request The authentication request containing the login credentials.
     * @return Response entity with the authentication response, including a JWT
     *         token.
     * @throws AuthenticationException If authentication fails due to incorrect
     *                                 credentials.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, StreamWriteException, DatabindException, java.io.IOException {
        service.refreshToken(request, response);
    }
}
