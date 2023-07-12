package com.example.bikegarage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue
    private Long id;
    private String titleRide;
    private String subTitleRide;
    private Double distance;
    private LocalDateTime date;
    private Long averagePower;
    private Long normalizedPower;
    @Column(name = "time_ride")
    private Duration timeRide;
    @ManyToOne
    @JsonIgnore
    private Bike bike;
    @ManyToOne
    @JsonIgnore
    private User user;
    @OneToOne
    @JsonIgnore
    private Review review;


    //wellicht strava koppeling nog toevoegen

}
