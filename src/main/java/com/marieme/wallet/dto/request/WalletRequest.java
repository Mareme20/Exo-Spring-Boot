package com.marieme.wallet.dto.request;

import com.marieme.wallet.validation.ValidDevise;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletRequest {

    @DecimalMin(value = "0.0", inclusive = true, message = "Le solde initial ne peut pas etre negatif")
    @Builder.Default
    private BigDecimal solde = BigDecimal.ZERO;

    @NotNull(message = "La devise est obligatoire")
    @ValidDevise
    private String devise;

    @NotNull(message = "L'identifiant utilisateur est obligatoire")
    private Long userId;
}
