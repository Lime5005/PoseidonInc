package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;


    @AfterAll
    public void cleanRating() {
        ratingRepository.deleteAll();
    }

    @Test
    public void testRatingService() {
         Rating rating = new Rating("m1", "s1", "f1", 1);
        // Save
        ratingService.insertRating(rating);
        List<Rating> ratings = ratingService.findAll();
        int size = ratings.size();
        assertTrue(size > 0);

        // Update
        Integer id = rating.getId();
        rating.setMoodysRating("m2");
        rating.setSendPRating("s2");
        rating.setFitchRating("f2");
        rating.setOrderNumber(2);
        ratingService.updateRating(id, rating);
        assertEquals("m2", rating.getMoodysRating());

        // Find
        Rating byId = ratingService.findById(id);
        assertNotNull(byId);

        // Delete
        ratingService.deleteById(id);
        Rating rating1 = ratingService.findById(id);
        assertNull(rating1);
    }


}
