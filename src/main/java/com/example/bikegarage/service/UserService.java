package com.example.bikegarage.service;

import com.example.bikegarage.dto.input.AddTrainerInputDTO;
import com.example.bikegarage.dto.input.PasswordInputDto;
import com.example.bikegarage.dto.input.UserInputDto;
import com.example.bikegarage.dto.output.UserOutputDto;
import com.example.bikegarage.exception.ForbiddenException;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.exception.UsernameNotFoundException;
import com.example.bikegarage.model.Authority;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import com.example.bikegarage.repository.UserRepository;
import com.example.bikegarage.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    @Lazy//loop voorkomen
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("#username==authentication.getName()")
    public UserOutputDto getUserByUsername(String username) throws RecordNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return transferUserModelToUserOutputDto(user);
        } else {
            throw new RecordNotFoundException("There is no user found with username " + username + " in the database!");
        }
    }

    public User getUser(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        return user;
    }

    public List<UserOutputDto> getAllUsers() throws RecordNotFoundException {
        List<UserOutputDto> allUserOutputDto = new ArrayList<>();
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RecordNotFoundException("There are no users in the database!");
        }
        for (User user : users
        ) {
            allUserOutputDto.add(transferUserModelToUserOutputDto(user));
        }
        return allUserOutputDto;
    }

    public List<UserOutputDto> getAllCyclistsOfTrainer(String trainerUsername) throws RecordNotFoundException {
        List<UserOutputDto> allCyclistsOutputDto = new ArrayList<>();
        Optional<User> trainerOptional = userRepository.findByUsername(trainerUsername);
        User trainer = trainerOptional.orElseThrow(() -> new UsernameNotFoundException(trainerUsername));
        List<User> cyclists = userRepository.findCyclistsByTrainer(trainer);
        if (cyclists.isEmpty()) {
            throw new RecordNotFoundException("Looks like this user doesn't train other cyclists!");
        }
        for (User cyclist : cyclists
        ) {
            allCyclistsOutputDto.add(transferUserModelToUserOutputDto(cyclist));
        }
        return allCyclistsOutputDto;
    }

    public List<String> getAllUsernames() throws RecordNotFoundException {
        List<String> usernames = new ArrayList<>();
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RecordNotFoundException("There are no users in the database!");
        }
        for (User user : users
        ) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }


    public UserOutputDto createUser(UserInputDto userInputDto) {
        userInputDto.setApikey(RandomStringGenerator.generateAlphaNumeric(20));
        User user = transferUserInputDtoToUser(userInputDto);
        user.addAuthority(new Authority(user.getUsername(), "ROLE_USER"));
        user.setPassword(passwordEncoder.encode(userInputDto.password));
        userRepository.save(user);

        return transferUserModelToUserOutputDto(user);
    }

    public UserOutputDto updateUser(String username, UserInputDto userInputDto) throws RecordNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + username + " in the database!"));
        User userUpdate = updateUserInputDtoToUser(userInputDto, user);
        userRepository.save(userUpdate);
        return transferUserModelToUserOutputDto(userUpdate);
    }

    public UserOutputDto assignTrainer(String cyclistUsername, AddTrainerInputDTO addTrainerInputDTO) throws RecordNotFoundException {
        Optional<User> cyclistOptional = userRepository.findByUsername(cyclistUsername);
        User cyclist = cyclistOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + cyclistUsername + " in the database!"));
        if (cyclist.getTrainer() != null) {
            throw new IllegalStateException("The user already has a trainer assigned.");
        }
        Optional<User> trainerOptional = userRepository.findByUsername(addTrainerInputDTO.trainerUsername);
        User trainer = trainerOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + addTrainerInputDTO.trainerUsername + " in the database!"));

        cyclist.setTrainer(trainer);
        trainer.addAuthority(new Authority(trainer.getUsername(), "ROLE_TRAINER"));
        userRepository.save(cyclist);
        userRepository.save(trainer);

        return transferUserModelToUserOutputDto(cyclist);
    }

    @PreAuthorize("#username==authentication.getName()")
    public String updatePassword(String username, PasswordInputDto passwordInputDto) throws RecordNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + username + " in the database!"));

        // authenticatie voor een ingelogde user om te kijken of hij dit wel mag wijzigen.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            boolean hasAuthority = authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
            if (!hasAuthority) {
                throw new ForbiddenException("Looks like you don't have the right authority to do this.");
            }
        }
        user.setPassword(passwordEncoder.encode(passwordInputDto.newPassword));
        userRepository.save(user);
        return "We did it! We changed your password";

    }

    public String deleteUser(String username) throws RecordNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + username + " in the database!"));
        userRepository.delete(user);
        return "Well well I hope you know what you're doing, because you just removed " + user.getUsername() + "!";
    }

    //security and authorization
    public Set<Authority> getAuthorities(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        UserOutputDto userDto = transferUserModelToUserOutputDto(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    //transfer methods
    public User transferUserInputDtoToUser(UserInputDto userInputDto) {
        User user = new User();
        user.setUsername(userInputDto.username);
        user.setPassword(userInputDto.password);
        user.setFirstName(userInputDto.firstName);
        user.setLastName(userInputDto.lastName);
        user.setGender(userInputDto.gender);
        user.setEmail(userInputDto.email);
        user.setDateOfBirth(userInputDto.dateOfBirth);
        user.setPhotoUrl(userInputDto.photoUrl);

        return user;
    }

    public UserOutputDto transferUserModelToUserOutputDto(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.username = user.getUsername();
        userOutputDto.email = user.getEmail();
        userOutputDto.firstName = user.getFirstName();
        userOutputDto.lastName = user.getLastName();
        userOutputDto.gender = user.getGender();
        userOutputDto.dateOfBirth = user.getDateOfBirth();
        userOutputDto.photoUrl = user.getPhotoUrl();
        userOutputDto.totalDistanceDriven = getTotalDistanceDriven(user);
        userOutputDto.rides = user.getRides();
        userOutputDto.bikes = user.getBikes();
        userOutputDto.enabled = user.isEnabled();
        userOutputDto.apikey = user.getApikey();
        userOutputDto.authorities = user.getAuthorities();
        userOutputDto.cyclists = user.getCyclists();
        if (user.getTrainer() != null) {
            userOutputDto.trainerUsername = user.getTrainer().getUsername();
        }

        return userOutputDto;
    }

    public User updateUserInputDtoToUser(UserInputDto userInputDto, User user) {
        // Zorg ervoor dat de username niet wordt gewijzigd. Extra controle, kan ook in de frontend worden opgevangen.
        user.setUsername(user.getUsername());
        if (userInputDto.password != null) {
            user.setPassword(userInputDto.password);
        }
        if (userInputDto.firstName != null) {
            user.setFirstName(userInputDto.firstName);
        }
        if (userInputDto.lastName != null) {
            user.setLastName(userInputDto.lastName);
        }
        if (userInputDto.gender != null) {
            user.setGender(userInputDto.gender);
        }
        if (userInputDto.email != null) {
            user.setEmail(userInputDto.email);
        }
        if (userInputDto.dateOfBirth != null) {
            user.setDateOfBirth(userInputDto.dateOfBirth);
        }
        if (userInputDto.photoUrl != null) {
            user.setPhotoUrl(userInputDto.photoUrl);
        }
        return user;
    }

    public Double getTotalDistanceDriven(User user) {
        Double totalDistanceDriven = 0.0;
        List<Ride> rides = user.getRides();
        if (rides != null) {
            for (Ride ride : rides
            ) {
                totalDistanceDriven += ride.getDistance();
            }
        }
        return totalDistanceDriven;
    }


}
