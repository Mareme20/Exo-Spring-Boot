package com.marieme.wallet.entity;

import com.marieme.wallet.enums.TransactionStatus;
import com.marieme.wallet.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represente un mouvement (depot, retrait, transfert) effectue sur un
 * portefeuille electronique.
 */
@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "wallet")
@EqualsAndHashCode(callSuper = false, of = "id")
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TransactionType type;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal montant;

    @Column(name = "date_transaction", nullable = false)
    private LocalDateTime dateTransaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus statut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @PrePersist
    public void prePersist() {
        if (dateTransaction == null) {
            dateTransaction = LocalDateTime.now();
        }
        if (statut == null) {
            statut = TransactionStatus.EN_ATTENTE;
        }
    }
}
