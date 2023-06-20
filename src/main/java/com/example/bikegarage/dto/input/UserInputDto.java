package com.example.bikegarage.dto.input;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;


public class UserInputDto {
    @NotBlank(message = "Username is required")
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public Character gender;
    public String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    public LocalDate dateOfBirth;
    public String photoUrl;
}
