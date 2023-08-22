package com.example.bikegarage.service;

import com.example.bikegarage.dto.input.ReviewInputDto;
import com.example.bikegarage.dto.output.ReviewOutputDto;
import com.example.bikegarage.exception.ForbiddenException;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.model.Review;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import com.example.bikegarage.repository.ReviewRepository;
import com.example.bikegarage.repository.RideRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RideRepository rideRepository;

    public ReviewService(ReviewRepository reviewRepository, RideRepository rideRepository) {
        this.reviewRepository = reviewRepository;
        this.rideRepository = rideRepository;
    }

    public ReviewOutputDto getReviewOnRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RecordNotFoundException("Ride with id-number " + rideId + " cannot be found"));
        return transferReviewModelToReviewOutputDto(ride.getReview());
    }

    public ReviewOutputDto createReview(ReviewInputDto reviewInputDto, Long rideId) throws RecordNotFoundException {
        // authenticatie voor een ingelogde user om te kijken of hij dit wel mag wijzigen.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            boolean hasAuthority = authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_TRAINER"));
            if (!hasAuthority) {
                throw new ForbiddenException("Looks like you don't have the right authority to do this.");
            }
        }
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RecordNotFoundException("Ride with id " + rideId + " cannot be found"));
        User trainer = ride.getUser().getTrainer();
        if (trainer == null || !trainer.getUsername().equals(authentication.getName())) {
            throw new ForbiddenException("You are not authorized to create a review for this ride.");
        }
        Review review = transferReviewInputDtoToReviewModel(reviewInputDto);
        review.setRide(ride);
        reviewRepository.save(review);
        ride.setReview(review);
        rideRepository.save(ride);
        return transferReviewModelToReviewOutputDto(review);
    }


    public ReviewOutputDto updateReview(ReviewInputDto reviewInputDto, Long rideId) throws RecordNotFoundException {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RecordNotFoundException("Ride with id-number " + rideId + " cannot be found"));
        Review review = ride.getReview();
        if (reviewInputDto.rating != null) {
            review.setRating(reviewInputDto.rating);
        }
        if (reviewInputDto.commentDescription != null) {
            review.setCommentDescription(reviewInputDto.commentDescription);
        }
        reviewRepository.save(review);
        return transferReviewModelToReviewOutputDto(review);
    }

    public String deleteReview(Long rideId) throws RecordNotFoundException {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RecordNotFoundException("Ride with id-number " + rideId + " cannot be found"));
        reviewRepository.delete(ride.getReview());
        return "Well well I hope you know what you're doing, because you just removed your review!";
    }

    public Review transferReviewInputDtoToReviewModel(ReviewInputDto reviewInputDto) {
        Review review = new Review();
        review.setRating(reviewInputDto.rating);
        review.setCommentDescription(reviewInputDto.commentDescription);
        return review;
    }

    public ReviewOutputDto transferReviewModelToReviewOutputDto(Review review) {
        ReviewOutputDto reviewOutputDto = new ReviewOutputDto();
        reviewOutputDto.rating = review.getRating();
        reviewOutputDto.commentDescription = review.getCommentDescription();
        return reviewOutputDto;
    }
}
