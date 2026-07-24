package com.marieme.wallet.service;

import com.marieme.wallet.dto.request.TransactionRequest;
import com.marieme.wallet.dto.response.TransactionResponse;
import com.marieme.wallet.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransactionResponse create(TransactionRequest request);

    TransactionResponse findById(Long id);

    Page<TransactionResponse> findByWalletId(Long walletId, Pageable pageable);

    Page<TransactionResponse> findByWalletIdAndType(Long walletId, TransactionType type, Pageable pageable);

    TransactionResponse cancel(Long id);
}
