package com.nnk.springboot.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "Foo", authorities = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeControllerTest {
    private int id = 0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    @BeforeAll
    public void init() {
        Trade trade = new Trade("a", "t", 10.0);
        tradeService.insertTrade(trade);
        for (Trade trade1 : tradeService.findAll()) {
            if (trade1.getAccount().equals("a")) {
                id = trade1.getTradeId();
                break;
            }
        }
    }

    @AfterAll
    public void clean() {
        tradeRepository.deleteAll();
    }

    @Test
    public void testTradeController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk());

        // Add
        this.mockMvc.perform(post("/trade/validate")
                        .param("account", "new acc")
                        .param("type", "t")
                        .param("buyQuantity", "10.0")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(status().isFound())
                .andReturn();

        // Get update form
        this.mockMvc.perform(get("/trade/update/{id}", id)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk());

        // Update
        this.mockMvc.perform(post("/trade/update/{id}", id)
                        .param("account", "update acc")
                        .param("type", "type")
                        .param("buyQuantity", "20.0")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(status().isFound())
                .andReturn();

        // Delete

        this.mockMvc.perform(get("/trade/delete/{id}", id))
                .andDo(print())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
