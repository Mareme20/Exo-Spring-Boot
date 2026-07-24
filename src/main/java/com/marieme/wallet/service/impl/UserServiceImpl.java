package com.marieme.wallet.service.impl;

import com.marieme.wallet.dto.request.UserCreationDto;
import com.marieme.wallet.dto.response.UserResponse;
import com.marieme.wallet.entity.User;
import com.marieme.wallet.exception.ConflictException;
import com.marieme.wallet.exception.ResourceNotFoundException;
import com.marieme.wallet.mapper.UserMapper;
import com.marieme.wallet.repository.UserRepository;
import com.marieme.wallet.service.UserService;
import com.marieme.wallet.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserCreationDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new ConflictException("Un utilisateur existe deja avec l'email : " + dto.email());
        }
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        return userMapper.toResponse(getUserOrThrow(id));
    }

@Override
    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable) {
        Pageable normalized = normalizePageable(pageable);
        return userRepository.findAll(normalized).map(userMapper::toResponse);
    }

private Pageable normalizePageable(Pageable pageable) {
        if (pageable == null) {
            return PaginationUtil.defaultPageable();
        }
        return PaginationUtil.normalize(pageable);
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Utilisateur", id));
    }
}
