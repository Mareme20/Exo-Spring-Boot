package com.marieme.wallet.controller;

import com.marieme.wallet.dto.request.LoginRequest;
import com.marieme.wallet.dto.response.AuthResponse;
import com.marieme.wallet.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur d'authentification.
 * Recu la requete POST /login et delegue au AuthService.
 * Correspond aux etapes 5 (requete) -> 6 (transmission) -> 7 (login()) du diagramme.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "Connexion et obtention du token JWT")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Authentifier un utilisateur et obtenir un token JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
