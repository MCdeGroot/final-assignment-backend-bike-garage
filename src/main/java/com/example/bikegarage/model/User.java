package com.example.bikegarage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Character gender;
    @Column
    private String email;
    private LocalDate dateOfBirth;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Bike> bikes;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Ride> rides;
    private String photoUrl;

}


