package com.example.bikegarage.controller;

import com.example.bikegarage.dto.input.ReviewInputDto;
import com.example.bikegarage.dto.output.BikeOutputDto;
import com.example.bikegarage.dto.output.ReviewOutputDto;
import com.example.bikegarage.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/{rideId}/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<ReviewOutputDto> getReviewOnRide(@PathVariable Long rideId) {
        return new ResponseEntity<>(reviewService.getReviewOnRide(rideId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createReview(@Valid @RequestBody ReviewInputDto reviewInputDto, @PathVariable Long rideId, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            ReviewOutputDto reviewOutputDto = reviewService.createReview(reviewInputDto, rideId);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + reviewOutputDto).toUriString());
            return ResponseEntity.created(uri).body(reviewOutputDto);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateReview(@Valid @RequestBody ReviewInputDto
                                                       reviewInputDto, @PathVariable Long rideId, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        return new ResponseEntity<>(reviewService.updateReview(reviewInputDto, rideId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable Long rideId) {
        reviewService.deleteReview(rideId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

