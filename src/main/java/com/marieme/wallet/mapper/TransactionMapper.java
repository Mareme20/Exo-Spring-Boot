package com.marieme.wallet.mapper;

import com.marieme.wallet.dto.request.TransactionRequest;
import com.marieme.wallet.dto.response.TransactionResponse;
import com.marieme.wallet.entity.Transaction;
import com.marieme.wallet.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper MapStruct pour l'entite Transaction.
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateTransaction", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "wallet", source = "wallet")
    Transaction toEntity(TransactionRequest request, Wallet wallet);

    @Mapping(target = "walletId", source = "wallet.id")
    TransactionResponse toResponse(Transaction transaction);
}
