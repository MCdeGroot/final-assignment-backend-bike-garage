package com.example.bikegarage.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bikegarage.dto.input.AddTrainerInputDTO;
import com.example.bikegarage.dto.input.PasswordInputDto;
import com.example.bikegarage.dto.input.UserInputDto;
import com.example.bikegarage.dto.input.UserUpdateInputDto;
import com.example.bikegarage.dto.output.UserOutputDto;
import com.example.bikegarage.exception.BadRequestException;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.model.Authority;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import com.example.bikegarage.repository.UserRepository;
import com.example.bikegarage.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

class UserControllerTest {
    /**
     * Method under test: {@link UserController#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        ResponseEntity<UserOutputDto> actualUserByUsername = (new UserController(new UserService(userRepository)))
                .getUserByUsername("janedoe");
        assertTrue(actualUserByUsername.hasBody());
        assertTrue(actualUserByUsername.getHeaders().isEmpty());
        assertEquals(200, actualUserByUsername.getStatusCodeValue());
        UserOutputDto body = actualUserByUsername.getBody();
        assertNull(body.getCyclists());
        assertNull(body.getPhotoUrl());
        assertNull(body.getLastName());
        assertNull(body.getGender());
        assertNull(body.getFirstName());
        assertTrue(body.getEnabled());
        assertNull(body.getEmail());
        assertNull(body.getDateOfBirth());
        assertNull(body.getUsername());
        assertNull(body.getBikes());
        assertTrue(body.getAuthorities().isEmpty());
        assertNull(body.getApikey());
        assertEquals(0.0d, body.getTotalDistanceDriven().doubleValue());
        assertNull(body.getRides());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        LocalDate dateOfBirth = LocalDate.of(1970, 1, 1);
        ArrayList<Bike> bikes = new ArrayList<>();
        ArrayList<Ride> rides = new ArrayList<>();
        User trainer = new User();
        ArrayList<User> cyclists = new ArrayList<>();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(
                Optional.of(new User("janedoe", "iloveyou", "jane.doe@example.org", "Jane", "Doe", '\u0001', dateOfBirth,
                        bikes, rides, "https://example.org/example", trainer, cyclists, true, "Apikey", new HashSet<>())));
        ResponseEntity<UserOutputDto> actualUserByUsername = (new UserController(new UserService(userRepository)))
                .getUserByUsername("janedoe");
        assertTrue(actualUserByUsername.hasBody());
        assertTrue(actualUserByUsername.getHeaders().isEmpty());
        assertEquals(200, actualUserByUsername.getStatusCodeValue());
        UserOutputDto body = actualUserByUsername.getBody();
        assertEquals(bikes, body.getCyclists());
        assertEquals("https://example.org/example", body.getPhotoUrl());
        assertEquals("Doe", body.getLastName());
        assertEquals('\u0001', body.getGender().charValue());
        assertEquals("Jane", body.getFirstName());
        assertTrue(body.getEnabled());
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals("1970-01-01", body.getDateOfBirth().toString());
        assertEquals("janedoe", body.getUsername());
        List<Bike> bikes2 = body.getBikes();
        assertEquals(rides, bikes2);
        assertTrue(body.getAuthorities().isEmpty());
        assertEquals("Apikey", body.getApikey());
        assertNull(body.getTrainerUsername());
        assertEquals(0.0d, body.getTotalDistanceDriven().doubleValue());
        assertEquals(bikes2, body.getRides());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.isEnabled()).thenReturn(true);
        when(user.getTrainer()).thenReturn(new User());
        when(user.getGender()).thenReturn('A');
        when(user.getApikey()).thenReturn("Apikey");
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getFirstName()).thenReturn("Jane");
        when(user.getLastName()).thenReturn("Doe");
        when(user.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        ArrayList<Bike> bikeList = new ArrayList<>();
        when(user.getBikes()).thenReturn(bikeList);
        when(user.getCyclists()).thenReturn(new ArrayList<>());
        ArrayList<Ride> rideList = new ArrayList<>();
        when(user.getRides()).thenReturn(rideList);
        when(user.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user));
        ResponseEntity<UserOutputDto> actualUserByUsername = (new UserController(new UserService(userRepository)))
                .getUserByUsername("janedoe");
        assertTrue(actualUserByUsername.hasBody());
        assertTrue(actualUserByUsername.getHeaders().isEmpty());
        assertEquals(200, actualUserByUsername.getStatusCodeValue());
        UserOutputDto body = actualUserByUsername.getBody();
        assertEquals(bikeList, body.getCyclists());
        assertEquals("https://example.org/example", body.getPhotoUrl());
        assertEquals("Doe", body.getLastName());
        assertEquals('A', body.getGender().charValue());
        assertEquals("Jane", body.getFirstName());
        assertTrue(body.getEnabled());
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals("1970-01-01", body.getDateOfBirth().toString());
        assertEquals("janedoe", body.getUsername());
        List<Bike> bikes = body.getBikes();
        assertEquals(rideList, bikes);
        assertTrue(body.getAuthorities().isEmpty());
        assertEquals("Apikey", body.getApikey());
        assertNull(body.getTrainerUsername());
        assertEquals(0.0d, body.getTotalDistanceDriven().doubleValue());
        assertEquals(bikes, body.getRides());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user).isEnabled();
        verify(user, atLeast(1)).getTrainer();
        verify(user).getGender();
        verify(user).getApikey();
        verify(user).getEmail();
        verify(user).getFirstName();
        verify(user).getLastName();
        verify(user).getPhotoUrl();
        verify(user).getUsername();
        verify(user).getDateOfBirth();
        verify(user).getBikes();
        verify(user).getCyclists();
        verify(user, atLeast(1)).getRides();
        verify(user).getAuthorities();
    }

    /**
     * Method under test: {@link UserController#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        ArrayList<Bike> bikeList = new ArrayList<>();
        when(user2.getBikes()).thenReturn(bikeList);
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        ArrayList<Ride> rideList = new ArrayList<>();
        when(user2.getRides()).thenReturn(rideList);
        when(user2.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user2));
        ResponseEntity<UserOutputDto> actualUserByUsername = (new UserController(new UserService(userRepository)))
                .getUserByUsername("janedoe");
        assertTrue(actualUserByUsername.hasBody());
        assertTrue(actualUserByUsername.getHeaders().isEmpty());
        assertEquals(200, actualUserByUsername.getStatusCodeValue());
        UserOutputDto body = actualUserByUsername.getBody();
        assertEquals(bikeList, body.getCyclists());
        assertEquals("https://example.org/example", body.getPhotoUrl());
        assertEquals("Doe", body.getLastName());
        assertEquals('A', body.getGender().charValue());
        assertEquals("Jane", body.getFirstName());
        assertTrue(body.getEnabled());
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals("1970-01-01", body.getDateOfBirth().toString());
        assertEquals("janedoe", body.getUsername());
        List<Bike> bikes = body.getBikes();
        assertEquals(rideList, bikes);
        assertTrue(body.getAuthorities().isEmpty());
        assertEquals("Apikey", body.getApikey());
        assertEquals("janedoe", body.getTrainerUsername());
        assertEquals(0.0d, body.getTotalDistanceDriven().doubleValue());
        assertEquals(bikes, body.getRides());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user2).isEnabled();
        verify(user2, atLeast(1)).getTrainer();
        verify(user2).getGender();
        verify(user2).getApikey();
        verify(user2).getEmail();
        verify(user2).getFirstName();
        verify(user2).getLastName();
        verify(user2).getPhotoUrl();
        verify(user2).getUsername();
        verify(user2).getDateOfBirth();
        verify(user2).getBikes();
        verify(user2).getCyclists();
        verify(user2, atLeast(1)).getRides();
        verify(user2).getAuthorities();
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#getUserByUsername(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserByUsername5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Double.doubleValue()" because the return value of "com.example.bikegarage.model.Ride.getDistance()" is null
        //       at com.example.bikegarage.service.UserService.getTotalDistanceDriven(UserService.java:243)
        //       at com.example.bikegarage.service.UserService.transferUserModelToUserOutputDto(UserService.java:202)
        //       at com.example.bikegarage.service.UserService.getUserByUsername(UserService.java:48)
        //       at com.example.bikegarage.controller.UserController.getUserByUsername(UserController.java:33)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(new Ride());
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(rideList);
        when(user2.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user2));
        (new UserController(new UserService(userRepository))).getUserByUsername("janedoe");
    }

    /**
     * Method under test: {@link UserController#getUserByUsername(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserByUsername6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.RecordNotFoundException: There is no user found with username janedoe in the database!
        //       at com.example.bikegarage.service.UserService.getUserByUsername(UserService.java:50)
        //       at com.example.bikegarage.controller.UserController.getUserByUsername(UserController.java:33)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        (new UserController(new UserService(userRepository))).getUserByUsername("janedoe");
    }

    /**
     * Method under test: {@link UserController#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername7() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.getUserByUsername(Mockito.<String>any())).thenReturn(userOutputDto);
        ResponseEntity<UserOutputDto> actualUserByUsername = (new UserController(userService))
                .getUserByUsername("janedoe");
        assertTrue(actualUserByUsername.hasBody());
        assertTrue(actualUserByUsername.getHeaders().isEmpty());
        assertEquals(200, actualUserByUsername.getStatusCodeValue());
        verify(userService).getUserByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsers() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.RecordNotFoundException: There are no users in the database!
        //       at com.example.bikegarage.service.UserService.getAllUsers(UserService.java:64)
        //       at com.example.bikegarage.controller.UserController.getAllUsers(UserController.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        (new UserController(new UserService(userRepository))).getAllUsers();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<UserOutputDto>> actualAllUsers = (new UserController(new UserService(userRepository)))
                .getAllUsers();
        assertEquals(1, actualAllUsers.getBody().size());
        assertTrue(actualAllUsers.hasBody());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers3() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserService userService = mock(UserService.class);
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        ResponseEntity<List<UserOutputDto>> actualAllUsers = (new UserController(userService)).getAllUsers();
        assertTrue(actualAllUsers.hasBody());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userService).getAllUsers();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsers4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.BadRequestException: An error occurred
        //       at com.example.bikegarage.service.UserService.getAllUsers(UserService.java:62)
        //       at com.example.bikegarage.controller.UserController.getAllUsers(UserController.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenThrow(new BadRequestException("An error occurred"));
        (new UserController(new UserService(userRepository))).getAllUsers();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        ArrayList<User> userList = new ArrayList<>();
        LocalDate dateOfBirth = LocalDate.of(1970, 1, 1);
        ArrayList<Bike> bikes = new ArrayList<>();
        ArrayList<Ride> rides = new ArrayList<>();
        User trainer = new User();
        ArrayList<User> cyclists = new ArrayList<>();
        userList.add(new User("janedoe", "iloveyou", "jane.doe@example.org", "Jane", "Doe", '\u0001', dateOfBirth, bikes,
                rides, "https://example.org/example", trainer, cyclists, true, "Apikey", new HashSet<>()));
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<UserOutputDto>> actualAllUsers = (new UserController(new UserService(userRepository)))
                .getAllUsers();
        assertEquals(1, actualAllUsers.getBody().size());
        assertTrue(actualAllUsers.hasBody());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsers6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.bikegarage.model.User.getUsername()" because "user" is null
        //       at com.example.bikegarage.service.UserService.transferUserModelToUserOutputDto(UserService.java:195)
        //       at com.example.bikegarage.service.UserService.getAllUsers(UserService.java:68)
        //       at com.example.bikegarage.controller.UserController.getAllUsers(UserController.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<User> userList = new ArrayList<>();
        userList.add(null);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        (new UserController(new UserService(userRepository))).getAllUsers();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.isEnabled()).thenReturn(true);
        when(user.getTrainer()).thenReturn(new User());
        when(user.getGender()).thenReturn('A');
        when(user.getApikey()).thenReturn("Apikey");
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getFirstName()).thenReturn("Jane");
        when(user.getLastName()).thenReturn("Doe");
        when(user.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user.getBikes()).thenReturn(new ArrayList<>());
        when(user.getCyclists()).thenReturn(new ArrayList<>());
        when(user.getRides()).thenReturn(new ArrayList<>());
        when(user.getAuthorities()).thenReturn(new HashSet<>());

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<UserOutputDto>> actualAllUsers = (new UserController(new UserService(userRepository)))
                .getAllUsers();
        assertEquals(1, actualAllUsers.getBody().size());
        assertTrue(actualAllUsers.hasBody());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userRepository).findAll();
        verify(user).isEnabled();
        verify(user, atLeast(1)).getTrainer();
        verify(user).getGender();
        verify(user).getApikey();
        verify(user).getEmail();
        verify(user).getFirstName();
        verify(user).getLastName();
        verify(user).getPhotoUrl();
        verify(user).getUsername();
        verify(user).getDateOfBirth();
        verify(user).getBikes();
        verify(user).getCyclists();
        verify(user, atLeast(1)).getRides();
        verify(user).getAuthorities();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers8() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(new ArrayList<>());
        when(user2.getAuthorities()).thenReturn(new HashSet<>());

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<UserOutputDto>> actualAllUsers = (new UserController(new UserService(userRepository)))
                .getAllUsers();
        assertEquals(1, actualAllUsers.getBody().size());
        assertTrue(actualAllUsers.hasBody());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userRepository).findAll();
        verify(user2).isEnabled();
        verify(user2, atLeast(1)).getTrainer();
        verify(user2).getGender();
        verify(user2).getApikey();
        verify(user2).getEmail();
        verify(user2).getFirstName();
        verify(user2).getLastName();
        verify(user2).getPhotoUrl();
        verify(user2).getUsername();
        verify(user2).getDateOfBirth();
        verify(user2).getBikes();
        verify(user2).getCyclists();
        verify(user2, atLeast(1)).getRides();
        verify(user2).getAuthorities();
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsers9() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Double.doubleValue()" because the return value of "com.example.bikegarage.model.Ride.getDistance()" is null
        //       at com.example.bikegarage.service.UserService.getTotalDistanceDriven(UserService.java:243)
        //       at com.example.bikegarage.service.UserService.transferUserModelToUserOutputDto(UserService.java:202)
        //       at com.example.bikegarage.service.UserService.getAllUsers(UserService.java:68)
        //       at com.example.bikegarage.controller.UserController.getAllUsers(UserController.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(new Ride());
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(rideList);
        when(user2.getAuthorities()).thenReturn(new HashSet<>());

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        (new UserController(new UserService(userRepository))).getAllUsers();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers10() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.getUsername()).thenThrow(new BadRequestException("An error occurred"));
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(new ArrayList<>());
        when(user2.getAuthorities()).thenReturn(new HashSet<>());

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        assertThrows(BadRequestException.class,
                () -> (new UserController(new UserService(userRepository))).getAllUsers());
        verify(userRepository).findAll();
        verify(user2).isEnabled();
        verify(user2, atLeast(1)).getTrainer();
        verify(user2).getGender();
        verify(user2).getApikey();
        verify(user2).getEmail();
        verify(user2).getFirstName();
        verify(user2).getLastName();
        verify(user2).getPhotoUrl();
        verify(user2).getUsername();
        verify(user2).getDateOfBirth();
        verify(user2).getBikes();
        verify(user2).getCyclists();
        verify(user2, atLeast(1)).getRides();
        verify(user2).getAuthorities();
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers11() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        Ride ride = new Ride();
        ride.setDistance(10.0d);

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(ride);
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(rideList);
        when(user2.getAuthorities()).thenReturn(new HashSet<>());

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<UserOutputDto>> actualAllUsers = (new UserController(new UserService(userRepository)))
                .getAllUsers();
        assertEquals(1, actualAllUsers.getBody().size());
        assertTrue(actualAllUsers.hasBody());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userRepository).findAll();
        verify(user2).isEnabled();
        verify(user2, atLeast(1)).getTrainer();
        verify(user2).getGender();
        verify(user2).getApikey();
        verify(user2).getEmail();
        verify(user2).getFirstName();
        verify(user2).getLastName();
        verify(user2).getPhotoUrl();
        verify(user2).getUsername();
        verify(user2).getDateOfBirth();
        verify(user2).getBikes();
        verify(user2).getCyclists();
        verify(user2, atLeast(1)).getRides();
        verify(user2).getAuthorities();
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsers12() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.bikegarage.model.Ride.getDistance()" because "ride" is null
        //       at com.example.bikegarage.service.UserService.getTotalDistanceDriven(UserService.java:243)
        //       at com.example.bikegarage.service.UserService.transferUserModelToUserOutputDto(UserService.java:202)
        //       at com.example.bikegarage.service.UserService.getAllUsers(UserService.java:68)
        //       at com.example.bikegarage.controller.UserController.getAllUsers(UserController.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(null);
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(rideList);
        when(user2.getAuthorities()).thenReturn(new HashSet<>());

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        (new UserController(new UserService(userRepository))).getAllUsers();
    }

    /**
     * Method under test: {@link UserController#getAllCyclistByTrainer(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllCyclistByTrainer() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.RecordNotFoundException: Looks like this user doesn't train other cyclists!
        //       at com.example.bikegarage.service.UserService.getAllCyclistsOfTrainer(UserService.java:79)
        //       at com.example.bikegarage.controller.UserController.getAllCyclistByTrainer(UserController.java:43)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findCyclistsByTrainer(Mockito.<User>any())).thenReturn(new ArrayList<>());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        (new UserController(new UserService(userRepository))).getAllCyclistByTrainer("janedoe");
    }

    /**
     * Method under test: {@link UserController#getAllCyclistByTrainer(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllCyclistByTrainer2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.BadRequestException: An error occurred
        //       at com.example.bikegarage.service.UserService.getAllCyclistsOfTrainer(UserService.java:77)
        //       at com.example.bikegarage.controller.UserController.getAllCyclistByTrainer(UserController.java:43)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findCyclistsByTrainer(Mockito.<User>any()))
                .thenThrow(new BadRequestException("An error occurred"));
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        (new UserController(new UserService(userRepository))).getAllCyclistByTrainer("janedoe");
    }

    /**
     * Method under test: {@link UserController#getAllCyclistByTrainer(String)}
     */
    @Test
    void testGetAllCyclistByTrainer3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findCyclistsByTrainer(Mockito.<User>any())).thenReturn(userList);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        ResponseEntity<List<UserOutputDto>> actualAllCyclistByTrainer = (new UserController(
                new UserService(userRepository))).getAllCyclistByTrainer("janedoe");
        assertEquals(1, actualAllCyclistByTrainer.getBody().size());
        assertTrue(actualAllCyclistByTrainer.hasBody());
        assertEquals(200, actualAllCyclistByTrainer.getStatusCodeValue());
        assertTrue(actualAllCyclistByTrainer.getHeaders().isEmpty());
        verify(userRepository).findCyclistsByTrainer(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#getAllCyclistByTrainer(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllCyclistByTrainer4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.UsernameNotFoundException: Cannot find user janedoe
        //       at com.example.bikegarage.service.UserService.lambda$getAllCyclistsOfTrainer$1(UserService.java:76)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.example.bikegarage.service.UserService.getAllCyclistsOfTrainer(UserService.java:76)
        //       at com.example.bikegarage.controller.UserController.getAllCyclistByTrainer(UserController.java:43)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findCyclistsByTrainer(Mockito.<User>any())).thenReturn(new ArrayList<>());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        (new UserController(new UserService(userRepository))).getAllCyclistByTrainer("janedoe");
    }

    /**
     * Method under test: {@link UserController#getAllCyclistByTrainer(String)}
     */
    @Test
    void testGetAllCyclistByTrainer5() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserService userService = mock(UserService.class);
        when(userService.getAllCyclistsOfTrainer(Mockito.<String>any())).thenReturn(new ArrayList<>());
        ResponseEntity<List<UserOutputDto>> actualAllCyclistByTrainer = (new UserController(userService))
                .getAllCyclistByTrainer("janedoe");
        assertTrue(actualAllCyclistByTrainer.hasBody());
        assertEquals(200, actualAllCyclistByTrainer.getStatusCodeValue());
        assertTrue(actualAllCyclistByTrainer.getHeaders().isEmpty());
        verify(userService).getAllCyclistsOfTrainer(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#getAllUsernames()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsernames() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.RecordNotFoundException: There are no users in the database!
        //       at com.example.bikegarage.service.UserService.getAllUsernames(UserService.java:92)
        //       at com.example.bikegarage.controller.UserController.getAllUsernames(UserController.java:48)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        (new UserController(new UserService(userRepository))).getAllUsernames();
    }

    /**
     * Method under test: {@link UserController#getAllUsernames()}
     */
    @Test
    void testGetAllUsernames2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<String>> actualAllUsernames = (new UserController(new UserService(userRepository)))
                .getAllUsernames();
        List<String> body = actualAllUsernames.getBody();
        assertEquals(1, body.size());
        assertNull(body.get(0));
        assertTrue(actualAllUsernames.hasBody());
        assertEquals(200, actualAllUsernames.getStatusCodeValue());
        assertTrue(actualAllUsernames.getHeaders().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserController#getAllUsernames()}
     */
    @Test
    void testGetAllUsernames3() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserService userService = mock(UserService.class);
        when(userService.getAllUsernames()).thenReturn(new ArrayList<>());
        ResponseEntity<List<String>> actualAllUsernames = (new UserController(userService)).getAllUsernames();
        assertTrue(actualAllUsernames.hasBody());
        assertEquals(200, actualAllUsernames.getStatusCodeValue());
        assertTrue(actualAllUsernames.getHeaders().isEmpty());
        verify(userService).getAllUsernames();
    }

    /**
     * Method under test: {@link UserController#getAllUsernames()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsernames4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.BadRequestException: An error occurred
        //       at com.example.bikegarage.service.UserService.getAllUsernames(UserService.java:90)
        //       at com.example.bikegarage.controller.UserController.getAllUsernames(UserController.java:48)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenThrow(new BadRequestException("An error occurred"));
        (new UserController(new UserService(userRepository))).getAllUsernames();
    }

    /**
     * Method under test: {@link UserController#getAllUsernames()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsernames5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.bikegarage.model.User.getUsername()" because "user" is null
        //       at com.example.bikegarage.service.UserService.getAllUsernames(UserService.java:96)
        //       at com.example.bikegarage.controller.UserController.getAllUsernames(UserController.java:48)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<User> userList = new ArrayList<>();
        userList.add(null);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        (new UserController(new UserService(userRepository))).getAllUsernames();
    }

    /**
     * Method under test: {@link UserController#getAllUsernames()}
     */
    @Test
    void testGetAllUsernames6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<String>> actualAllUsernames = (new UserController(new UserService(userRepository)))
                .getAllUsernames();
        List<String> body = actualAllUsernames.getBody();
        assertEquals(1, body.size());
        assertEquals("janedoe", body.get(0));
        assertTrue(actualAllUsernames.hasBody());
        assertEquals(200, actualAllUsernames.getStatusCodeValue());
        assertTrue(actualAllUsernames.getHeaders().isEmpty());
        verify(userRepository).findAll();
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#createUser(UserInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.crypto.password.PasswordEncoder.encode(java.lang.CharSequence)" because "<local3>.passwordEncoder" is null
        //       at com.example.bikegarage.service.UserService.createUser(UserService.java:106)
        //       at com.example.bikegarage.controller.UserController.createUser(UserController.java:62)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController(new UserService(mock(UserRepository.class)));

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setFirstName("Jane");
        userInputDto.setGender('A');
        userInputDto.setLastName("Doe");
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");
        userController.createUser(userInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#createUser(UserInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.crypto.password.PasswordEncoder.encode(java.lang.CharSequence)" because "<local3>.passwordEncoder" is null
        //       at com.example.bikegarage.service.UserService.createUser(UserService.java:106)
        //       at com.example.bikegarage.controller.UserController.createUser(UserController.java:62)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));

        UserService userService = new UserService(userRepository);
        userService.addAuthority("janedoe", "JaneDoe");
        UserController userController = new UserController(userService);

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setFirstName("Jane");
        userInputDto.setGender('A');
        userInputDto.setLastName("Doe");
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");
        userController.createUser(userInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#createUser(UserInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: No current ServletRequestAttributes
        //       at com.example.bikegarage.controller.UserController.createUser(UserController.java:63)
        //   See https://diff.blue/R013 to resolve this issue.

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.createUser(Mockito.<UserInputDto>any())).thenReturn(userOutputDto);
        doNothing().when(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
        userService.addAuthority("janedoe", "JaneDoe");
        UserController userController = new UserController(userService);

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setFirstName("Jane");
        userInputDto.setGender('A');
        userInputDto.setLastName("Doe");
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");
        userController.createUser(userInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#createUser(UserInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.validation.BindingResult.hasFieldErrors()" because "br" is null
        //       at com.example.bikegarage.controller.UserController.createUser(UserController.java:53)
        //   See https://diff.blue/R013 to resolve this issue.

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.createUser(Mockito.<UserInputDto>any())).thenReturn(userOutputDto);
        doNothing().when(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
        userService.addAuthority("janedoe", "JaneDoe");
        UserController userController = new UserController(userService);

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setFirstName("Jane");
        userInputDto.setGender('A');
        userInputDto.setLastName("Doe");
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");
        userController.createUser(userInputDto, null);
    }

    /**
     * Method under test: {@link UserController#createUser(UserInputDto, BindingResult)}
     */
    @Test
    void testCreateUser5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        UserService userService = mock(UserService.class);
        doNothing().when(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
        userService.addAuthority("janedoe", "JaneDoe");
        UserController userController = new UserController(userService);

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setFirstName("Jane");
        userInputDto.setGender('A');
        userInputDto.setLastName("Doe");
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(new ArrayList<>());
        when(br.hasFieldErrors()).thenReturn(true);
        ResponseEntity<Object> actualCreateUserResult = userController.createUser(userInputDto, br);
        assertEquals("", actualCreateUserResult.getBody());
        assertEquals(400, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        verify(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
        verify(br).hasFieldErrors();
        verify(br).getFieldErrors();
    }

    /**
     * Method under test: {@link UserController#createUser(UserInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.BadRequestException: An error occurred
        //       at com.example.bikegarage.controller.UserController.createUser(UserController.java:55)
        //   See https://diff.blue/R013 to resolve this issue.

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.createUser(Mockito.<UserInputDto>any())).thenReturn(userOutputDto);
        doNothing().when(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
        userService.addAuthority("janedoe", "JaneDoe");
        UserController userController = new UserController(userService);

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setFirstName("Jane");
        userInputDto.setGender('A');
        userInputDto.setLastName("Doe");
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenThrow(new BadRequestException("An error occurred"));
        when(br.hasFieldErrors()).thenReturn(true);
        userController.createUser(userInputDto, br);
    }

    /**
     * Method under test: {@link UserController#createUser(UserInputDto, BindingResult)}
     */
    @Test
    void testCreateUser7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        UserService userService = mock(UserService.class);
        doNothing().when(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
        userService.addAuthority("janedoe", "JaneDoe");
        UserController userController = new UserController(userService);

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setFirstName("Jane");
        userInputDto.setGender('A');
        userInputDto.setLastName("Doe");
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(fieldErrorList);
        when(br.hasFieldErrors()).thenReturn(true);
        ResponseEntity<Object> actualCreateUserResult = userController.createUser(userInputDto, br);
        assertEquals("Field: Default Message\n", actualCreateUserResult.getBody());
        assertEquals(400, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        verify(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
        verify(br).hasFieldErrors();
        verify(br).getFieldErrors();
    }

    /**
     * Method under test: {@link UserController#createUser(UserInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateUser8() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.validation.FieldError.getField()" because "fe" is null
        //       at com.example.bikegarage.controller.UserController.createUser(UserController.java:56)
        //   See https://diff.blue/R013 to resolve this issue.

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.createUser(Mockito.<UserInputDto>any())).thenReturn(userOutputDto);
        doNothing().when(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
        userService.addAuthority("janedoe", "JaneDoe");
        UserController userController = new UserController(userService);

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setFirstName("Jane");
        userInputDto.setGender('A');
        userInputDto.setLastName("Doe");
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(null);
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(fieldErrorList);
        when(br.hasFieldErrors()).thenReturn(true);
        userController.createUser(userInputDto, br);
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    void testUpdateUser() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        UserController userController = new UserController(new UserService(userRepository));

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        ResponseEntity<Object> actualUpdateUserResult = userController.updateUser("janedoe", userUpdateInputDto,
                new BindException("Target", "Object Name"));
        assertTrue(actualUpdateUserResult.hasBody());
        assertEquals(202, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        assertEquals("1970-01-01", ((UserOutputDto) actualUpdateUserResult.getBody()).getDateOfBirth().toString());
        assertNull(((UserOutputDto) actualUpdateUserResult.getBody()).getRides());
        assertNull(((UserOutputDto) actualUpdateUserResult.getBody()).getPhotoUrl());
        assertEquals("Doe", ((UserOutputDto) actualUpdateUserResult.getBody()).getLastName());
        assertEquals('A', ((UserOutputDto) actualUpdateUserResult.getBody()).getGender().charValue());
        assertEquals("Jane", ((UserOutputDto) actualUpdateUserResult.getBody()).getFirstName());
        assertTrue(((UserOutputDto) actualUpdateUserResult.getBody()).getEnabled());
        assertEquals("jane.doe@example.org", ((UserOutputDto) actualUpdateUserResult.getBody()).getEmail());
        assertNull(((UserOutputDto) actualUpdateUserResult.getBody()).getApikey());
        assertNull(((UserOutputDto) actualUpdateUserResult.getBody()).getCyclists());
        assertNull(((UserOutputDto) actualUpdateUserResult.getBody()).getBikes());
        assertTrue(((UserOutputDto) actualUpdateUserResult.getBody()).getAuthorities().isEmpty());
        assertNull(((UserOutputDto) actualUpdateUserResult.getBody()).getUsername());
        assertEquals(0.0d, ((UserOutputDto) actualUpdateUserResult.getBody()).getTotalDistanceDriven().doubleValue());
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.BadRequestException: An error occurred
        //       at com.example.bikegarage.service.UserService.updateUser(UserService.java:116)
        //       at com.example.bikegarage.controller.UserController.updateUser(UserController.java:78)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenThrow(new BadRequestException("An error occurred"));
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        UserController userController = new UserController(new UserService(userRepository));

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        userController.updateUser("janedoe", userUpdateInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    void testUpdateUser3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        LocalDate dateOfBirth = LocalDate.of(1970, 1, 1);
        ArrayList<Bike> bikes = new ArrayList<>();
        ArrayList<Ride> rides = new ArrayList<>();
        User trainer = new User();
        ArrayList<User> cyclists = new ArrayList<>();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(
                Optional.of(new User("janedoe", "iloveyou", "jane.doe@example.org", "Jane", "Doe", '\u0001', dateOfBirth,
                        bikes, rides, "https://example.org/example", trainer, cyclists, true, "Apikey", new HashSet<>())));
        UserController userController = new UserController(new UserService(userRepository));

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        ResponseEntity<Object> actualUpdateUserResult = userController.updateUser("janedoe", userUpdateInputDto,
                new BindException("Target", "Object Name"));
        assertTrue(actualUpdateUserResult.hasBody());
        assertEquals(202, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        assertEquals("1970-01-01", ((UserOutputDto) actualUpdateUserResult.getBody()).getDateOfBirth().toString());
        assertEquals(bikes, ((UserOutputDto) actualUpdateUserResult.getBody()).getRides());
        assertEquals("https://example.org/example", ((UserOutputDto) actualUpdateUserResult.getBody()).getPhotoUrl());
        assertEquals("Doe", ((UserOutputDto) actualUpdateUserResult.getBody()).getLastName());
        assertEquals('A', ((UserOutputDto) actualUpdateUserResult.getBody()).getGender().charValue());
        assertEquals("Jane", ((UserOutputDto) actualUpdateUserResult.getBody()).getFirstName());
        assertTrue(((UserOutputDto) actualUpdateUserResult.getBody()).getEnabled());
        assertEquals("jane.doe@example.org", ((UserOutputDto) actualUpdateUserResult.getBody()).getEmail());
        assertEquals("Apikey", ((UserOutputDto) actualUpdateUserResult.getBody()).getApikey());
        List<User> cyclists2 = ((UserOutputDto) actualUpdateUserResult.getBody()).getCyclists();
        assertEquals(bikes, cyclists2);
        assertEquals(cyclists2, ((UserOutputDto) actualUpdateUserResult.getBody()).getBikes());
        assertTrue(((UserOutputDto) actualUpdateUserResult.getBody()).getAuthorities().isEmpty());
        assertEquals("janedoe", ((UserOutputDto) actualUpdateUserResult.getBody()).getUsername());
        assertNull(((UserOutputDto) actualUpdateUserResult.getBody()).getTrainerUsername());
        assertEquals(0.0d, ((UserOutputDto) actualUpdateUserResult.getBody()).getTotalDistanceDriven().doubleValue());
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    void testUpdateUser4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.isEnabled()).thenReturn(true);
        when(user.getTrainer()).thenReturn(new User());
        when(user.getGender()).thenReturn('A');
        when(user.getApikey()).thenReturn("Apikey");
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getFirstName()).thenReturn("Jane");
        when(user.getLastName()).thenReturn("Doe");
        when(user.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        ArrayList<Bike> bikeList = new ArrayList<>();
        when(user.getBikes()).thenReturn(bikeList);
        when(user.getCyclists()).thenReturn(new ArrayList<>());
        when(user.getRides()).thenReturn(new ArrayList<>());
        when(user.getAuthorities()).thenReturn(new HashSet<>());
        doNothing().when(user).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFirstName(Mockito.<String>any());
        doNothing().when(user).setGender(Mockito.<Character>any());
        doNothing().when(user).setLastName(Mockito.<String>any());
        doNothing().when(user).setUsername(Mockito.<String>any());
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserController userController = new UserController(new UserService(userRepository));

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        ResponseEntity<Object> actualUpdateUserResult = userController.updateUser("janedoe", userUpdateInputDto,
                new BindException("Target", "Object Name"));
        assertTrue(actualUpdateUserResult.hasBody());
        assertEquals(202, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        assertEquals("1970-01-01", ((UserOutputDto) actualUpdateUserResult.getBody()).getDateOfBirth().toString());
        assertEquals(bikeList, ((UserOutputDto) actualUpdateUserResult.getBody()).getRides());
        assertEquals("https://example.org/example", ((UserOutputDto) actualUpdateUserResult.getBody()).getPhotoUrl());
        assertEquals("Doe", ((UserOutputDto) actualUpdateUserResult.getBody()).getLastName());
        assertEquals('A', ((UserOutputDto) actualUpdateUserResult.getBody()).getGender().charValue());
        assertEquals("Jane", ((UserOutputDto) actualUpdateUserResult.getBody()).getFirstName());
        assertTrue(((UserOutputDto) actualUpdateUserResult.getBody()).getEnabled());
        assertEquals("jane.doe@example.org", ((UserOutputDto) actualUpdateUserResult.getBody()).getEmail());
        assertEquals("Apikey", ((UserOutputDto) actualUpdateUserResult.getBody()).getApikey());
        List<User> cyclists = ((UserOutputDto) actualUpdateUserResult.getBody()).getCyclists();
        assertEquals(bikeList, cyclists);
        assertEquals(cyclists, ((UserOutputDto) actualUpdateUserResult.getBody()).getBikes());
        assertTrue(((UserOutputDto) actualUpdateUserResult.getBody()).getAuthorities().isEmpty());
        assertEquals("janedoe", ((UserOutputDto) actualUpdateUserResult.getBody()).getUsername());
        assertNull(((UserOutputDto) actualUpdateUserResult.getBody()).getTrainerUsername());
        assertEquals(0.0d, ((UserOutputDto) actualUpdateUserResult.getBody()).getTotalDistanceDriven().doubleValue());
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user).isEnabled();
        verify(user, atLeast(1)).getTrainer();
        verify(user).getGender();
        verify(user).getApikey();
        verify(user).getEmail();
        verify(user).getFirstName();
        verify(user).getLastName();
        verify(user).getPhotoUrl();
        verify(user, atLeast(1)).getUsername();
        verify(user).getDateOfBirth();
        verify(user).getBikes();
        verify(user).getCyclists();
        verify(user, atLeast(1)).getRides();
        verify(user).getAuthorities();
        verify(user).setDateOfBirth(Mockito.<LocalDate>any());
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setFirstName(Mockito.<String>any());
        verify(user).setGender(Mockito.<Character>any());
        verify(user).setLastName(Mockito.<String>any());
        verify(user).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    void testUpdateUser5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        ArrayList<Bike> bikeList = new ArrayList<>();
        when(user2.getBikes()).thenReturn(bikeList);
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(new ArrayList<>());
        when(user2.getAuthorities()).thenReturn(new HashSet<>());
        doNothing().when(user2).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(user2).setEmail(Mockito.<String>any());
        doNothing().when(user2).setFirstName(Mockito.<String>any());
        doNothing().when(user2).setGender(Mockito.<Character>any());
        doNothing().when(user2).setLastName(Mockito.<String>any());
        doNothing().when(user2).setUsername(Mockito.<String>any());
        Optional<User> ofResult = Optional.of(user2);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserController userController = new UserController(new UserService(userRepository));

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        ResponseEntity<Object> actualUpdateUserResult = userController.updateUser("janedoe", userUpdateInputDto,
                new BindException("Target", "Object Name"));
        assertTrue(actualUpdateUserResult.hasBody());
        assertEquals(202, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        assertEquals("1970-01-01", ((UserOutputDto) actualUpdateUserResult.getBody()).getDateOfBirth().toString());
        assertEquals(bikeList, ((UserOutputDto) actualUpdateUserResult.getBody()).getRides());
        assertEquals("https://example.org/example", ((UserOutputDto) actualUpdateUserResult.getBody()).getPhotoUrl());
        assertEquals("Doe", ((UserOutputDto) actualUpdateUserResult.getBody()).getLastName());
        assertEquals('A', ((UserOutputDto) actualUpdateUserResult.getBody()).getGender().charValue());
        assertEquals("Jane", ((UserOutputDto) actualUpdateUserResult.getBody()).getFirstName());
        assertTrue(((UserOutputDto) actualUpdateUserResult.getBody()).getEnabled());
        assertEquals("jane.doe@example.org", ((UserOutputDto) actualUpdateUserResult.getBody()).getEmail());
        assertEquals("Apikey", ((UserOutputDto) actualUpdateUserResult.getBody()).getApikey());
        List<User> cyclists = ((UserOutputDto) actualUpdateUserResult.getBody()).getCyclists();
        assertEquals(bikeList, cyclists);
        assertEquals(cyclists, ((UserOutputDto) actualUpdateUserResult.getBody()).getBikes());
        assertTrue(((UserOutputDto) actualUpdateUserResult.getBody()).getAuthorities().isEmpty());
        assertEquals("janedoe", ((UserOutputDto) actualUpdateUserResult.getBody()).getUsername());
        assertEquals("janedoe", ((UserOutputDto) actualUpdateUserResult.getBody()).getTrainerUsername());
        assertEquals(0.0d, ((UserOutputDto) actualUpdateUserResult.getBody()).getTotalDistanceDriven().doubleValue());
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user2).isEnabled();
        verify(user2, atLeast(1)).getTrainer();
        verify(user2).getGender();
        verify(user2).getApikey();
        verify(user2).getEmail();
        verify(user2).getFirstName();
        verify(user2).getLastName();
        verify(user2).getPhotoUrl();
        verify(user2, atLeast(1)).getUsername();
        verify(user2).getDateOfBirth();
        verify(user2).getBikes();
        verify(user2).getCyclists();
        verify(user2, atLeast(1)).getRides();
        verify(user2).getAuthorities();
        verify(user2).setDateOfBirth(Mockito.<LocalDate>any());
        verify(user2).setEmail(Mockito.<String>any());
        verify(user2).setFirstName(Mockito.<String>any());
        verify(user2).setGender(Mockito.<Character>any());
        verify(user2).setLastName(Mockito.<String>any());
        verify(user2).setUsername(Mockito.<String>any());
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Double.doubleValue()" because the return value of "com.example.bikegarage.model.Ride.getDistance()" is null
        //       at com.example.bikegarage.service.UserService.getTotalDistanceDriven(UserService.java:243)
        //       at com.example.bikegarage.service.UserService.transferUserModelToUserOutputDto(UserService.java:202)
        //       at com.example.bikegarage.service.UserService.updateUser(UserService.java:117)
        //       at com.example.bikegarage.controller.UserController.updateUser(UserController.java:78)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(new Ride());
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(rideList);
        when(user2.getAuthorities()).thenReturn(new HashSet<>());
        doNothing().when(user2).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(user2).setEmail(Mockito.<String>any());
        doNothing().when(user2).setFirstName(Mockito.<String>any());
        doNothing().when(user2).setGender(Mockito.<Character>any());
        doNothing().when(user2).setLastName(Mockito.<String>any());
        doNothing().when(user2).setUsername(Mockito.<String>any());
        Optional<User> ofResult = Optional.of(user2);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserController userController = new UserController(new UserService(userRepository));

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        userController.updateUser("janedoe", userUpdateInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.RecordNotFoundException: There is no user found with username janedoe in the database!
        //       at com.example.bikegarage.service.UserService.lambda$updateUser$2(UserService.java:114)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.example.bikegarage.service.UserService.updateUser(UserService.java:114)
        //       at com.example.bikegarage.controller.UserController.updateUser(UserController.java:78)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        UserController userController = new UserController(new UserService(userRepository));

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        userController.updateUser("janedoe", userUpdateInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    void testUpdateUser8() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.updateUser(Mockito.<String>any(), Mockito.<UserUpdateInputDto>any())).thenReturn(userOutputDto);
        UserController userController = new UserController(userService);

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        ResponseEntity<Object> actualUpdateUserResult = userController.updateUser("janedoe", userUpdateInputDto,
                new BindException("Target", "Object Name"));
        assertTrue(actualUpdateUserResult.hasBody());
        assertEquals(202, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        verify(userService).updateUser(Mockito.<String>any(), Mockito.<UserUpdateInputDto>any());
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser9() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.validation.BindingResult.hasFieldErrors()" because "br" is null
        //       at com.example.bikegarage.controller.UserController.updateUser(UserController.java:69)
        //   See https://diff.blue/R013 to resolve this issue.

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.updateUser(Mockito.<String>any(), Mockito.<UserUpdateInputDto>any())).thenReturn(userOutputDto);
        UserController userController = new UserController(userService);

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        userController.updateUser("janedoe", userUpdateInputDto, null);
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    void testUpdateUser10() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController(mock(UserService.class));

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(new ArrayList<>());
        when(br.hasFieldErrors()).thenReturn(true);
        ResponseEntity<Object> actualUpdateUserResult = userController.updateUser("janedoe", userUpdateInputDto, br);
        assertEquals("", actualUpdateUserResult.getBody());
        assertEquals(400, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        verify(br).hasFieldErrors();
        verify(br).getFieldErrors();
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    void testUpdateUser11() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController(mock(UserService.class));

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(fieldErrorList);
        when(br.hasFieldErrors()).thenReturn(true);
        ResponseEntity<Object> actualUpdateUserResult = userController.updateUser("janedoe", userUpdateInputDto, br);
        assertEquals("Field: Default Message\n", actualUpdateUserResult.getBody());
        assertEquals(400, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        verify(br).hasFieldErrors();
        verify(br).getFieldErrors();
    }

    /**
     * Method under test: {@link UserController#updateUser(String, UserUpdateInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser12() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.bikegarage.dto.input.UserUpdateInputDto["dateOfBirth"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.validation.FieldError.getField()" because "fe" is null
        //       at com.example.bikegarage.controller.UserController.updateUser(UserController.java:72)
        //   See https://diff.blue/R013 to resolve this issue.

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.updateUser(Mockito.<String>any(), Mockito.<UserUpdateInputDto>any())).thenReturn(userOutputDto);
        UserController userController = new UserController(userService);

        UserUpdateInputDto userUpdateInputDto = new UserUpdateInputDto();
        userUpdateInputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userUpdateInputDto.setEmail("jane.doe@example.org");
        userUpdateInputDto.setFirstName("Jane");
        userUpdateInputDto.setGender('A');
        userUpdateInputDto.setLastName("Doe");
        userUpdateInputDto.setUsername("janedoe");

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(null);
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(fieldErrorList);
        when(br.hasFieldErrors()).thenReturn(true);
        userController.updateUser("janedoe", userUpdateInputDto, br);
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    void testAssignTrainerToUser() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        UserController userController = new UserController(new UserService(userRepository));
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";
        ResponseEntity<Object> actualAssignTrainerToUserResult = userController.assignTrainerToUser("janedoe",
                trainerInputDto, new BindException("Target", "Object Name"));
        assertTrue(actualAssignTrainerToUserResult.hasBody());
        assertEquals(202, actualAssignTrainerToUserResult.getStatusCodeValue());
        assertTrue(actualAssignTrainerToUserResult.getHeaders().isEmpty());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getDateOfBirth());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getRides());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getPhotoUrl());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getLastName());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getGender());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getFirstName());
        assertTrue(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getEnabled());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getEmail());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getApikey());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getCyclists());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getBikes());
        assertEquals(1, ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getAuthorities().size());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getUsername());
        assertNull(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getTrainerUsername());
        assertEquals(0.0d,
                ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getTotalDistanceDriven().doubleValue());
        verify(userRepository, atLeast(1)).save(Mockito.<User>any());
        verify(userRepository, atLeast(1)).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAssignTrainerToUser2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.BadRequestException: An error occurred
        //       at com.example.bikegarage.service.UserService.assignTrainer(UserService.java:131)
        //       at com.example.bikegarage.controller.UserController.assignTrainerToUser(UserController.java:92)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenThrow(new BadRequestException("An error occurred"));
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        UserController userController = new UserController(new UserService(userRepository));
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";
        userController.assignTrainerToUser("janedoe", trainerInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAssignTrainerToUser3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: The user already has a trainer assigned.
        //       at com.example.bikegarage.service.UserService.assignTrainer(UserService.java:124)
        //       at com.example.bikegarage.controller.UserController.assignTrainerToUser(UserController.java:92)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        LocalDate dateOfBirth = LocalDate.of(1970, 1, 1);
        ArrayList<Bike> bikes = new ArrayList<>();
        ArrayList<Ride> rides = new ArrayList<>();
        User trainer = new User();
        ArrayList<User> cyclists = new ArrayList<>();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(
                Optional.of(new User("janedoe", "iloveyou", "jane.doe@example.org", "Jane", "Doe", '\u0001', dateOfBirth,
                        bikes, rides, "https://example.org/example", trainer, cyclists, true, "ROLE_TRAINER", new HashSet<>())));
        UserController userController = new UserController(new UserService(userRepository));
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";
        userController.assignTrainerToUser("janedoe", trainerInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAssignTrainerToUser4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: The user already has a trainer assigned.
        //       at com.example.bikegarage.service.UserService.assignTrainer(UserService.java:124)
        //       at com.example.bikegarage.controller.UserController.assignTrainerToUser(UserController.java:92)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.isEnabled()).thenReturn(true);
        when(user.getTrainer()).thenReturn(new User());
        when(user.getGender()).thenReturn('A');
        when(user.getApikey()).thenReturn("Apikey");
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getFirstName()).thenReturn("Jane");
        when(user.getLastName()).thenReturn("Doe");
        when(user.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user.getBikes()).thenReturn(new ArrayList<>());
        when(user.getCyclists()).thenReturn(new ArrayList<>());
        when(user.getRides()).thenReturn(new ArrayList<>());
        when(user.getAuthorities()).thenReturn(new HashSet<>());
        doNothing().when(user).addAuthority(Mockito.<Authority>any());
        doNothing().when(user).setTrainer(Mockito.<User>any());
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserController userController = new UserController(new UserService(userRepository));
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";
        userController.assignTrainerToUser("janedoe", trainerInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    void testAssignTrainerToUser5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.isEnabled()).thenReturn(true);
        when(user.getTrainer()).thenReturn(null);
        when(user.getGender()).thenReturn('A');
        when(user.getApikey()).thenReturn("Apikey");
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getFirstName()).thenReturn("Jane");
        when(user.getLastName()).thenReturn("Doe");
        when(user.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        ArrayList<Bike> bikeList = new ArrayList<>();
        when(user.getBikes()).thenReturn(bikeList);
        when(user.getCyclists()).thenReturn(new ArrayList<>());
        when(user.getRides()).thenReturn(new ArrayList<>());
        when(user.getAuthorities()).thenReturn(new HashSet<>());
        doNothing().when(user).addAuthority(Mockito.<Authority>any());
        doNothing().when(user).setTrainer(Mockito.<User>any());
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserController userController = new UserController(new UserService(userRepository));
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";
        ResponseEntity<Object> actualAssignTrainerToUserResult = userController.assignTrainerToUser("janedoe",
                trainerInputDto, new BindException("Target", "Object Name"));
        assertTrue(actualAssignTrainerToUserResult.hasBody());
        assertEquals(202, actualAssignTrainerToUserResult.getStatusCodeValue());
        assertTrue(actualAssignTrainerToUserResult.getHeaders().isEmpty());
        assertEquals("1970-01-01",
                ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getDateOfBirth().toString());
        assertEquals(bikeList, ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getRides());
        assertEquals("https://example.org/example",
                ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getPhotoUrl());
        assertEquals("Doe", ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getLastName());
        assertEquals('A', ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getGender().charValue());
        assertEquals("Jane", ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getFirstName());
        assertTrue(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getEnabled());
        assertEquals("jane.doe@example.org", ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getEmail());
        assertEquals("Apikey", ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getApikey());
        List<User> cyclists = ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getCyclists();
        assertEquals(bikeList, cyclists);
        assertEquals(cyclists, ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getBikes());
        assertTrue(((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getAuthorities().isEmpty());
        assertEquals("janedoe", ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getUsername());
        assertEquals(0.0d,
                ((UserOutputDto) actualAssignTrainerToUserResult.getBody()).getTotalDistanceDriven().doubleValue());
        verify(userRepository, atLeast(1)).save(Mockito.<User>any());
        verify(userRepository, atLeast(1)).findByUsername(Mockito.<String>any());
        verify(user).isEnabled();
        verify(user, atLeast(1)).getTrainer();
        verify(user).getGender();
        verify(user).getApikey();
        verify(user).getEmail();
        verify(user).getFirstName();
        verify(user).getLastName();
        verify(user).getPhotoUrl();
        verify(user, atLeast(1)).getUsername();
        verify(user).getDateOfBirth();
        verify(user).getBikes();
        verify(user).getCyclists();
        verify(user, atLeast(1)).getRides();
        verify(user).getAuthorities();
        verify(user).addAuthority(Mockito.<Authority>any());
        verify(user).setTrainer(Mockito.<User>any());
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAssignTrainerToUser6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.RecordNotFoundException: There is no user found with username janedoe in the database!
        //       at com.example.bikegarage.service.UserService.lambda$assignTrainer$3(UserService.java:122)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.example.bikegarage.service.UserService.assignTrainer(UserService.java:122)
        //       at com.example.bikegarage.controller.UserController.assignTrainerToUser(UserController.java:92)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        UserController userController = new UserController(new UserService(userRepository));
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";
        userController.assignTrainerToUser("janedoe", trainerInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    void testAssignTrainerToUser7() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.assignTrainer(Mockito.<String>any(), Mockito.<AddTrainerInputDTO>any()))
                .thenReturn(userOutputDto);
        UserController userController = new UserController(userService);
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";
        ResponseEntity<Object> actualAssignTrainerToUserResult = userController.assignTrainerToUser("janedoe",
                trainerInputDto, new BindException("Target", "Object Name"));
        assertTrue(actualAssignTrainerToUserResult.hasBody());
        assertEquals(202, actualAssignTrainerToUserResult.getStatusCodeValue());
        assertTrue(actualAssignTrainerToUserResult.getHeaders().isEmpty());
        verify(userService).assignTrainer(Mockito.<String>any(), Mockito.<AddTrainerInputDTO>any());
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAssignTrainerToUser8() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.validation.BindingResult.hasFieldErrors()" because "br" is null
        //       at com.example.bikegarage.controller.UserController.assignTrainerToUser(UserController.java:83)
        //   See https://diff.blue/R013 to resolve this issue.

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.assignTrainer(Mockito.<String>any(), Mockito.<AddTrainerInputDTO>any()))
                .thenReturn(userOutputDto);
        UserController userController = new UserController(userService);
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";
        userController.assignTrainerToUser("janedoe", trainerInputDto, null);
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    void testAssignTrainerToUser9() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserController userController = new UserController(mock(UserService.class));
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(new ArrayList<>());
        when(br.hasFieldErrors()).thenReturn(true);
        ResponseEntity<Object> actualAssignTrainerToUserResult = userController.assignTrainerToUser("janedoe",
                trainerInputDto, br);
        assertEquals("", actualAssignTrainerToUserResult.getBody());
        assertEquals(400, actualAssignTrainerToUserResult.getStatusCodeValue());
        assertTrue(actualAssignTrainerToUserResult.getHeaders().isEmpty());
        verify(br).hasFieldErrors();
        verify(br).getFieldErrors();
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    void testAssignTrainerToUser10() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserController userController = new UserController(mock(UserService.class));
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(fieldErrorList);
        when(br.hasFieldErrors()).thenReturn(true);
        ResponseEntity<Object> actualAssignTrainerToUserResult = userController.assignTrainerToUser("janedoe",
                trainerInputDto, br);
        assertEquals("Field: Default Message\n", actualAssignTrainerToUserResult.getBody());
        assertEquals(400, actualAssignTrainerToUserResult.getStatusCodeValue());
        assertTrue(actualAssignTrainerToUserResult.getHeaders().isEmpty());
        verify(br).hasFieldErrors();
        verify(br).getFieldErrors();
    }

    /**
     * Method under test: {@link UserController#assignTrainerToUser(String, AddTrainerInputDTO, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAssignTrainerToUser11() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.validation.FieldError.getField()" because "fe" is null
        //       at com.example.bikegarage.controller.UserController.assignTrainerToUser(UserController.java:86)
        //   See https://diff.blue/R013 to resolve this issue.

        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setApikey("Apikey");
        userOutputDto.setAuthorities(new HashSet<>());
        userOutputDto.setBikes(new ArrayList<>());
        userOutputDto.setCyclists(new ArrayList<>());
        userOutputDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userOutputDto.setEmail("jane.doe@example.org");
        userOutputDto.setEnabled(true);
        userOutputDto.setFirstName("Jane");
        userOutputDto.setGender('A');
        userOutputDto.setLastName("Doe");
        userOutputDto.setRides(new ArrayList<>());
        userOutputDto.setTotalDistanceDriven(10.0d);
        userOutputDto.setTrainerUsername("janedoe");
        userOutputDto.setUsername("janedoe");
        UserService userService = mock(UserService.class);
        when(userService.assignTrainer(Mockito.<String>any(), Mockito.<AddTrainerInputDTO>any()))
                .thenReturn(userOutputDto);
        UserController userController = new UserController(userService);
        AddTrainerInputDTO trainerInputDto = new AddTrainerInputDTO();
        trainerInputDto.trainerUsername = "janedoe";

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(null);
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(fieldErrorList);
        when(br.hasFieldErrors()).thenReturn(true);
        userController.assignTrainerToUser("janedoe", trainerInputDto, br);
    }

    /**
     * Method under test: {@link UserController#updatePassword(String, PasswordInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdatePassword() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.crypto.password.PasswordEncoder.encode(java.lang.CharSequence)" because "<local4>.passwordEncoder" is null
        //       at com.example.bikegarage.service.UserService.updatePassword(UserService.java:142)
        //       at com.example.bikegarage.controller.UserController.updatePassword(UserController.java:107)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        UserController userController = new UserController(new UserService(userRepository));
        PasswordInputDto passwordInputDto = new PasswordInputDto();
        passwordInputDto.newPassword = "iloveyou";
        userController.updatePassword("janedoe", passwordInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#updatePassword(String, PasswordInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdatePassword2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.RecordNotFoundException: There is no user found with username janedoe in the database!
        //       at com.example.bikegarage.service.UserService.lambda$updatePassword$5(UserService.java:141)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.example.bikegarage.service.UserService.updatePassword(UserService.java:141)
        //       at com.example.bikegarage.controller.UserController.updatePassword(UserController.java:107)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        UserController userController = new UserController(new UserService(userRepository));
        PasswordInputDto passwordInputDto = new PasswordInputDto();
        passwordInputDto.newPassword = "iloveyou";
        userController.updatePassword("janedoe", passwordInputDto, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#updatePassword(String, PasswordInputDto, BindingResult)}
     */
    @Test
    void testUpdatePassword3() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserService userService = mock(UserService.class);
        when(userService.updatePassword(Mockito.<String>any(), Mockito.<PasswordInputDto>any())).thenReturn("2020-03-01");
        UserController userController = new UserController(userService);
        PasswordInputDto passwordInputDto = new PasswordInputDto();
        passwordInputDto.newPassword = "iloveyou";
        ResponseEntity<String> actualUpdatePasswordResult = userController.updatePassword("janedoe", passwordInputDto,
                new BindException("Target", "Object Name"));
        assertEquals("2020-03-01", actualUpdatePasswordResult.getBody());
        assertEquals(202, actualUpdatePasswordResult.getStatusCodeValue());
        assertTrue(actualUpdatePasswordResult.getHeaders().isEmpty());
        verify(userService).updatePassword(Mockito.<String>any(), Mockito.<PasswordInputDto>any());
    }

    /**
     * Method under test: {@link UserController#updatePassword(String, PasswordInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdatePassword4() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.validation.BindingResult.hasFieldErrors()" because "br" is null
        //       at com.example.bikegarage.controller.UserController.updatePassword(UserController.java:98)
        //   See https://diff.blue/R013 to resolve this issue.

        UserService userService = mock(UserService.class);
        when(userService.updatePassword(Mockito.<String>any(), Mockito.<PasswordInputDto>any())).thenReturn("2020-03-01");
        UserController userController = new UserController(userService);
        PasswordInputDto passwordInputDto = new PasswordInputDto();
        passwordInputDto.newPassword = "iloveyou";
        userController.updatePassword("janedoe", passwordInputDto, null);
    }

    /**
     * Method under test: {@link UserController#updatePassword(String, PasswordInputDto, BindingResult)}
     */
    @Test
    void testUpdatePassword5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserController userController = new UserController(mock(UserService.class));
        PasswordInputDto passwordInputDto = new PasswordInputDto();
        passwordInputDto.newPassword = "iloveyou";
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(new ArrayList<>());
        when(br.hasFieldErrors()).thenReturn(true);
        ResponseEntity<String> actualUpdatePasswordResult = userController.updatePassword("janedoe", passwordInputDto,
                br);
        assertEquals("", actualUpdatePasswordResult.getBody());
        assertEquals(400, actualUpdatePasswordResult.getStatusCodeValue());
        assertTrue(actualUpdatePasswordResult.getHeaders().isEmpty());
        verify(br).hasFieldErrors();
        verify(br).getFieldErrors();
    }

    /**
     * Method under test: {@link UserController#updatePassword(String, PasswordInputDto, BindingResult)}
     */
    @Test
    void testUpdatePassword6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserController userController = new UserController(mock(UserService.class));
        PasswordInputDto passwordInputDto = new PasswordInputDto();
        passwordInputDto.newPassword = "iloveyou";

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(new FieldError("Object Name", "Field", "Default Message"));
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(fieldErrorList);
        when(br.hasFieldErrors()).thenReturn(true);
        ResponseEntity<String> actualUpdatePasswordResult = userController.updatePassword("janedoe", passwordInputDto,
                br);
        assertEquals("Field: Default Message\n", actualUpdatePasswordResult.getBody());
        assertEquals(400, actualUpdatePasswordResult.getStatusCodeValue());
        assertTrue(actualUpdatePasswordResult.getHeaders().isEmpty());
        verify(br).hasFieldErrors();
        verify(br).getFieldErrors();
    }

    /**
     * Method under test: {@link UserController#updatePassword(String, PasswordInputDto, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdatePassword7() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.validation.FieldError.getField()" because "fe" is null
        //       at com.example.bikegarage.controller.UserController.updatePassword(UserController.java:101)
        //   See https://diff.blue/R013 to resolve this issue.

        UserService userService = mock(UserService.class);
        when(userService.updatePassword(Mockito.<String>any(), Mockito.<PasswordInputDto>any())).thenReturn("2020-03-01");
        UserController userController = new UserController(userService);
        PasswordInputDto passwordInputDto = new PasswordInputDto();
        passwordInputDto.newPassword = "iloveyou";

        ArrayList<FieldError> fieldErrorList = new ArrayList<>();
        fieldErrorList.add(null);
        BeanPropertyBindingResult br = mock(BeanPropertyBindingResult.class);
        when(br.getFieldErrors()).thenReturn(fieldErrorList);
        when(br.hasFieldErrors()).thenReturn(true);
        userController.updatePassword("janedoe", passwordInputDto, br);
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        ResponseEntity<HttpStatus> actualDeleteUserResult = (new UserController(new UserService(userRepository)))
                .deleteUser("janedoe");
        assertNull(actualDeleteUserResult.getBody());
        assertEquals(204, actualDeleteUserResult.getStatusCodeValue());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).delete(Mockito.<User>any());
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUser2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.BadRequestException: An error occurred
        //       at com.example.bikegarage.service.UserService.deleteUser(UserService.java:151)
        //       at com.example.bikegarage.controller.UserController.deleteUser(UserController.java:112)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        doThrow(new BadRequestException("An error occurred")).when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        (new UserController(new UserService(userRepository))).deleteUser("janedoe");
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        ResponseEntity<HttpStatus> actualDeleteUserResult = (new UserController(new UserService(userRepository)))
                .deleteUser("janedoe");
        assertNull(actualDeleteUserResult.getBody());
        assertEquals(204, actualDeleteUserResult.getStatusCodeValue());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userRepository).delete(Mockito.<User>any());
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUser4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.RecordNotFoundException: There is no user found with username janedoe in the database!
        //       at com.example.bikegarage.service.UserService.lambda$deleteUser$6(UserService.java:150)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.example.bikegarage.service.UserService.deleteUser(UserService.java:150)
        //       at com.example.bikegarage.controller.UserController.deleteUser(UserController.java:112)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        (new UserController(new UserService(userRepository))).deleteUser("janedoe");
    }

    /**
     * Method under test: {@link UserController#deleteUser(String)}
     */
    @Test
    void testDeleteUser5() throws RecordNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserService userService = mock(UserService.class);
        when(userService.deleteUser(Mockito.<String>any())).thenReturn("Delete User");
        ResponseEntity<HttpStatus> actualDeleteUserResult = (new UserController(userService)).deleteUser("janedoe");
        assertNull(actualDeleteUserResult.getBody());
        assertEquals(204, actualDeleteUserResult.getStatusCodeValue());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        verify(userService).deleteUser(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    void testGetUserAuthorities() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        ResponseEntity<Object> actualUserAuthorities = (new UserController(new UserService(userRepository)))
                .getUserAuthorities("janedoe");
        assertTrue(actualUserAuthorities.hasBody());
        assertEquals(200, actualUserAuthorities.getStatusCodeValue());
        assertTrue(actualUserAuthorities.getHeaders().isEmpty());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    void testGetUserAuthorities2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        LocalDate dateOfBirth = LocalDate.of(1970, 1, 1);
        ArrayList<Bike> bikes = new ArrayList<>();
        ArrayList<Ride> rides = new ArrayList<>();
        User trainer = new User();
        ArrayList<User> cyclists = new ArrayList<>();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(
                Optional.of(new User("janedoe", "iloveyou", "jane.doe@example.org", "Jane", "Doe", '\u0001', dateOfBirth,
                        bikes, rides, "https://example.org/example", trainer, cyclists, true, "Apikey", new HashSet<>())));
        ResponseEntity<Object> actualUserAuthorities = (new UserController(new UserService(userRepository)))
                .getUserAuthorities("janedoe");
        assertTrue(actualUserAuthorities.hasBody());
        assertEquals(200, actualUserAuthorities.getStatusCodeValue());
        assertTrue(actualUserAuthorities.getHeaders().isEmpty());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    void testGetUserAuthorities3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.isEnabled()).thenReturn(true);
        when(user.getTrainer()).thenReturn(new User());
        when(user.getGender()).thenReturn('A');
        when(user.getApikey()).thenReturn("Apikey");
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getFirstName()).thenReturn("Jane");
        when(user.getLastName()).thenReturn("Doe");
        when(user.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user.getUsername()).thenReturn("janedoe");
        when(user.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user.getBikes()).thenReturn(new ArrayList<>());
        when(user.getCyclists()).thenReturn(new ArrayList<>());
        when(user.getRides()).thenReturn(new ArrayList<>());
        when(user.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user));
        ResponseEntity<Object> actualUserAuthorities = (new UserController(new UserService(userRepository)))
                .getUserAuthorities("janedoe");
        assertTrue(actualUserAuthorities.hasBody());
        assertEquals(200, actualUserAuthorities.getStatusCodeValue());
        assertTrue(actualUserAuthorities.getHeaders().isEmpty());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user).isEnabled();
        verify(user, atLeast(1)).getTrainer();
        verify(user).getGender();
        verify(user).getApikey();
        verify(user).getEmail();
        verify(user).getFirstName();
        verify(user).getLastName();
        verify(user).getPhotoUrl();
        verify(user).getUsername();
        verify(user).getDateOfBirth();
        verify(user).getBikes();
        verify(user).getCyclists();
        verify(user, atLeast(1)).getRides();
        verify(user).getAuthorities();
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    void testGetUserAuthorities4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(new ArrayList<>());
        when(user2.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user2));
        ResponseEntity<Object> actualUserAuthorities = (new UserController(new UserService(userRepository)))
                .getUserAuthorities("janedoe");
        assertTrue(actualUserAuthorities.hasBody());
        assertEquals(200, actualUserAuthorities.getStatusCodeValue());
        assertTrue(actualUserAuthorities.getHeaders().isEmpty());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user2).isEnabled();
        verify(user2, atLeast(1)).getTrainer();
        verify(user2).getGender();
        verify(user2).getApikey();
        verify(user2).getEmail();
        verify(user2).getFirstName();
        verify(user2).getLastName();
        verify(user2).getPhotoUrl();
        verify(user2).getUsername();
        verify(user2).getDateOfBirth();
        verify(user2).getBikes();
        verify(user2).getCyclists();
        verify(user2, atLeast(1)).getRides();
        verify(user2).getAuthorities();
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserAuthorities5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Double.doubleValue()" because the return value of "com.example.bikegarage.model.Ride.getDistance()" is null
        //       at com.example.bikegarage.service.UserService.getTotalDistanceDriven(UserService.java:243)
        //       at com.example.bikegarage.service.UserService.transferUserModelToUserOutputDto(UserService.java:202)
        //       at com.example.bikegarage.service.UserService.getAuthorities(UserService.java:159)
        //       at com.example.bikegarage.controller.UserController.getUserAuthorities(UserController.java:117)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(new Ride());
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(rideList);
        when(user2.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user2));
        (new UserController(new UserService(userRepository))).getUserAuthorities("janedoe");
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserAuthorities6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.UsernameNotFoundException: Cannot find user janedoe
        //       at com.example.bikegarage.service.UserService.lambda$getAuthorities$7(UserService.java:158)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.example.bikegarage.service.UserService.getAuthorities(UserService.java:158)
        //       at com.example.bikegarage.controller.UserController.getUserAuthorities(UserController.java:117)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        (new UserController(new UserService(userRepository))).getUserAuthorities("janedoe");
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    void testGetUserAuthorities7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserService userService = mock(UserService.class);
        when(userService.getAuthorities(Mockito.<String>any())).thenReturn(new HashSet<>());
        ResponseEntity<Object> actualUserAuthorities = (new UserController(userService)).getUserAuthorities("janedoe");
        assertTrue(actualUserAuthorities.hasBody());
        assertEquals(200, actualUserAuthorities.getStatusCodeValue());
        assertTrue(actualUserAuthorities.getHeaders().isEmpty());
        verify(userService).getAuthorities(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    void testGetUserAuthorities8() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.getUsername()).thenThrow(new BadRequestException("An error occurred"));
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(new ArrayList<>());
        when(user2.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user2));
        assertThrows(BadRequestException.class,
                () -> (new UserController(new UserService(userRepository))).getUserAuthorities("janedoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user2).isEnabled();
        verify(user2, atLeast(1)).getTrainer();
        verify(user2).getGender();
        verify(user2).getApikey();
        verify(user2).getEmail();
        verify(user2).getFirstName();
        verify(user2).getLastName();
        verify(user2).getPhotoUrl();
        verify(user2).getUsername();
        verify(user2).getDateOfBirth();
        verify(user2).getBikes();
        verify(user2).getCyclists();
        verify(user2, atLeast(1)).getRides();
        verify(user2).getAuthorities();
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    void testGetUserAuthorities9() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        Ride ride = new Ride();
        ride.setDistance(10.0d);

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(ride);
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(rideList);
        when(user2.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user2));
        ResponseEntity<Object> actualUserAuthorities = (new UserController(new UserService(userRepository)))
                .getUserAuthorities("janedoe");
        assertTrue(actualUserAuthorities.hasBody());
        assertEquals(200, actualUserAuthorities.getStatusCodeValue());
        assertTrue(actualUserAuthorities.getHeaders().isEmpty());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user2).isEnabled();
        verify(user2, atLeast(1)).getTrainer();
        verify(user2).getGender();
        verify(user2).getApikey();
        verify(user2).getEmail();
        verify(user2).getFirstName();
        verify(user2).getLastName();
        verify(user2).getPhotoUrl();
        verify(user2).getUsername();
        verify(user2).getDateOfBirth();
        verify(user2).getBikes();
        verify(user2).getCyclists();
        verify(user2, atLeast(1)).getRides();
        verify(user2).getAuthorities();
        verify(user).getUsername();
    }

    /**
     * Method under test: {@link UserController#getUserAuthorities(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserAuthorities10() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.bikegarage.model.Ride.getDistance()" because "ride" is null
        //       at com.example.bikegarage.service.UserService.getTotalDistanceDriven(UserService.java:243)
        //       at com.example.bikegarage.service.UserService.transferUserModelToUserOutputDto(UserService.java:202)
        //       at com.example.bikegarage.service.UserService.getAuthorities(UserService.java:159)
        //       at com.example.bikegarage.controller.UserController.getUserAuthorities(UserController.java:117)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(null);
        User user2 = mock(User.class);
        when(user2.isEnabled()).thenReturn(true);
        when(user2.getTrainer()).thenReturn(user);
        when(user2.getGender()).thenReturn('A');
        when(user2.getApikey()).thenReturn("Apikey");
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getFirstName()).thenReturn("Jane");
        when(user2.getLastName()).thenReturn("Doe");
        when(user2.getPhotoUrl()).thenReturn("https://example.org/example");
        when(user2.getUsername()).thenReturn("janedoe");
        when(user2.getDateOfBirth()).thenReturn(LocalDate.of(1970, 1, 1));
        when(user2.getBikes()).thenReturn(new ArrayList<>());
        when(user2.getCyclists()).thenReturn(new ArrayList<>());
        when(user2.getRides()).thenReturn(rideList);
        when(user2.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user2));
        (new UserController(new UserService(userRepository))).getUserAuthorities("janedoe");
    }

    /**
     * Method under test: {@link UserController#addUserAuthority(String, Map)}
     */
    @Test
    void testAddUserAuthority() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        UserController userController = new UserController(new UserService(userRepository));
        ResponseEntity<Object> actualAddUserAuthorityResult = userController.addUserAuthority("janedoe", new HashMap<>());
        assertNull(actualAddUserAuthorityResult.getBody());
        assertEquals(204, actualAddUserAuthorityResult.getStatusCodeValue());
        assertTrue(actualAddUserAuthorityResult.getHeaders().isEmpty());
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#addUserAuthority(String, Map)}
     */
    @Test
    void testAddUserAuthority2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenThrow(new BadRequestException("An error occurred"));
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        UserController userController = new UserController(new UserService(userRepository));
        assertThrows(BadRequestException.class, () -> userController.addUserAuthority("janedoe", new HashMap<>()));
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#addUserAuthority(String, Map)}
     */
    @Test
    void testAddUserAuthority3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        User user = mock(User.class);
        doNothing().when(user).addAuthority(Mockito.<Authority>any());
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserController userController = new UserController(new UserService(userRepository));
        ResponseEntity<Object> actualAddUserAuthorityResult = userController.addUserAuthority("janedoe", new HashMap<>());
        assertNull(actualAddUserAuthorityResult.getBody());
        assertEquals(204, actualAddUserAuthorityResult.getStatusCodeValue());
        assertTrue(actualAddUserAuthorityResult.getHeaders().isEmpty());
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user).addAuthority(Mockito.<Authority>any());
    }

    /**
     * Method under test: {@link UserController#addUserAuthority(String, Map)}
     */
    @Test
    void testAddUserAuthority4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        UserController userController = new UserController(new UserService(userRepository));
        assertThrows(BadRequestException.class, () -> userController.addUserAuthority("janedoe", new HashMap<>()));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#addUserAuthority(String, Map)}
     */
    @Test
    void testAddUserAuthority5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserController userController = new UserController(null);
        assertThrows(BadRequestException.class, () -> userController.addUserAuthority("janedoe", new HashMap<>()));
    }

    /**
     * Method under test: {@link UserController#addUserAuthority(String, Map)}
     */
    @Test
    void testAddUserAuthority6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserService userService = mock(UserService.class);
        doNothing().when(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
        UserController userController = new UserController(userService);
        ResponseEntity<Object> actualAddUserAuthorityResult = userController.addUserAuthority("janedoe", new HashMap<>());
        assertNull(actualAddUserAuthorityResult.getBody());
        assertEquals(204, actualAddUserAuthorityResult.getStatusCodeValue());
        assertTrue(actualAddUserAuthorityResult.getHeaders().isEmpty());
        verify(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link UserController#deleteUserAuthority(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUserAuthority() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at com.example.bikegarage.service.UserService.removeAuthority(UserService.java:173)
        //       at com.example.bikegarage.controller.UserController.deleteUserAuthority(UserController.java:134)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        (new UserController(new UserService(userRepository))).deleteUserAuthority("janedoe", "JaneDoe");
    }

    /**
     * Method under test: {@link UserController#deleteUserAuthority(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUserAuthority2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at com.example.bikegarage.service.UserService.removeAuthority(UserService.java:173)
        //       at com.example.bikegarage.controller.UserController.deleteUserAuthority(UserController.java:134)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getAuthorities()).thenReturn(new HashSet<>());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user));
        (new UserController(new UserService(userRepository))).deleteUserAuthority("janedoe", "JaneDoe");
    }

    /**
     * Method under test: {@link UserController#deleteUserAuthority(String, String)}
     */
    @Test
    void testDeleteUserAuthority3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        HashSet<Authority> authoritySet = new HashSet<>();
        authoritySet.add(new Authority("janedoe", "JaneDoe"));
        User user = mock(User.class);
        doNothing().when(user).removeAuthority(Mockito.<Authority>any());
        when(user.getAuthorities()).thenReturn(authoritySet);
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        ResponseEntity<Object> actualDeleteUserAuthorityResult = (new UserController(new UserService(userRepository)))
                .deleteUserAuthority("janedoe", "JaneDoe");
        assertNull(actualDeleteUserAuthorityResult.getBody());
        assertEquals(204, actualDeleteUserAuthorityResult.getStatusCodeValue());
        assertTrue(actualDeleteUserAuthorityResult.getHeaders().isEmpty());
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user).getAuthorities();
        verify(user).removeAuthority(Mockito.<Authority>any());
    }

    /**
     * Method under test: {@link UserController#deleteUserAuthority(String, String)}
     */
    @Test
    void testDeleteUserAuthority4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        HashSet<Authority> authoritySet = new HashSet<>();
        authoritySet.add(new Authority("janedoe", "JaneDoe"));
        User user = mock(User.class);
        doThrow(new BadRequestException("An error occurred")).when(user).removeAuthority(Mockito.<Authority>any());
        when(user.getAuthorities()).thenReturn(authoritySet);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user));
        assertThrows(BadRequestException.class,
                () -> (new UserController(new UserService(userRepository))).deleteUserAuthority("janedoe", "JaneDoe"));
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(user).getAuthorities();
        verify(user).removeAuthority(Mockito.<Authority>any());
    }

    /**
     * Method under test: {@link UserController#deleteUserAuthority(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUserAuthority5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at com.example.bikegarage.service.UserService.removeAuthority(UserService.java:173)
        //       at com.example.bikegarage.controller.UserController.deleteUserAuthority(UserController.java:134)
        //   See https://diff.blue/R013 to resolve this issue.

        HashSet<Authority> authoritySet = new HashSet<>();
        authoritySet.add(new Authority("janedoe", "Authority"));
        User user = mock(User.class);
        doNothing().when(user).removeAuthority(Mockito.<Authority>any());
        when(user.getAuthorities()).thenReturn(authoritySet);
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        (new UserController(new UserService(userRepository))).deleteUserAuthority("janedoe", "JaneDoe");
    }

    /**
     * Method under test: {@link UserController#deleteUserAuthority(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUserAuthority6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.equalsIgnoreCase(String)" because the return value of "com.example.bikegarage.model.Authority.getAuthority()" is null
        //       at com.example.bikegarage.service.UserService.lambda$removeAuthority$10(UserService.java:173)
        //       at java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
        //       at java.util.HashMap$KeySpliterator.tryAdvance(HashMap.java:1728)
        //       at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.findAny(ReferencePipeline.java:652)
        //       at com.example.bikegarage.service.UserService.removeAuthority(UserService.java:173)
        //       at com.example.bikegarage.controller.UserController.deleteUserAuthority(UserController.java:134)
        //   See https://diff.blue/R013 to resolve this issue.

        HashSet<Authority> authoritySet = new HashSet<>();
        authoritySet.add(new Authority());
        User user = mock(User.class);
        doNothing().when(user).removeAuthority(Mockito.<Authority>any());
        when(user.getAuthorities()).thenReturn(authoritySet);
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        (new UserController(new UserService(userRepository))).deleteUserAuthority("janedoe", "JaneDoe");
    }

    /**
     * Method under test: {@link UserController#deleteUserAuthority(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUserAuthority7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.bikegarage.model.Authority.getAuthority()" because "a" is null
        //       at com.example.bikegarage.service.UserService.lambda$removeAuthority$10(UserService.java:173)
        //       at java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
        //       at java.util.HashMap$KeySpliterator.tryAdvance(HashMap.java:1728)
        //       at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.findAny(ReferencePipeline.java:652)
        //       at com.example.bikegarage.service.UserService.removeAuthority(UserService.java:173)
        //       at com.example.bikegarage.controller.UserController.deleteUserAuthority(UserController.java:134)
        //   See https://diff.blue/R013 to resolve this issue.

        HashSet<Authority> authoritySet = new HashSet<>();
        authoritySet.add(null);
        User user = mock(User.class);
        doNothing().when(user).removeAuthority(Mockito.<Authority>any());
        when(user.getAuthorities()).thenReturn(authoritySet);
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        (new UserController(new UserService(userRepository))).deleteUserAuthority("janedoe", "JaneDoe");
    }

    /**
     * Method under test: {@link UserController#deleteUserAuthority(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUserAuthority8() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.example.bikegarage.exception.UsernameNotFoundException: Cannot find user janedoe
        //       at com.example.bikegarage.service.UserService.lambda$removeAuthority$9(UserService.java:172)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.example.bikegarage.service.UserService.removeAuthority(UserService.java:172)
        //       at com.example.bikegarage.controller.UserController.deleteUserAuthority(UserController.java:134)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        (new UserController(new UserService(userRepository))).deleteUserAuthority("janedoe", "JaneDoe");
    }

    /**
     * Method under test: {@link UserController#deleteUserAuthority(String, String)}
     */
    @Test
    void testDeleteUserAuthority9() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserService userService = mock(UserService.class);
        doNothing().when(userService).removeAuthority(Mockito.<String>any(), Mockito.<String>any());
        ResponseEntity<Object> actualDeleteUserAuthorityResult = (new UserController(userService))
                .deleteUserAuthority("janedoe", "JaneDoe");
        assertNull(actualDeleteUserAuthorityResult.getBody());
        assertEquals(204, actualDeleteUserAuthorityResult.getStatusCodeValue());
        assertTrue(actualDeleteUserAuthorityResult.getHeaders().isEmpty());
        verify(userService).removeAuthority(Mockito.<String>any(), Mockito.<String>any());
    }
}

