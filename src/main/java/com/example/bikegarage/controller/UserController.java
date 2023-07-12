package com.example.bikegarage.controller;

import com.example.bikegarage.dto.input.AddTrainerInputDTO;
import com.example.bikegarage.dto.input.PasswordInputDto;
import com.example.bikegarage.dto.input.UserInputDto;
import com.example.bikegarage.dto.output.UserOutputDto;
import com.example.bikegarage.exception.BadRequestException;
import com.example.bikegarage.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/cyclists/{trainerUsername}")
    public ResponseEntity<List<UserOutputDto>> getAllCyclistByTrainer(@PathVariable String trainerUsername) {
        return new ResponseEntity<>(userService.getAllCyclistsOfTrainer(trainerUsername), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        UserOutputDto userOutputDto = userService.createUser(userInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + userOutputDto.username).toUriString());
        return ResponseEntity.created(uri).body(userOutputDto);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable String username, @RequestBody UserInputDto userInputDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        UserOutputDto userOutputDto = userService.updateUser(username, userInputDto);
        return new ResponseEntity<>(userOutputDto, HttpStatus.ACCEPTED);
    }
    @PutMapping("/assign-trainer/{cyclistUsername}")
    public ResponseEntity<Object> assignTrainerToUser(@PathVariable String cyclistUsername, @RequestBody AddTrainerInputDTO trainerInputDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        UserOutputDto userOutputDto = userService.assignTrainer(cyclistUsername, trainerInputDto);
        return new ResponseEntity<>(userOutputDto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/updatepassword/{username}")
    public ResponseEntity<String> updatePassword(@PathVariable String username, @RequestBody PasswordInputDto passwordInputDto, BindingResult br){
        if (br.hasFieldErrors()) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fe : br.getFieldErrors()) {
            sb.append(fe.getField() + ": ");
            sb.append(fe.getDefaultMessage());
            sb.append("\n");
        }
        return ResponseEntity.badRequest().body(sb.toString());
    }
        return new ResponseEntity<>(userService.updatePassword(username, passwordInputDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}
