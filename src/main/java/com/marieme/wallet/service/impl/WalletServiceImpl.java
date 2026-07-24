package com.marieme.wallet.service.impl;

import com.marieme.wallet.dto.request.WalletRequest;
import com.marieme.wallet.dto.response.WalletResponse;
import com.marieme.wallet.entity.User;
import com.marieme.wallet.entity.Wallet;
import com.marieme.wallet.exception.ResourceNotFoundException;
import com.marieme.wallet.mapper.WalletMapper;
import com.marieme.wallet.repository.UserRepository;
import com.marieme.wallet.repository.WalletRepository;
import com.marieme.wallet.service.WalletService;
import com.marieme.wallet.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final WalletMapper walletMapper;

    @Override
    public WalletResponse create(WalletRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> ResourceNotFoundException.of("Utilisateur", request.getUserId()));

        Wallet wallet = walletMapper.toEntity(request, user);
        Wallet saved = walletRepository.save(wallet);
        return walletMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public WalletResponse findById(Long id) {
        return walletMapper.toResponse(getWalletOrThrow(id));
    }

@Override
    @Transactional(readOnly = true)
    public Page<WalletResponse> findAll(Pageable pageable) {
Pageable normalized = PaginationUtil.normalize(pageable);
        return walletRepository.findAll(normalized).map(walletMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalletResponse> findByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw ResourceNotFoundException.of("Utilisateur", userId);
        }
Pageable normalized = PaginationUtil.normalize(pageable);
        return walletRepository.findByUserId(userId, normalized).map(walletMapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        Wallet wallet = getWalletOrThrow(id);
        walletRepository.delete(wallet);
    }

    private Wallet getWalletOrThrow(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Portefeuille", id));
    }
}
