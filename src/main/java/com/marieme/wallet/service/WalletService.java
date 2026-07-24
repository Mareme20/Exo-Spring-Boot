package com.marieme.wallet.service;

import com.marieme.wallet.dto.request.WalletRequest;
import com.marieme.wallet.dto.response.WalletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletService {

    WalletResponse create(WalletRequest request);

    WalletResponse findById(Long id);

    Page<WalletResponse> findAll(Pageable pageable);

    Page<WalletResponse> findByUserId(Long userId, Pageable pageable);

    void delete(Long id);
}
