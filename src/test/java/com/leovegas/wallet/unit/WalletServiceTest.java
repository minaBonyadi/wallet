package com.leovegas.wallet.unit;

import com.leovegas.wallet.component.strategy.TransactionStrategyFactory;
import com.leovegas.wallet.dto.TransactionType;
import com.leovegas.wallet.exception.NotFoundException;
import com.leovegas.wallet.model.Player;
import com.leovegas.wallet.model.PlayerTransaction;
import com.leovegas.wallet.repository.PlayerRepository;
import com.leovegas.wallet.repository.TransactionRepository;
import com.leovegas.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WalletServiceTest {

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
        Player player = Util.createSamplePlayer();
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
            walletService.getPlayerTransactions(1L);
        }catch (NotFoundException exception) {
            //Then
            assertThat(exception.getMessage()).isEqualTo("Player not found!");
        }
    }

    @Test
    void getPlayerTransactions_1() {
        //Given
        Player player = Util.createSamplePlayer();

        //When
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        List<PlayerTransaction> playerTransactions = walletService.getPlayerTransactions(1L);

        //Then
        assertThat(playerTransactions.get(0).getPlayer().getName()).isEqualTo("mina");
        assertThat(playerTransactions.get(0).getPlayer().getBalance()).isEqualTo(BigDecimal.valueOf(100000));

        assertThat(playerTransactions.get(1).getPlayer().getName()).isEqualTo("mina");
        assertThat(playerTransactions.get(1).getPlayer().getBalance()).isEqualTo(BigDecimal.valueOf(100000));

        assertThat(playerTransactions.get(0).getAmount()).isEqualTo(BigDecimal.valueOf(2000));
        assertThat(playerTransactions.get(0).getType()).isEqualTo(TransactionType.CREDIT);

        assertThat(playerTransactions.get(1).getAmount()).isEqualTo(BigDecimal.valueOf(5000));
        assertThat(playerTransactions.get(1).getType()).isEqualTo(TransactionType.DEBIT);
    }

    @Test
    void getPlayerTransactionsWhenPlayerNotExist_2() {
        //Given
        //When
        try{
            walletService.getPlayerById(1L);
        }catch (NotFoundException exception) {
            //Then
            assertThat(exception.getMessage()).isEqualTo("Player not found!");
        }
    }
}
