package com.leovegas.wallet.component;

import com.leovegas.wallet.component.strategy.TransactionStrategy;
import com.leovegas.wallet.dto.TransactionType;
import com.leovegas.wallet.model.PlayerTransaction;
import com.leovegas.wallet.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditTransaction implements TransactionStrategy {

    private final PlayerRepository playerRepository;

    @Override
    public TransactionType getStrategyName() {
        return TransactionType.CREDIT;
    }

    @Override
    public void doTransaction(PlayerTransaction playerTransaction) {
        playerTransaction.getPlayer().setBalance(
                playerTransaction.getPlayer().getBalance().add(playerTransaction.getAmount()));
        playerRepository.save(playerTransaction.getPlayer());
    }

}
