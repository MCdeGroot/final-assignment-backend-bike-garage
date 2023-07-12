package com.example.bikegarage.dto.output;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Review;
import com.example.bikegarage.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class RideOutputDto {
    public Long id;

    public String titleRide;
    public String subTitleRide;
    public Double distance;
    public LocalDateTime date;
    public Duration timeRide;
    public Long averagePower;
    public Long normalizedPower;
    public Bike bike;
    public User user;
    public Double reviewRating;
}
