package com.datespot.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.datespot.config.JwtService;
import com.datespot.user.Role;
import com.datespot.user.User;
import com.datespot.user.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class responsible for handling user authentication and registration.
 * Provides methods for registering new users, authenticating existing users,
 * and generating JWT tokens for secure communication.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    // private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // If I add any values to User & RegisterRequest, I have to add it here too
    /**
     * Registers a new user by creating a user entity, encrypting their password,
     * saving the user in the database, and generating a JWT token for them.
     * 
     * @param request The request containing the user's registration details.
     * @return An AuthenticationResponse containing the generated JWT token.
     * @throws IllegalArgumentException If the registration fails due to invalid
     *                                  data.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        // var savedUser =
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        // var refreshToken = jwtService.generateRefreshToken(user);
        // saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                // .refreshToken(refreshToken)
                .build();
    }

    /**
     * Authenticates an existing user by verifying their credentials, and generates
     * a JWT token upon successful authentication.
     * 
     * @param request The authentication request containing the user's credentials.
     * @return An AuthenticationResponse containing the generated JWT token.
     * @throws IllegalArgumentException If the authentication fails due to invalid
     *                                  credentials.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        // var refreshToken = jwtService.generateRefreshToken(user);
        // revokeAllUserTokens(user);
        // saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                // .refreshToken(refreshToken)
                .build();
    }
}
