package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public void insertRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public Boolean updateRating(int id, Rating rating) {
        boolean updated = false;
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            Rating newRating = optionalRating.get();
            newRating.setMoodysRating(rating.getMoodysRating());
            newRating.setSendPRating(rating.getSendPRating());
            newRating.setFitchRating(rating.getFitchRating());
            newRating.setOrderNumber(rating.getOrderNumber());
            ratingRepository.save(newRating);
            updated = true;
        }
        return updated;
    }

    @Override
    public Rating findById(int id) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        return optionalRating.orElse(null);
    }

    @Override
    public void deleteById(int id) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        optionalRating.ifPresent(ratingRepository::delete);
    }
}
