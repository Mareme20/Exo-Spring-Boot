package com.marieme.wallet.dto.request;

import com.marieme.wallet.validation.ValidEmail;
import com.marieme.wallet.validation.ValidNom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreationDto(
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 120, message = "Le nom doit contenir entre 2 et 120 caracteres")
    @ValidNom
    String nom,

    @NotBlank(message = "L'email est obligatoire")
    @ValidEmail
    @Size(max = 180)
    String email
) {}
