package com.example.bikegarage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToOne
    private Bike bike;
    @ManyToOne
    private User user;
    @OneToOne
    private Review review;


    //wellicht strava koppeling nog toevoegen

}
