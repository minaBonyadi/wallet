package com.leovegas.wallet.component.strategy;

import com.leovegas.wallet.dto.TransactionType;
import com.leovegas.wallet.model.PlayerTransaction;

public interface TransactionStrategy {
    TransactionType getStrategyName();
    void doTransaction(PlayerTransaction playerTransaction);
}
