package com.example.bikegarage.dto.input;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.User;
import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Lazy;

import java.util.Date;

public class RideInputDto {
    @NotBlank(message = "Title is required!")
    public String titleRide;
    @Size(max = 200, message = "The subtitle can only be 200 characters long.")
    public String subTitleRide;
    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be higher than zero")
    public Double distance;
    @NotNull
    @PastOrPresent(message = "The date can't be in the future")
    public Date date;
    @Lazy(false)
    public Bike bike;

    public User user;

}
