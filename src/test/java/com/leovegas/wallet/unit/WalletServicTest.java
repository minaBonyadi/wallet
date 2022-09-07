package com.leovegas.wallet.unit;

import com.leovegas.wallet.component.strategy.TransactionStrategyFactory;
import com.leovegas.wallet.dto.PlayerDto;
import com.leovegas.wallet.dto.TransactionDto;
import com.leovegas.wallet.dto.TransactionType;
import com.leovegas.wallet.dto.rest_response.RestResponse;
import com.leovegas.wallet.dto.rest_response.RestResponseType;
import com.leovegas.wallet.exception.NotFoundException;
import com.leovegas.wallet.model.Player;
import com.leovegas.wallet.model.PlayerTransaction;
import com.leovegas.wallet.repository.PlayerRepository;
import com.leovegas.wallet.repository.TransactionRepository;
import com.leovegas.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WalletServicTest {

    @MockBean
    private PlayerRepository playerRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private TransactionStrategyFactory transactionStrategyFactory;

    @Autowired
    private WalletService walletService;

    @Test
    void getPlayerById_1() {
        //Given
        Player player = Player.builder().id(1L).name("mina")
                .balance(BigDecimal.valueOf(100000))
                .transactions(Arrays.asList(PlayerTransaction.builder()
                                .id(1L)
                        .amount(BigDecimal.valueOf(2000))
                        .type(TransactionType.CREDIT).build(),
                        PlayerTransaction.builder()
                        .id(2L)
                        .amount(BigDecimal.valueOf(5000))
                        .type(TransactionType.DEBIT).build())).build();
        //When
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        Player finalPlayer = walletService.getPlayerById(1L);

        //Then
        assertThat(finalPlayer.getName()).isEqualTo("mina");
        assertThat(finalPlayer.getBalance()).isEqualTo(BigDecimal.valueOf(100000));
        assertThat(finalPlayer.getTransactions().get(0).getAmount()).isEqualTo(BigDecimal.valueOf(2000));
        assertThat(finalPlayer.getTransactions().get(0).getType()).isEqualTo(TransactionType.CREDIT);

        assertThat(finalPlayer.getTransactions().get(1).getAmount()).isEqualTo(BigDecimal.valueOf(5000));
        assertThat(finalPlayer.getTransactions().get(1).getType()).isEqualTo(TransactionType.DEBIT);

    }

    @Test
    void getPlayerByIdWhenNotExist_2() {
        //Given
        //When
        try{
            walletService.getPlayerById(1L);
        }catch (NotFoundException exception) {
            //Then
            assertThat(exception.getMessage()).isEqualTo("Player not found!");
        }
    }

    @Test
    void createAndRunTransaction_1() {
        //Given
        Player player = Player.builder().id(1L).name("mina")
                .balance(BigDecimal.valueOf(100000))
                .transactions(Arrays.asList(PlayerTransaction.builder()
                                .id(1L)
                                .amount(BigDecimal.valueOf(2000))
                                .type(TransactionType.CREDIT).build(),
                        PlayerTransaction.builder()
                                .id(2L)
                                .amount(BigDecimal.valueOf(5000))
                                .type(TransactionType.DEBIT).build())).build();

        TransactionDto transactionDto = TransactionDto.builder().amount(BigDecimal.valueOf(60000))
                .type(TransactionType.DEBIT).build();

        //When
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));
//        when(walletService.getPlayerTransactions(any())).thenReturn(playerTransaction);

        RestResponse response = walletService.createAndRunTransaction(1L, transactionDto);

        //Then
        assertThat(response.getMessage()).isEqualTo("The transaction has just done!");
        assertThat(response.getCode()).isEqualTo(RestResponseType.SUCCESS);
    }
}
