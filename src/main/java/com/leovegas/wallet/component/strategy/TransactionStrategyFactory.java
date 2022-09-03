package com.leovegas.wallet.component.strategy;

import com.leovegas.wallet.dto.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class TransactionStrategyFactory {

    private Map<TransactionType, TransactionStrategy> transactionStrategies;

    @Autowired
    public TransactionStrategyFactory(Set<TransactionStrategy> strategySet) {
        initializeTransaction(strategySet);
    }

    public TransactionStrategy findTransactionStrategy(TransactionType strategyName) {
        return transactionStrategies.get(strategyName);
    }

    public void initializeTransaction(Set<TransactionStrategy> strategySet) {

        transactionStrategies = new HashMap<>();
        strategySet.forEach(
                strategy -> transactionStrategies.put(strategy.getStrategyName(), strategy));
    }
}
