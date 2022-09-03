package com.leovegas.wallet.model;

import com.leovegas.wallet.dto.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private TransactionType type;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

}
