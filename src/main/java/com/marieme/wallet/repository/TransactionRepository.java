package com.marieme.wallet.repository;

import com.marieme.wallet.entity.Transaction;
import com.marieme.wallet.enums.TransactionStatus;
import com.marieme.wallet.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByWalletId(Long walletId, Pageable pageable);

    Page<Transaction> findByWalletIdAndType(Long walletId, TransactionType type, Pageable pageable);

    Page<Transaction> findByWalletIdAndStatut(Long walletId, TransactionStatus statut, Pageable pageable);

    /**
     * Recherche paginee des transactions par type, tous portefeuilles confondus.
     */
    Page<Transaction> findByType(TransactionType type, Pageable pageable);

    /**
     * Recherche paginee des transactions par statut, tous portefeuilles confondus.
     */
    Page<Transaction> findByStatut(TransactionStatus statut, Pageable pageable);

    /**
     * Recherche paginee des transactions sur une periode de date.
     */
    @Query(value = "SELECT t FROM Transaction t WHERE t.dateTransaction BETWEEN :debut AND :fin",
           countQuery = "SELECT COUNT(t) FROM Transaction t WHERE t.dateTransaction BETWEEN :debut AND :fin")
    Page<Transaction> findByDateBetween(@Param("debut") LocalDateTime debut,
                                        @Param("fin") LocalDateTime fin,
                                        Pageable pageable);

    /**
     * Recherche paginee des transactions d'un portefeuille sur une periode de date.
     */
    @Query(value = "SELECT t FROM Transaction t WHERE t.wallet.id = :walletId AND t.dateTransaction BETWEEN :debut AND :fin",
           countQuery = "SELECT COUNT(t) FROM Transaction t WHERE t.wallet.id = :walletId AND t.dateTransaction BETWEEN :debut AND :fin")
    Page<Transaction> findByWalletIdAndDateBetween(@Param("walletId") Long walletId,
                                                    @Param("debut") LocalDateTime debut,
                                                    @Param("fin") LocalDateTime fin,
                                                    Pageable pageable);

    /**
     * Recherche paginee des transactions par montant minimum.
     */
    @Query("SELECT t FROM Transaction t WHERE t.montant >= :montantMin")
    Page<Transaction> findByMontantMin(@Param("montantMin") java.math.BigDecimal montantMin, Pageable pageable);

    /**
     * Recherche paginee des transactions par type et statut.
     */
    @Query(value = "SELECT t FROM Transaction t WHERE t.type = :type AND t.statut = :statut",
           countQuery = "SELECT COUNT(t) FROM Transaction t WHERE t.type = :type AND t.statut = :statut")
    Page<Transaction> findByTypeAndStatut(@Param("type") TransactionType type,
                                          @Param("statut") TransactionStatus statut,
                                          Pageable pageable);
}
