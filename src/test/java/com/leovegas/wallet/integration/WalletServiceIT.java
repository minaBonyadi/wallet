package com.leovegas.wallet.integration;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;

import com.leovegas.wallet.WalletApplication;
import com.leovegas.wallet.dto.TransactionType;
import com.leovegas.wallet.dto.mapper.WalletMapperImpl;
import com.leovegas.wallet.exception.NotFoundException;
import com.leovegas.wallet.exception.TransactionRunningException;
import com.leovegas.wallet.model.Player;
import com.leovegas.wallet.model.PlayerTransaction;
import com.leovegas.wallet.repository.PlayerRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {WalletApplication.class, WalletMapperImpl.class})
@AutoConfigureMockMvc
class WalletServiceIT {

    @Autowired
    private MockMvc mvc;

	@Autowired
	private PlayerRepository playerRepository;

	private long samplePlayer() {
		playerRepository.deleteAll();

		//check player need builder or not
		Player player = Player.builder().name("mina").balance(new BigDecimal("123456.90")).build();

		player.setTransactions(Collections.singletonList(
				PlayerTransaction.builder().player(player)
				.amount(new BigDecimal(543200))
				.type(TransactionType.CREDIT).build()));

		playerRepository.save(player);
		return player.getId();
	}

    @Test
	@Description("call get player info when player is not in our db and raise a 'Player not found' exception")
    void playerNotFoundException_1() throws Exception {
        //************************
        //          Given
        //************************
        //************************
        //          WHEN
        //************************
		//************************
		//          THEN
		//************************
        mvc.perform(get("/wallet/player/"+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
				.andExpect(result -> assertEquals("Player not found!",
						Objects.requireNonNull(result.getResolvedException()).getMessage()))
				.andReturn();
    }

	@Test
	@Description("call get player info when player is exist in our db")
	void getPlayerInfoSuccessfully_2() throws Exception {
		//************************
		//          Given
		//************************
		long playerId = samplePlayer();
		//************************
		//          WHEN
		//************************
		MvcResult responseBody = mvc.perform(get("/wallet/player/"+playerId)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//************************
		//          THEN
		//************************
		// check rest response
		String restResponse = responseBody.getResponse().getContentAsString();

		assertThat(restResponse).contains("id");
		assertThat(restResponse).contains("name");
		assertThat(restResponse).contains("balance");

		assertThat(restResponse).contains("mina");
		assertThat(restResponse).contains("123456.90");

	}


	@Test
	@Description("create and run a transaction which is a CREDIT transaction")
	void createAndRunTransaction_3() throws Exception {
		//************************
		//          Given
		//************************
		long playerId = samplePlayer();

		String requestBody =
				"""
					{
					"amount" : 2000,
					"type": "CREDIT"
					}
				""";
		//************************
		//          WHEN
		//************************
		MvcResult responseBody = mvc.perform(post("/wallet/"+playerId+"/transaction")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//************************
		//          THEN
		//************************
		// check rest response
		String restResponse = responseBody.getResponse().getContentAsString();
		assertThat(restResponse).contains("The transaction has just done!");

		Player finalPlayer = playerRepository.findById(playerId).orElse(null);
		assertThat(Objects.requireNonNull(finalPlayer).getBalance().doubleValue()).isEqualTo(125456.90);
		assertThat(finalPlayer.getName()).contains("mina");

		assertEquals(finalPlayer.getTransactions().get(0).getType().name(), "CREDIT");
		assertEquals(finalPlayer.getTransactions().get(1).getType().name(), "CREDIT");
		assertEquals(finalPlayer.getTransactions().get(0).getAmount().doubleValue(), 543200.0);
		assertEquals(finalPlayer.getTransactions().get(1).getAmount().doubleValue(), 2000.0);
	}


	@Test
	@Description("create and run a transaction which is a DEBIT transaction")
	void createAndRunTransaction_4() throws Exception {
		//************************
		//          Given
		//************************
		long playerId = samplePlayer();

		String requestBody = """
								{
								"amount" : 3000,
								"type": "DEBIT"
								}
				""";
		//************************
		//          WHEN
		//************************
		MvcResult responseBody = mvc.perform(post("/wallet/"+playerId+"/transaction")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//************************
		//          THEN
		//************************
		// check rest response
		String restResponse = responseBody.getResponse().getContentAsString();
		assertThat(restResponse).contains("The transaction has just done!");

		Player finalPlayer = playerRepository.findById(playerId).orElse(null);
		assertThat(Objects.requireNonNull(finalPlayer).getBalance().doubleValue()).isEqualTo(120456.9);
		assertThat(finalPlayer.getName()).contains("mina");

		assertEquals(finalPlayer.getTransactions().get(0).getType().name(), "CREDIT");
		assertEquals(finalPlayer.getTransactions().get(1).getType().name(), "DEBIT");
		assertEquals(finalPlayer.getTransactions().get(0).getAmount().doubleValue(), 543200.0);
		assertEquals(finalPlayer.getTransactions().get(1).getAmount().doubleValue(), 3000.0);
	}

	@Test
	@Description("create and run a transaction which is a DEBIT transaction when amount is higher than player balance")
	void createAndRunTransaction_5() throws Exception {
		//************************
		//          Given
		//************************
		long playerId = samplePlayer();

		String requestBody = """
								{
								"amount" : 133456.90,
								"type": "DEBIT"
								}
				""";
		//************************
		//          WHEN
		//************************
		MvcResult responseBody = mvc.perform(post("/wallet/"+playerId+"/transaction")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof TransactionRunningException))
				.andExpect(result -> assertEquals("Sorry, player balance is not enough!",
						Objects.requireNonNull(result.getResolvedException()).getMessage()))
				.andReturn();
		//************************
		//          THEN
		//************************
		// check rest response
		String restResponse = responseBody.getResponse().getContentAsString();
		assertThat(restResponse).contains("code");
		assertThat(restResponse).contains("message");

		assertThat(restResponse).contains("error");
		assertThat(restResponse).contains("Sorry, player balance is not enough!");
	}


	@Test
	@Description("Getting a player transactions")
	void getAllPlayerTransactions_6() throws Exception {
		//************************
		//          Given
		//************************
		long playerId = samplePlayer();
		//************************
		//          WHEN
		//************************
		MvcResult responseBody = mvc.perform(get("/wallet/"+playerId+"/transaction")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		//************************
		//          THEN
		//************************
		// check rest response
		String restResponse = responseBody.getResponse().getContentAsString();

		assertThat(restResponse).contains("id");
		assertThat(restResponse).contains("amount");
		assertThat(restResponse).contains("type");

		assertThat(restResponse).contains("CREDIT");
		assertThat(restResponse).contains("543200.00");
	}

	@Test
	@Description("Getting a player transactions when player not exists")
	void getAllPlayerTransactions_7() throws Exception {
		//************************
		//          Given
		//************************
		long playerId = samplePlayer();
		//************************
		//          WHEN
		//************************
		//************************
		//          THEN
		//************************
		mvc.perform(get("/wallet/"+(playerId+1)+"/transaction")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
				.andExpect(result -> assertEquals("Player not found!",
						Objects.requireNonNull(result.getResolvedException()).getMessage()))
				.andReturn();
	}
}
