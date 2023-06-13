package com.example.bikegarage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private Long distance;
    private Date date;

    //dit is mij nog niet helemaal duidelijk iedere ride heeft maar 1 bike.
    @ManyToOne
    private Bike bike;
    @ManyToOne
    private User user;

    //wellicht strava koppeling nog toevoegen

}