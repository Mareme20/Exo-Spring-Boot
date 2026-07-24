package com.marieme.wallet.service;

import com.marieme.wallet.dto.request.UserCreationDto;
import com.marieme.wallet.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse create(UserCreationDto dto);

    UserResponse findById(Long id);

    Page<UserResponse> findAll(Pageable pageable);
}
