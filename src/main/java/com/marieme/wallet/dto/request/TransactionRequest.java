package com.marieme.wallet.dto.request;

import com.marieme.wallet.enums.TransactionType;
import com.marieme.wallet.validation.ValidMontant;
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
public class TransactionRequest {

    @NotNull(message = "Le type de transaction est obligatoire")
    private TransactionType type;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.01", message = "Le montant doit etre strictement positif")
    @ValidMontant
    private BigDecimal montant;

    @NotNull(message = "L'identifiant du portefeuille est obligatoire")
    private Long walletId;
}
