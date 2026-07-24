package com.marieme.wallet.service.impl;

import com.marieme.wallet.dto.request.TransactionRequest;
import com.marieme.wallet.dto.response.TransactionResponse;
import com.marieme.wallet.entity.Transaction;
import com.marieme.wallet.entity.Wallet;
import com.marieme.wallet.enums.TransactionStatus;
import com.marieme.wallet.enums.TransactionType;
import com.marieme.wallet.exception.BadRequestException;
import com.marieme.wallet.exception.ResourceNotFoundException;
import com.marieme.wallet.mapper.TransactionMapper;
import com.marieme.wallet.repository.TransactionRepository;
import com.marieme.wallet.repository.WalletRepository;
import com.marieme.wallet.service.TransactionService;
import com.marieme.wallet.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponse create(TransactionRequest request) {
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> ResourceNotFoundException.of("Portefeuille", request.getWalletId()));

        applyMovement(wallet, request.getType(), request.getMontant());

        Transaction transaction = transactionMapper.toEntity(request, wallet);
        transaction.setStatut(TransactionStatus.VALIDEE);

        walletRepository.save(wallet);
        Transaction saved = transactionRepository.save(transaction);
        return transactionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse findById(Long id) {
        return transactionMapper.toResponse(getTransactionOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> findByWalletId(Long walletId, Pageable pageable) {
        ensureWalletExists(walletId);
        Pageable normalized = PaginationUtil.normalize(pageable);
        return transactionRepository.findByWalletId(walletId, normalized).map(transactionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> findByWalletIdAndType(Long walletId, TransactionType type, Pageable pageable) {
        ensureWalletExists(walletId);
        Pageable normalized = PaginationUtil.normalize(pageable);
        return transactionRepository.findByWalletIdAndType(walletId, type, normalized).map(transactionMapper::toResponse);
    }

    @Override
    public TransactionResponse cancel(Long id) {
        Transaction transaction = getTransactionOrThrow(id);
        if (transaction.getStatut() == TransactionStatus.ANNULEE) {
            throw new BadRequestException("La transaction est deja annulee");
        }
        // Reverse the balance impact
        Wallet wallet = transaction.getWallet();
        reverseMovement(wallet, transaction.getType(), transaction.getMontant());
        walletRepository.save(wallet);

        transaction.setStatut(TransactionStatus.ANNULEE);
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

    /**
     * Applique l'impact d'un mouvement sur le solde du portefeuille.
     */
    private void applyMovement(Wallet wallet, TransactionType type, BigDecimal montant) {
        switch (type) {
            case DEPOT, TRANSFERT_ENTRANT -> wallet.setSolde(wallet.getSolde().add(montant));
            case RETRAIT, TRANSFERT_SORTANT -> {
                if (wallet.getSolde().compareTo(montant) < 0) {
                    throw new BadRequestException("Solde insuffisant pour effectuer cette operation");
                }
                wallet.setSolde(wallet.getSolde().subtract(montant));
            }
        }
    }

    private void reverseMovement(Wallet wallet, TransactionType type, BigDecimal montant) {
        switch (type) {
            case DEPOT, TRANSFERT_ENTRANT -> wallet.setSolde(wallet.getSolde().subtract(montant));
            case RETRAIT, TRANSFERT_SORTANT -> wallet.setSolde(wallet.getSolde().add(montant));
        }
    }

    private void ensureWalletExists(Long walletId) {
        if (!walletRepository.existsById(walletId)) {
            throw ResourceNotFoundException.of("Portefeuille", walletId);
        }
    }

    private Transaction getTransactionOrThrow(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Transaction", id));
    }
}
