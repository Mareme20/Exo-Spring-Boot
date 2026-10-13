package com.marieme.wallet.mapper;

import com.marieme.wallet.dto.request.WalletRequest;
import com.marieme.wallet.dto.response.WalletResponse;
import com.marieme.wallet.entity.User;
import com.marieme.wallet.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper MapStruct pour l'entite Wallet.
 */
@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "user", source = "user")
    Wallet toEntity(WalletRequest request, User user);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userNom", source = "user.nom")
    WalletResponse toResponse(Wallet wallet);
}
