package com.marieme.wallet.mapper;

import com.marieme.wallet.dto.request.TransactionRequest;
import com.marieme.wallet.dto.response.TransactionResponse;
import com.marieme.wallet.entity.Transaction;
import com.marieme.wallet.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequest request, Wallet wallet) {
        if (request == null) {
            return null;
        }
        return Transaction.builder()
                .type(request.getType())
                .montant(request.getMontant())
                .wallet(wallet)
                .build();
    }

    public TransactionResponse toResponse(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        return TransactionResponse.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .montant(transaction.getMontant())
                .dateTransaction(transaction.getDateTransaction())
                .statut(transaction.getStatut())
                .walletId(transaction.getWallet() != null ? transaction.getWallet().getId() : null)
                .build();
    }
}
