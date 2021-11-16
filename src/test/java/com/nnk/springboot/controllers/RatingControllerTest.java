package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
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
public class RatingControllerTest {
    private int id = 0;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    @BeforeAll
    public void init() {
        Rating rating = new Rating("m", "s", "fitch rating", 1);
        ratingService.insertRating(rating);
        for (Rating rate : ratingService.findAll()) {
            if (rate.getMoodysRating().equals("m")) {
                id = rate.getId();
                break;
            }
        }
    }

    @AfterAll
    public void clean() {
        ratingRepository.deleteAll();
    }

    @Test
    public void testBidListController() throws Exception {
        // FindAll
        this.mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk());

        // Get add form
        this.mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk());

        // Add
        this.mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "new moody")
                        .param("sendPRating", "new sendP")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "100")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(status().isFound())
                .andReturn();

        // Get update form
        this.mockMvc.perform(get("/rating/update/{id}", id)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk());

        // Update
        this.mockMvc.perform(post("/rating/update/{id}", id)
                        .param("moodysRating", "update moody")
                        .param("sendPRating", "update sendP")
                        .param("fitchRating", "fitchRating")
                        .param("orderNumber", "100")
                        .accept(MediaType.ALL))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(status().isFound())
                .andReturn();

        // Delete
        this.mockMvc.perform(get("/rating/delete/{id}", id))
                .andDo(print())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(status().isFound())
                .andReturn();
    }

}
