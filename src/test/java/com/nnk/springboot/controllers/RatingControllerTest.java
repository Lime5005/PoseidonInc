package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RatingService ratingService;

    @Test
    public void testBidListController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk());

        // Add
        Rating rating = new Rating(111, "moody", "sendP", "fitch", 10);
        this.mockMvc.perform(post("/rating/validate")
                        .content(objectMapper.writeValueAsString(rating))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Update
        Rating newRating = new Rating(111, "moody rating", "sendP rating", "fitch rating", 1);
        this.mockMvc.perform(post("/rating/update/111")
                        .content(objectMapper.writeValueAsString(newRating))
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        // Delete
        Rating ratingToDelete = new Rating(1, "m", "s", "fitch rating", 1);
        ratingService.insertRating(ratingToDelete);
        Integer ratingId = 0;
        for (Rating rate : ratingService.findAll()) {
            if (rate.getMoodysRating().equals("m")) {
                ratingId = rate.getId();
                break;
            }
        }
        this.mockMvc.perform(get("/rating/delete/{id}", ratingId))
                .andDo(print())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
