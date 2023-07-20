package com.example.bikegarage.dto.input;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.User;
import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Lazy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class RideInputDto {
    @NotNull(message = "Title is required!")
    @Size(max = 50, message = "The title can only be 50 characters long.")
    public String titleRide;
    @Size(max = 400, message = "The subtitle can only be 400 characters long.")
    public String subTitleRide;
    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be higher than zero")
    public Double distance;
    public Duration timeRide;
    @Positive(message = "Average power must be positive")
    public Long averagePower;
    @Positive(message = "Normalized power must be positive")
    public Long normalizedPower;
    @NotNull
    @PastOrPresent(message = "The date can't be in the future")
    public LocalDateTime date;
    @Lazy(false)
    public Bike bike;
    @NotBlank(message = "User must be logged in!")
    public String username;

}
