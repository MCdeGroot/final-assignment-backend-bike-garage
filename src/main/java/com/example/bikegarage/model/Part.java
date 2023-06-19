package com.example.bikegarage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bike_parts")
public class Part {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private PartType partType;
    private Double currentDistanceDriven = 0.0;
    private Double maxDistance;
    @ManyToOne
    private Bike bike;
    private LocalDateTime installationDate;

    //methods

    //Methods

    public void updateCurrentDistanceDriven(Ride ride) {
        Double distance = ride.getDistance();
        Double newTotalDistanceDriven = getCurrentDistanceDriven() + distance;
        setCurrentDistanceDriven(newTotalDistanceDriven);
    }

}


