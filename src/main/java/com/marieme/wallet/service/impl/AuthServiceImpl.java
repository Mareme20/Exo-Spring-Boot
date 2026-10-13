package com.marieme.wallet.service.impl;

import com.marieme.wallet.dto.request.LoginRequest;
import com.marieme.wallet.dto.response.AuthResponse;
import com.marieme.wallet.exception.BadRequestException;
import com.marieme.wallet.security.CustomUserDetailsService;
import com.marieme.wallet.security.JwtService;
import com.marieme.wallet.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation du service d'authentification.
 * Suit le diagramme de sequence :
 * 8  -> loadUserByUsername (via CustomUserDetailsService)
 * 9  -> findUserByEmail (UserRepository)
 * 10 -> user (retour de l'utilisateur)
 * 11 -> verifierPassword (BCrypt check)
 * 12 -> generateToken(user) via JwtService
 * 13 -> token
 * 14 -> reponse au client
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest request) {
        // Etape 8-9-10 : loadUserByUsername via UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());

        // Etape 11 : Verification du mot de passe (BCrypt)
        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            throw new BadRequestException("Email ou mot de passe incorrect");
        }

 
        // Etape 12 : Generation du token JWT
        String token = jwtService.generateToken(userDetails);

        // Etape 13-14 : Retour du token au client
        return AuthResponse.of(
                token,
                userDetails.getUsername()
        );
    }   
}
