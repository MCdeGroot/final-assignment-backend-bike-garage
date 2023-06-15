package com.example.bikegarage.dto.output;

import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.User;

import java.util.Date;

public class RideOutputDto {
    public Long id;

    public String titleRide;
    public String subTitleRide;
    public Double distance;
    public Date date;
    public Bike bike;
    public User user;
}
