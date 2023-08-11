package com.example.bikegarage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bikegarage.dto.input.ReviewInputDto;
import com.example.bikegarage.dto.output.ReviewOutputDto;
import com.example.bikegarage.exception.ForbiddenException;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.File;
import com.example.bikegarage.model.Review;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import com.example.bikegarage.repository.ReviewRepository;
import com.example.bikegarage.repository.RideRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReviewService.class})
@ExtendWith(SpringExtension.class)
class ReviewServiceTest {
    @MockBean
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private RideRepository rideRepository;

    /**
     * Method under test: {@link ReviewService#getReviewOnRide(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetReviewOnRide() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.bikegarage.model.Review.getRating()" because "review" is null
        //       at com.example.bikegarage.service.ReviewService.transferReviewModelToReviewOutputDto(ReviewService.java:88)
        //       at com.example.bikegarage.service.ReviewService.getReviewOnRide(ReviewService.java:29)
        //   See https://diff.blue/R013 to resolve this issue.

        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Ride()));
        reviewService.getReviewOnRide(1L);
    }

    /**
     * Method under test: {@link ReviewService#getReviewOnRide(Long)}
     */
    @Test
    void testGetReviewOnRide2() {
        Ride ride = new Ride();
        ride.setReview(new Review());
        Optional<Ride> ofResult = Optional.of(ride);
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ReviewOutputDto actualReviewOnRide = reviewService.getReviewOnRide(1L);
        assertNull(actualReviewOnRide.commentDescription);
        assertNull(actualReviewOnRide.rating);
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReviewService#getReviewOnRide(Long)}
     */
    @Test
    void testGetReviewOnRide3() {
        Ride ride = mock(Ride.class);
        when(ride.getReview()).thenThrow(new ForbiddenException("An error occurred"));
        Optional<Ride> ofResult = Optional.of(ride);
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ForbiddenException.class, () -> reviewService.getReviewOnRide(1L));
        verify(rideRepository).findById(Mockito.<Long>any());
        verify(ride).getReview();
    }

    /**
     * Method under test: {@link ReviewService#getReviewOnRide(Long)}
     */
    @Test
    void testGetReviewOnRide4() {
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        new ForbiddenException("An error occurred");
        assertThrows(RecordNotFoundException.class, () -> reviewService.getReviewOnRide(1L));
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReviewService#createReview(ReviewInputDto, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateReview() throws RecordNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getAuthorities()" because "authentication" is null
        //       at com.example.bikegarage.service.ReviewService.createReview(ReviewService.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        ReviewInputDto reviewInputDto = new ReviewInputDto();
        reviewInputDto.commentDescription = "Comment Description";
        reviewInputDto.rating = 10.0d;
        reviewService.createReview(reviewInputDto, 1L);
    }

