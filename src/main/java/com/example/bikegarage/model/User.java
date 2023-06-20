package com.example.bikegarage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;

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
    private Double totalDistanceDriven;
    @OneToMany(mappedBy = "user")
    private ArrayList<Bike> bikes;
    @OneToMany(mappedBy = "user")
    private ArrayList<Ride> rides;
    private String photoUrl;

}
