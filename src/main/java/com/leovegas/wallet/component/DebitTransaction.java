package com.leovegas.wallet.component;

import com.leovegas.wallet.component.strategy.TransactionStrategy;
import com.leovegas.wallet.dto.TransactionType;
import com.leovegas.wallet.exception.TransactionRunningException;
import com.leovegas.wallet.model.PlayerTransaction;
import com.leovegas.wallet.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebitTransaction implements TransactionStrategy {

    private final PlayerRepository playerRepository;

    @Autowired
    public TransactionType getStrategyName() {
        return TransactionType.DEBIT;
    }

    /** run a debit transaction
     *
     * @param playerTransaction a created transaction of a player
     */
    @Override
    public void doTransaction(PlayerTransaction playerTransaction) {

        if (playerTransaction.getPlayer().getBalance().doubleValue() > playerTransaction.getAmount().doubleValue()) {

            playerTransaction.getPlayer().setBalance(
                    playerTransaction.getPlayer().getBalance().subtract(playerTransaction.getAmount()));

            playerRepository.save(playerTransaction.getPlayer());

        }else {
            throw new TransactionRunningException("Sorry, player balance is not enough!");
        }
    }
}
