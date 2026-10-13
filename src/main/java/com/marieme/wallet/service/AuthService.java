package com.marieme.wallet.service;

import com.marieme.wallet.dto.request.LoginRequest;
import com.marieme.wallet.dto.response.AuthResponse;

/**
 * Service d'authentification.
 * Contient la logique metier du login :
 * etapes 7 (login) -> 10 (user) -> 11 (password check) -> 12-13 (JWT) -> 14 (response)
 */
public interface AuthService {

    /**
     * Authentifie un utilisateur avec email et mot de passe.
     *
     * @param request LoginRequest contenant email et password
     * @return AuthResponse contenant le token JWT
     */
    AuthResponse login(LoginRequest request);
}
