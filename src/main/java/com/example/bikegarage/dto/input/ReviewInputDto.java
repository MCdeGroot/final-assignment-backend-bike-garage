package com.example.bikegarage.dto.input;

import jakarta.validation.constraints.*;

public class ReviewInputDto {

    @NotNull
    @DecimalMin(value = "1.0", inclusive = true)
    @DecimalMax(value = "10.0", inclusive = true)
    public Double rating;
    public String commentDescription;
}
