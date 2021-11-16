package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "Foo", authorities = {"USER"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidListControllerTest {
    private int id = 0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListService bidListService;

    @Autowired
    private BidListRepository bidListRepository;

    @BeforeAll
    public void init() {
        BidList bidList = new BidList("init account", "type", 10.0);
        bidListService.insertBidList(bidList);
        for (BidList list : bidListService.findAll()) {
            if (list.getAccount().equals("init account")) {
                id = list.getBidListId();
                break;
            }
        }
    }

    @AfterAll
    public void clean() {
        bidListRepository.deleteAll();
    }

    @Test
    public void testBidListController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());

        // Add
        this.mockMvc.perform(post("/bidList/validate")
                        .param("account", "account")
                        .param("type", "type")
                        .param("bidQuantity", "20.0")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(status().isFound())
                .andReturn();


        // Get update form
        this.mockMvc.perform(get("/bidList/update/{id}", id)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());

        // Update
        this.mockMvc.perform(post("/bidList/update/{id}", id)
                        .param("account", "account2")
                        .param("type", "type2")
                        .param("bidQuantity", "22.0")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(status().isFound())
                .andReturn();

        // Delete
        this.mockMvc.perform(get("/bidList/delete/{id}", id))
                .andDo(print())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
