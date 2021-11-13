package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "Foo", authorities = {"USER"})
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @MockBean
    @Autowired
    private BidListService bidListService;

    @Test
    public void testGetAllBidList() throws Exception {
        this.mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBidForm() throws Exception {
        this.mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddBidList() throws Exception {
        BidList bidList = new BidList(111, "account1", "type1", 10.0);
        this.mockMvc.perform(post("/bidList/validate")
                        .content(objectMapper.writeValueAsString(bidList))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
//        System.out.println("response = " + response.getContentAsString()); // response is in HTML
    }

    @Test
    public void testUpdateBidList() throws Exception {
        BidList oldBidList = new BidList(222, "account2", "type2", 22.0);
        this.mockMvc.perform(post("/bidList/update/222")
                        .content(objectMapper.writeValueAsString(oldBidList))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteBid() throws Exception {
        BidList bidList = new BidList("account3", "type3", 33.0);
        bidListService.insertBidList(bidList);
        List<BidList> all = bidListService.findAll();
        int id = 0;
        for (BidList list : all) {
            if (list.getAccount().equals("account3")) {
                id = list.getBidListId();
                break;
            }
        }
        this.mockMvc.perform(get("/bidList/delete/{id}", id))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn();

    }

}
