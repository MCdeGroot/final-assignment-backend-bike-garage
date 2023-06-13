package com.example.bikegarage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    private String password;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private Character gender;
    @Column
    private String emailAdress;
    private Long totalDistanceDriven;
    @OneToMany(mappedBy = "user")
    private ArrayList<Bike> bikes;
    @OneToMany(mappedBy = "user")
    private ArrayList<Ride> rides;










}
