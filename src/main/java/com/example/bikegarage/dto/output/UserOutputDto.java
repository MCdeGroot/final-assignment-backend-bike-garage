package com.example.bikegarage.dto.output;
import com.example.bikegarage.model.Authority;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Ride;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserOutputDto {
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public Character gender;
    public LocalDate dateOfBirth;
    public Double totalDistanceDriven;
    public List<Bike> bikes;

    public List<Ride> rides;
    public String photoUrl;
    public Set<Authority> authorities;
    public Boolean enabled;
    public String apikey;


}
