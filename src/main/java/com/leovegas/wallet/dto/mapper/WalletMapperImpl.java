package com.leovegas.wallet.dto.mapper;

import com.leovegas.wallet.dto.PlayerDto;
import com.leovegas.wallet.dto.TransactionDto;
import com.leovegas.wallet.model.Player;
import com.leovegas.wallet.model.PlayerTransaction;

import java.util.ArrayList;
import java.util.List;

public class WalletMapperImpl implements WalletMapper{
    @Override
    public PlayerDto mapPlayerToPlayerDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .balance(player.getBalance()).build();
    }

    @Override
    public List<TransactionDto> mapTransactionLstToTransactionDto(List<PlayerTransaction> transactions) {
        if (transactions.isEmpty()) {
            return new ArrayList<>();
        }

        //todo add player
        return transactions.stream().map(transaction -> TransactionDto.builder()
//                .player()
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .id(transaction.getId()).build()).toList();
    }
}
