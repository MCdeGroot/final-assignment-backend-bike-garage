package com.example.bikegarage.dto.output;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Ride;
import java.time.LocalDate;
import java.util.List;

public class UserOutputDto {
    public String username;
    public String firstName;
    public String lastName;
    public Character gender;
    public LocalDate dateOfBirth;
    public Double totalDistanceDriven;
    public List<Bike> bikes;

    public List<Ride> rides;
    public String photoUrl;
}
