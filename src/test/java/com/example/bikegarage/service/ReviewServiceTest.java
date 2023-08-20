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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReviewOnRide() {
        Ride ride = new Ride();
        Review review = new Review();
        ReviewOutputDto reviewOutputDto = new ReviewOutputDto();
        ride.setReview(review);

        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));

        ReviewOutputDto result = reviewService.getReviewOnRide(1L);

        assertNotNull(result);
        assertEquals(reviewOutputDto, result);
    }

//    @Test
//    void testCreateReview() throws RecordNotFoundException {
//        // Mocking authentication with ROLE_TRAINER authority
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_TRAINER"));
//        Authentication authentication = new UsernamePasswordAuthenticationToken("testTrainer", "password", authorities);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Set up Ride
//        User user = new User();
//        user.setUsername("testUser"); // Stel de gebruikersnaam in
//        SecurityContextHolder.getContext().setAuthentication(
//                new UsernamePasswordAuthenticationToken(user, "password", authorities)
//        );
//        user.setTrainer(new User());
//
//        Ride ride = new Ride();
//        ride.setUser(user);
//        // Rest van de Ride-setup hier
//
//        // Set up ReviewInputDto
//        ReviewInputDto reviewInputDto = new ReviewInputDto();
//        reviewInputDto.setRating(5.0);
//        reviewInputDto.setCommentDescription("Great ride!");
//
//        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));
//        when(reviewRepository.save(any())).thenReturn(new Review());
//
//        ReviewOutputDto result = reviewService.createReview(reviewInputDto, 1L);
//
//        assertNotNull(result);
//        verify(reviewRepository, times(1)).save(any());
//    }

    @Test
    void testCreateReviewWithoutAuthority() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Ride ride = new Ride();
        ReviewInputDto reviewInputDto = new ReviewInputDto();

        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));

        assertThrows(ForbiddenException.class, () -> reviewService.createReview(reviewInputDto, 1L));
    }

    @Test
    void testUpdateReview() {
        Ride ride = new Ride();
        Review review = new Review();
        ride.setReview(review);
        ReviewInputDto reviewInputDto = new ReviewInputDto();
        reviewInputDto.setRating(4.0);

        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));
        when(reviewRepository.save(any())).thenReturn(review);

        ReviewOutputDto result = reviewService.updateReview(reviewInputDto, 1L);

        assertNotNull(result);
        assertEquals(review.getRating(), result.getRating());
        verify(reviewRepository, times(1)).save(any());
    }

    @Test
    void testDeleteReview() {
        Ride ride = new Ride();
        Review review = new Review();
        ride.setReview(review);

        when(rideRepository.findById(anyLong())).thenReturn(Optional.of(ride));

        String result = reviewService.deleteReview(1L);

        assertNotNull(result);
        assertEquals("Well well I hope you know what you're doing, because you just removed your review!", result);
        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    void testTransferReviewInputDtoToReviewModel() {
        ReviewInputDto reviewInputDto = new ReviewInputDto();
        reviewInputDto.setRating(4.0);
        reviewInputDto.setCommentDescription("Test comment");

        Review result = reviewService.transferReviewInputDtoToReviewModel(reviewInputDto);

        assertNotNull(result);
        assertEquals(reviewInputDto.getRating(), result.getRating());
        assertEquals(reviewInputDto.getCommentDescription(), result.getCommentDescription());
    }

    @Test
    void testTransferReviewModelToReviewOutputDto() {
        Review review = new Review();
        review.setRating(4.0);
        review.setCommentDescription("Test comment");

        ReviewOutputDto result = reviewService.transferReviewModelToReviewOutputDto(review);

        assertNotNull(result);
        assertEquals(review.getRating(), result.getRating());
        assertEquals(review.getCommentDescription(), result.getCommentDescription());
    }
}