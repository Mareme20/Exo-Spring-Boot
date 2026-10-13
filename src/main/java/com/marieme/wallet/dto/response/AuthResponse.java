package com.marieme.wallet.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
    String token,
    String type,
    String email
) {
    public static AuthResponse of(String token,  String email) {
        return AuthResponse.builder()
                .token(token)
                .type("Bearer") 
                .email(email)
                .build();
    }
}
