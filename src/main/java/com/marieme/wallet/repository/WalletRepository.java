package com.marieme.wallet.repository;

import com.marieme.wallet.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Page<Wallet> findByUserId(Long userId, Pageable pageable);

    Page<Wallet> findByDevise(String devise, Pageable pageable);

    /**
     * Recherche paginee des portefeuilles par solde minimum.
     */
    @Query("SELECT w FROM Wallet w WHERE w.solde >= :soldeMin")
    Page<Wallet> findBySoldeMin(@Param("soldeMin") BigDecimal soldeMin, Pageable pageable);

    /**
     * Recherche paginee des portefeuilles par solde maximum.
     */
    @Query("SELECT w FROM Wallet w WHERE w.solde <= :soldeMax")
    Page<Wallet> findBySoldeMax(@Param("soldeMax") BigDecimal soldeMax, Pageable pageable);

    /**
     * Recherche paginee des portefeuilles dans une fourchette de solde.
     */
    @Query(value = "SELECT w FROM Wallet w WHERE w.solde BETWEEN :soldeMin AND :soldeMax",
           countQuery = "SELECT COUNT(w) FROM Wallet w WHERE w.solde BETWEEN :soldeMin AND :soldeMax")
    Page<Wallet> findBySoldeBetween(@Param("soldeMin") BigDecimal soldeMin,
                                     @Param("soldeMax") BigDecimal soldeMax,
                                     Pageable pageable);

    /**
     * Recherche paginee des portefeuilles par utilisateur et devise.
     */
    @Query("SELECT w FROM Wallet w WHERE w.user.id = :userId AND w.devise = :devise")
    Page<Wallet> findByUserIdAndDevise(@Param("userId") Long userId,
                                       @Param("devise") String devise,
                                       Pageable pageable);

    /**
     * Compte le nombre de portefeuilles d'un utilisateur.
     */
    long countByUserId(Long userId);
}
