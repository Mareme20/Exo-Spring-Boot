package com.marieme.wallet.dto.response;

import com.marieme.wallet.enums.TransactionStatus;
import com.marieme.wallet.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private Long id;
    private TransactionType type;
    private BigDecimal montant;
    private LocalDateTime dateTransaction;
    private TransactionStatus statut;
    private Long walletId;
}
