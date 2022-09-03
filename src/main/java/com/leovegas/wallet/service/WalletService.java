package com.leovegas.wallet.service;

import com.leovegas.wallet.component.strategy.TransactionStrategyFactory;
import com.leovegas.wallet.dto.TransactionDto;
import com.leovegas.wallet.dto.rest_response.RestResponse;
import com.leovegas.wallet.dto.rest_response.RestResponseType;
import com.leovegas.wallet.exception.NotFoundException;
import com.leovegas.wallet.model.Player;
import com.leovegas.wallet.model.PlayerTransaction;
import com.leovegas.wallet.repository.PlayerRepository;
import com.leovegas.wallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionStrategyFactory transactionStrategyFactory;

    /**
     *
     * @param playerId
     * @return
     */
    public Player getPlayerById(long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new NotFoundException("Player not found!"));
    }

    /**
     *
     * @param playerId
     * @param transactionDto
     * @return
     */
    public RestResponse createAndRunTransaction(long playerId, TransactionDto transactionDto) {

        PlayerTransaction playerTransaction = PlayerTransaction.builder()
                .player(playerRepository.findById(playerId).orElseThrow(() ->
                        new NotFoundException("Player not found!")))
                .type(transactionDto.getType())
                .amount(transactionDto.getAmount())
                .build();

        transactionRepository.save(playerTransaction);
        log.info("This transaction by id {} has just received", playerTransaction.getId());

        transactionStrategyFactory.findTransactionStrategy(playerTransaction.getType())
                .doTransaction(playerTransaction);

        log.info("This transaction by id{} has just run", playerTransaction.getId());

        return new RestResponse(RestResponseType.SUCCESS, "The transaction has just done!");
    }

    /**
     *
     * @param playerId
     * @return
     */
    public List<PlayerTransaction> getPlayerTransactions(long playerId) {
        return playerRepository.findById(playerId).orElseThrow(()-> new NotFoundException("Player not found!"))
                .getTransactions();
    }
}