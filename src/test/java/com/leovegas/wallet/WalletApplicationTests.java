package com.leovegas.wallet;

import com.leovegas.wallet.dto.mapper.WalletMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {WalletApplication.class, WalletMapperImpl.class})
@AutoConfigureMockMvc
class WalletApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void test1() throws Exception {
        //************************
        //          Given
        //************************
        //************************
        //          WHEN
        //************************

        MvcResult responseBody = mvc.perform(get("/wallet/player/"+1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

//        String restResponse = performGetRequest("/wallet/player/"+1 , 200);
        //************************
        //          THEN
        //************************
        // check rest response
        String restResponse = responseBody.getResponse().getContentAsString();

        assertThat(restResponse).contains("mina.bonyadi92@yahoo.com");
        assertThat(restResponse).contains("bonyadi");
        assertThat(restResponse).contains("mina");
        assertThat(restResponse).contains("76276346");
    }

}
