package com.marieme.wallet.controller;

import com.marieme.wallet.dto.request.WalletRequest;
import com.marieme.wallet.dto.response.WalletResponse;
import com.marieme.wallet.service.WalletService;
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
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
@Tag(name = "Portefeuilles", description = "Gestion des portefeuilles electroniques")
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    @Operation(summary = "Creer un portefeuille pour un utilisateur")
    public ResponseEntity<WalletResponse> create(@Valid @RequestBody WalletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer un portefeuille par son id")
    public ResponseEntity<WalletResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(walletService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Lister tous les portefeuilles (pagine)")
    public ResponseEntity<Page<WalletResponse>> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(walletService.findAll(pageable));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Lister les portefeuilles d'un utilisateur")
    public ResponseEntity<Page<WalletResponse>> findByUserId(
            @PathVariable Long userId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(walletService.findByUserId(userId, pageable));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un portefeuille")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        walletService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
