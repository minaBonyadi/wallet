package com.leovegas.wallet.repository;

import com.leovegas.wallet.model.PlayerTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<PlayerTransaction, Long> {
}
