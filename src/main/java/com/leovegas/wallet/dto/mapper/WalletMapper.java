package com.leovegas.wallet.dto.mapper;

import com.leovegas.wallet.dto.PlayerDto;
import com.leovegas.wallet.dto.TransactionDto;
import com.leovegas.wallet.model.Player;
import com.leovegas.wallet.model.PlayerTransaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface WalletMapper {
    PlayerDto mapPlayerToPlayerDto(Player player);

    List<TransactionDto> mapTransactionLstToTransactionDto(List<PlayerTransaction> transactions);
}
