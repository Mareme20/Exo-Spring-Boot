package com.marieme.wallet.controller;

import com.marieme.wallet.dto.request.TransactionRequest;
import com.marieme.wallet.dto.response.TransactionResponse;
import com.marieme.wallet.enums.TransactionType;
import com.marieme.wallet.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Gestion des mouvements sur les portefeuilles")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Creer une transaction (depot, retrait, transfert)")
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer une transaction par son id")
    public ResponseEntity<TransactionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @GetMapping("/wallet/{walletId}")
    @Operation(summary = "Lister les transactions d'un portefeuille (pagine)")
    public ResponseEntity<Page<TransactionResponse>> findByWalletId(
            @PathVariable Long walletId,
            @RequestParam(required = false) TransactionType type,
            @PageableDefault(size = 20, sort = "dateTransaction", direction = Sort.Direction.DESC)
            Pageable pageable) {
        if (type != null) {
            return ResponseEntity.ok(transactionService.findByWalletIdAndType(walletId, type, pageable));
        }
        return ResponseEntity.ok(transactionService.findByWalletId(walletId, pageable));
    }

    @PatchMapping("/{id}/annuler")
    @Operation(summary = "Annuler une transaction et restaurer le solde du portefeuille")
    public ResponseEntity<TransactionResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.cancel(id));
    }
}
