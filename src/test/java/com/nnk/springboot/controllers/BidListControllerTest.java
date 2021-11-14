package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BidListService bidListService;

    @Test
    public void testBidListController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());

        // Add
        BidList bidList = new BidList(111,"account", "type", 20.0);
        this.mockMvc.perform(post("/bidList/validate")
                        .content(objectMapper.writeValueAsString(bidList))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Update
        BidList newBidList = new BidList(111, "account2", "type2", 22.0);
        this.mockMvc.perform(post("/bidList/update/111")
                        .content(objectMapper.writeValueAsString(newBidList))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Delete
        BidList bidToDelete = new BidList("account to delete", "type to delete", 1.0);
        bidListService.insertBidList(bidToDelete);
        Integer listId = 0;
        for (BidList list : bidListService.findAll()) {
            if (list.getAccount().equals("account to delete")) {
                listId = list.getBidListId();
                break;
            }
        }
        this.mockMvc.perform(get("/bidList/delete/{id}", listId))
                .andDo(print())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
