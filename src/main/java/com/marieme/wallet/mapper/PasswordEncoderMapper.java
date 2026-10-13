package com.marieme.wallet.mapper;

import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Utilitaire pour encoder les mots de passe avec BCrypt dans les mappers MapStruct.
 * Utilise l'annotation @Named pour etre reference dans @Mapping(qualifiedByName = ...)
 */
@Component
public class PasswordEncoderMapper {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Named("encodePassword")
    public String encodePassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            return null;
        }
        return passwordEncoder.encode(rawPassword);
    }
}
