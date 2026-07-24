package com.marieme.wallet.mapper;

import com.marieme.wallet.dto.request.UserCreationDto;
import com.marieme.wallet.dto.response.UserResponse;
import com.marieme.wallet.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserCreationDto dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .nom(dto.nom())
                .email(dto.email())
                .build();
    }

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .nom(user.getNom())
                .email(user.getEmail())
                .dateCreation(user.getDateCreation())
                .nombreWallets(user.getWallets() == null ? 0 : user.getWallets().size())
                .build();
    }
}
