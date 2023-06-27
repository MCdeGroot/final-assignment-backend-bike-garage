package com.example.bikegarage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(nullable = false, length = 255)
    private String password;
    @Column
    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Character gender;
    private LocalDate dateOfBirth;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Bike> bikes;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Ride> rides;
    private String photoUrl;

    //Security and Authorithy
    @Column(nullable = false)
    private boolean enabled = true;

    @Column
    private String apikey;
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    //getters setters
    public Set<Authority> getAuthorities() {
        return authorities;
    }
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }
}


