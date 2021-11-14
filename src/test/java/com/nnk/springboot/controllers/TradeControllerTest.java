package com.nnk.springboot.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
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
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TradeService tradeService;

    @Test
    public void testTradeController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk());

        // Add
        Trade trade = new Trade(111, "account", "type", 10.0);
        this.mockMvc.perform(post("/trade/validate")
                        .content(objectMapper.writeValueAsString(trade))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Update
        Trade newTrade = new Trade(111, "new account", "type", 10.0);
        this.mockMvc.perform(post("/trade/update/111")
                        .content(objectMapper.writeValueAsString(newTrade))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Delete
        Trade tradeToDelete = new Trade(1, "account to delete", "type", 10.0);
        tradeService.insertTrade(tradeToDelete);
        Integer tradeId = 0;
        for (Trade trade1 : tradeService.findAll()) {
            if (trade1.getAccount().equals("account to delete")) {
                tradeId = trade1.getTradeId();
                break;
            }
        }
        this.mockMvc.perform(get("/trade/delete/{id}", tradeId))
                .andDo(print())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
