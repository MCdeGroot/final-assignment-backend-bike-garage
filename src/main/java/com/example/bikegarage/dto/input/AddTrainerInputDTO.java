package com.example.bikegarage.dto.input;

import jakarta.validation.constraints.NotNull;

public class AddTrainerInputDTO {
    @NotNull
    public String trainerUsername;
}
