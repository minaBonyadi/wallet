package com.leovegas.wallet.unit;

import com.leovegas.wallet.dto.TransactionType;
import com.leovegas.wallet.model.Player;
import com.leovegas.wallet.model.PlayerTransaction;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

public class Util {
    public static Player createSamplePlayer() {
        Player player = Player.builder().id(1L).name("mina")
                .balance(BigDecimal.valueOf(100000)).build();

        player.setTransactions(
                Arrays.asList(PlayerTransaction.builder()
                                .id(1L)
                                .amount(BigDecimal.valueOf(2000))
                                .type(TransactionType.CREDIT)
                                .player(player)
                                .build(),
                        PlayerTransaction.builder()
                                .id(2L)
                                .amount(BigDecimal.valueOf(5000))
                                .type(TransactionType.DEBIT)
                                .player(player)
                                .build()));
        return player;
    }

    public static PlayerTransaction getPlayerTransaction() {
        Player player = Player.builder().id(1L).name("mina")
                .balance(BigDecimal.valueOf(100000)).build();

        player.setTransactions(Collections.singletonList(PlayerTransaction.builder()
                                .id(1L)
                                .amount(BigDecimal.valueOf(2000))
                                .type(TransactionType.CREDIT)
                                .player(player)
                                .build()));

        return player.getTransactions().get(0);
    }
}
