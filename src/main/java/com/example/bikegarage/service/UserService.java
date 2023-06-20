package com.example.bikegarage.service;

import com.example.bikegarage.dto.input.UserInputDto;
import com.example.bikegarage.dto.output.UserOutputDto;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.User;
import com.example.bikegarage.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserOutputDto getUserByUsername (String username) throws RecordNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + username + " in the database!"));
        return transferUserModelToUserOutputDto(user);
    }

    public List<UserOutputDto> getAllUsers() throws RecordNotFoundException{
        List<UserOutputDto> allUserOutputDtos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new RecordNotFoundException("There are no users in the database!");
        }
        for (User user : users
        ) {allUserOutputDtos.add(transferUserModelToUserOutputDto(user));
        }
        return allUserOutputDtos;
    }

    public UserOutputDto createUser(UserInputDto userInputDto){
        User user = transferUserInputDtoToUser(userInputDto);
        userRepository.save(user);
        return transferUserModelToUserOutputDto(user);
    }

    public UserOutputDto updateUser(UserInputDto userInputDto, String username) throws RecordNotFoundException{
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + username + " in the database!"));
        User userUpdate = updateUserInputDtoToUser(userInputDto, user);
        userRepository.save(userUpdate);
        return transferUserModelToUserOutputDto(userUpdate);
    }
    public String deleteUser(String username) throws RecordNotFoundException{
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new RecordNotFoundException("There is no user found with username " + username + " in the database!"));
        userRepository.delete(user);
        return "Well well I hope you know what you're doing, because you just removed " + user.getUsername() + "!";
    }

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
        userOutputDto.firstName = user.getFirstName();
        userOutputDto.lastName = user.getLastName();
        userOutputDto.gender = user.getGender();
        userOutputDto.dateOfBirth = user.getDateOfBirth();
        userOutputDto.photoUrl = user.getPhotoUrl();
        userOutputDto.totalDistanceDriven = user.getTotalDistanceDriven();
        userOutputDto.bikes = user.getBikes();
        userOutputDto.rides = user.getRides();

        return userOutputDto;
    }
    public User updateUserInputDtoToUser(UserInputDto userInputDto, User user){
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



}