    /**
     * Method under test: {@link ReviewService#createReview(ReviewInputDto, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateReview2() throws RecordNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getAuthorities()" because "authentication" is null
        //       at com.example.bikegarage.service.ReviewService.createReview(ReviewService.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        ReviewInputDto reviewInputDto = mock(ReviewInputDto.class);
        reviewInputDto.commentDescription = "Comment Description";
        reviewInputDto.rating = 10.0d;
        reviewService.createReview(reviewInputDto, 1L);
    }

    /**
     * Method under test: {@link ReviewService#updateReview(ReviewInputDto, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateReview() throws RecordNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.bikegarage.model.Review.setRating(java.lang.Double)" because "review" is null
        //       at com.example.bikegarage.service.ReviewService.updateReview(ReviewService.java:64)
        //   See https://diff.blue/R013 to resolve this issue.

        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Ride()));
        ReviewInputDto reviewInputDto = new ReviewInputDto();
        reviewInputDto.commentDescription = "Comment Description";
        reviewInputDto.rating = 10.0d;
        reviewService.updateReview(reviewInputDto, 1L);
    }

    /**
     * Method under test: {@link ReviewService#updateReview(ReviewInputDto, Long)}
     */
    @Test
    void testUpdateReview2() throws RecordNotFoundException {
        when(reviewRepository.save(Mockito.<Review>any())).thenReturn(new Review());
        LocalDateTime date = LocalDate.of(1970, 1, 1).atStartOfDay();
        Bike bike = new Bike();
        User user = new User();
        Review review = new Review();
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Ride(1L, "Dr", "Dr", 10.0d, date,
                1L, 1L, null, bike, user, review, new File("foo.txt", "text/plain", "https://example.org/example"))));
        ReviewInputDto reviewInputDto = new ReviewInputDto();
        reviewInputDto.commentDescription = "Comment Description";
        reviewInputDto.rating = 10.0d;
        ReviewOutputDto actualUpdateReviewResult = reviewService.updateReview(reviewInputDto, 1L);
        assertEquals("Comment Description", actualUpdateReviewResult.commentDescription);
        assertEquals(10.0d, actualUpdateReviewResult.rating.doubleValue());
        verify(reviewRepository).save(Mockito.<Review>any());
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReviewService#updateReview(ReviewInputDto, Long)}
     */
    @Test
    void testUpdateReview3() throws RecordNotFoundException {
        Ride ride = mock(Ride.class);
        when(ride.getReview()).thenThrow(new ForbiddenException("An error occurred"));
        Optional<Ride> ofResult = Optional.of(ride);
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ReviewInputDto reviewInputDto = new ReviewInputDto();
        reviewInputDto.commentDescription = "Comment Description";
        reviewInputDto.rating = 10.0d;
        assertThrows(ForbiddenException.class, () -> reviewService.updateReview(reviewInputDto, 1L));
        verify(rideRepository).findById(Mockito.<Long>any());
        verify(ride).getReview();
    }

    /**
     * Method under test: {@link ReviewService#updateReview(ReviewInputDto, Long)}
     */
    @Test
    void testUpdateReview4() throws RecordNotFoundException {
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        new ForbiddenException("An error occurred");
        ReviewInputDto reviewInputDto = new ReviewInputDto();
        reviewInputDto.commentDescription = "Comment Description";
        reviewInputDto.rating = 10.0d;
        assertThrows(RecordNotFoundException.class, () -> reviewService.updateReview(reviewInputDto, 1L));
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReviewService#deleteReview(Long)}
     */
    @Test
    void testDeleteReview() throws RecordNotFoundException {
        doNothing().when(reviewRepository).delete(Mockito.<Review>any());
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Ride()));
        assertEquals("Well well I hope you know what you're doing, because you just removed your review!",
                reviewService.deleteReview(1L));
        verify(reviewRepository).delete(Mockito.<Review>any());
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReviewService#deleteReview(Long)}
     */
    @Test
    void testDeleteReview2() throws RecordNotFoundException {
        doThrow(new ForbiddenException("An error occurred")).when(reviewRepository).delete(Mockito.<Review>any());
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Ride()));
        assertThrows(ForbiddenException.class, () -> reviewService.deleteReview(1L));
        verify(reviewRepository).delete(Mockito.<Review>any());
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReviewService#deleteReview(Long)}
     */
    @Test
    void testDeleteReview3() throws RecordNotFoundException {
        Ride ride = mock(Ride.class);
        when(ride.getReview()).thenThrow(new ForbiddenException("An error occurred"));
        Optional<Ride> ofResult = Optional.of(ride);
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ForbiddenException.class, () -> reviewService.deleteReview(1L));
        verify(rideRepository).findById(Mockito.<Long>any());
        verify(ride).getReview();
    }

    /**
     * Method under test: {@link ReviewService#deleteReview(Long)}
     */
    @Test
    void testDeleteReview4() throws RecordNotFoundException {
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        new ForbiddenException("An error occurred");
        assertThrows(RecordNotFoundException.class, () -> reviewService.deleteReview(1L));
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ReviewService#transferReviewInputDtoToReviewModel(ReviewInputDto)}
     */
    @Test
    void testTransferReviewInputDtoToReviewModel() {
        ReviewInputDto reviewInputDto = new ReviewInputDto();
        reviewInputDto.commentDescription = "Comment Description";
        reviewInputDto.rating = 10.0d;
        Review actualTransferReviewInputDtoToReviewModelResult = reviewService
                .transferReviewInputDtoToReviewModel(reviewInputDto);
        assertEquals("Comment Description", actualTransferReviewInputDtoToReviewModelResult.getCommentDescription());
        assertEquals(10.0d, actualTransferReviewInputDtoToReviewModelResult.getRating().doubleValue());
    }

    /**
     * Method under test: {@link ReviewService#transferReviewInputDtoToReviewModel(ReviewInputDto)}
     */
    @Test
    void testTransferReviewInputDtoToReviewModel2() {
        ReviewInputDto reviewInputDto = mock(ReviewInputDto.class);
        reviewInputDto.commentDescription = "Comment Description";
        reviewInputDto.rating = 10.0d;
        Review actualTransferReviewInputDtoToReviewModelResult = reviewService
                .transferReviewInputDtoToReviewModel(reviewInputDto);
        assertEquals("Comment Description", actualTransferReviewInputDtoToReviewModelResult.getCommentDescription());
        assertEquals(10.0d, actualTransferReviewInputDtoToReviewModelResult.getRating().doubleValue());
    }

    /**
     * Method under test: {@link ReviewService#transferReviewModelToReviewOutputDto(Review)}
     */
    @Test
    void testTransferReviewModelToReviewOutputDto() {
        ReviewOutputDto actualTransferReviewModelToReviewOutputDtoResult = reviewService
                .transferReviewModelToReviewOutputDto(new Review());
        assertNull(actualTransferReviewModelToReviewOutputDtoResult.commentDescription);
        assertNull(actualTransferReviewModelToReviewOutputDtoResult.rating);
    }

    /**
     * Method under test: {@link ReviewService#transferReviewModelToReviewOutputDto(Review)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testTransferReviewModelToReviewOutputDto2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.bikegarage.model.Review.getRating()" because "review" is null
        //       at com.example.bikegarage.service.ReviewService.transferReviewModelToReviewOutputDto(ReviewService.java:88)
        //   See https://diff.blue/R013 to resolve this issue.

        reviewService.transferReviewModelToReviewOutputDto(null);
    }

    /**
     * Method under test: {@link ReviewService#transferReviewModelToReviewOutputDto(Review)}
     */
    @Test
    void testTransferReviewModelToReviewOutputDto3() {
        Review review = mock(Review.class);
        when(review.getRating()).thenReturn(10.0d);
        when(review.getCommentDescription()).thenReturn("Comment Description");
        ReviewOutputDto actualTransferReviewModelToReviewOutputDtoResult = reviewService
                .transferReviewModelToReviewOutputDto(review);
        assertEquals("Comment Description", actualTransferReviewModelToReviewOutputDtoResult.commentDescription);
        assertEquals(10.0d, actualTransferReviewModelToReviewOutputDtoResult.rating.doubleValue());
        verify(review).getRating();
        verify(review).getCommentDescription();
    }
}

