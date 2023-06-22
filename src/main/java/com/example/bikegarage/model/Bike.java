package com.example.bikegarage.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
// Adding constructors, check later if these are the constructors I need.
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bikes")
public class Bike {

    @Id
    @GeneratedValue
    private Long id;
    private Long frameNumber;
    private String brand;
    private String model;
    private String name;
    @Enumerated(EnumType.STRING)
    private BikeType bikeType;
    @OneToMany (mappedBy = "bike")
    @JsonIgnore
    private Set<Part> bikeParts;
    @OneToMany (mappedBy = "bike")
    @JsonIgnore
    private List<Ride> rides;
    @ManyToOne
    private User user;

// private ?? imageBike;


}
