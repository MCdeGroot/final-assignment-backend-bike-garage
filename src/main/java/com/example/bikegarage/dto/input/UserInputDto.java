package com.example.bikegarage.dto.input;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserInputDto {
    @NotBlank(message = "Username is required")
    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
    public Character gender;
    @JsonFormat(pattern="yyyy-MM-dd")
    public LocalDate dateOfBirth;
    public String photoUrl;
    public Boolean enabled;
    public String apikey;
}
