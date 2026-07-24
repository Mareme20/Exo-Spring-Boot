package com.marieme.wallet.mapper;

import com.marieme.wallet.dto.request.WalletRequest;
import com.marieme.wallet.dto.response.WalletResponse;
import com.marieme.wallet.entity.User;
import com.marieme.wallet.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public Wallet toEntity(WalletRequest request, User user) {
        if (request == null) {
            return null;
        }
        return Wallet.builder()
                .solde(request.getSolde())
                .devise(request.getDevise())
                .user(user)
                .build();
    }

    public WalletResponse toResponse(Wallet wallet) {
        if (wallet == null) {
            return null;
        }
        return WalletResponse.builder()
                .id(wallet.getId())
                .solde(wallet.getSolde())
                .devise(wallet.getDevise())
                .userId(wallet.getUser() != null ? wallet.getUser().getId() : null)
                .userNom(wallet.getUser() != null ? wallet.getUser().getNom() : null)
                .dateCreation(wallet.getDateCreation())
                .build();
    }
}
