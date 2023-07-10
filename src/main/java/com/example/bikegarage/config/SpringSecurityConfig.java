package com.example.bikegarage.config;

import com.example.bikegarage.filter.JwtRequestFilter;
import com.example.bikegarage.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*  Deze security is niet de enige manier om het te doen.
    In de andere branch van deze github repo staat een ander voorbeeld
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    // Je kunt dit ook in een aparte configuratie klasse zetten.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }



    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
  //              .requestMatchers("/**").permitAll()

                //-----------Endpoint Bike------------------
                .requestMatchers(HttpMethod.GET, "/bikes").hasAnyRole("USER","TRAINER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/bikes/{id}").hasAnyRole("USER","TRAINER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/bikes").hasAnyRole("USER")
                .requestMatchers(HttpMethod.PUT, "/bikes").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/bikes/{id}").hasAnyRole("USER","ADMIN")

                //-----------Endpoint Rides-----------------
                .requestMatchers(HttpMethod.GET, "/rides").hasAnyRole("USER","TRAINER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/rides/ride-data/{id}").hasAnyRole("USER","TRAINER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/rides/{distance}").hasAnyRole("USER","TRAINER","ADMIN") // navragen hoe dit werkt
                .requestMatchers(HttpMethod.GET, "/rides/{username}").hasAnyRole("USER","TRAINER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/rides").hasAnyRole("USER")
                .requestMatchers(HttpMethod.PUT, "/rides").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/rides/{id}").hasAnyRole("USER","ADMIN")

                //-----------Endpoint Users-----------------
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.POST,"/users/{username}/authorities").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET,"/users").hasAnyRole("USER","TRAINER","ADMIN")
                .requestMatchers(HttpMethod.GET,"/users/{username}").hasAnyRole("USER","TRAINER","ADMIN")
                .requestMatchers(HttpMethod.GET,"/users/{username}/authorities").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.PUT,"/users/{username}").hasAnyRole("USER","TRAINER","ADMIN")
                .requestMatchers(HttpMethod.PUT,"/users/updatepassword/{username}").authenticated()
                .requestMatchers(HttpMethod.DELETE,"/users/{username}").hasAnyRole("USER","TRAINER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/{username}/authorities/{authority}").hasAnyRole("USER","ADMIN")

                // Je mag meerdere paths tegelijk definieren
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}