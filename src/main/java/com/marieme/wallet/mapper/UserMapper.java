package com.marieme.wallet.mapper;

import com.marieme.wallet.dto.request.UserCreationDto;
import com.marieme.wallet.dto.response.UserResponse;
import com.marieme.wallet.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
 
/**
 * Mapper MapStruct pour l'entite User.
 * Le mot de passe est encode via PasswordEncoderMapper.
 */
@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "wallets", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    User toEntity(UserCreationDto dto);

    @Mapping(target = "nombreWallets", expression = "java(user.getWallets() == null ? 0 : user.getWallets().size())")
    UserResponse toResponse(User user);
}
