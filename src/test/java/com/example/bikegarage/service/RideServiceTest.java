package com.example.bikegarage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bikegarage.dto.input.RideInputDto;
import com.example.bikegarage.dto.output.RideOutputDto;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.exception.UsernameNotFoundException;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.File;
import com.example.bikegarage.model.Review;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import com.example.bikegarage.repository.BikeRepository;
import com.example.bikegarage.repository.FileRepository;
import com.example.bikegarage.repository.PartRepository;

import com.example.bikegarage.repository.RideRepository;
import com.example.bikegarage.repository.UserRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ContextConfiguration(classes = {RideService.class})
@ExtendWith(MockitoExtension.class)
class RideServiceTest {
    @MockBean
    private BikeRepository bikeRepository;

    @MockBean
    private FileRepository fileRepository;

    @MockBean
    private PartRepository partRepository;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private RideRepository rideRepository;

    @InjectMocks
    private RideService rideService;

    /**
     * Method under test: {@link RideService#getRideById(Long)}
     */
    @Test
    void testGetRideById() throws RecordNotFoundException {
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Ride()));
        RideOutputDto actualRideById = rideService.getRideById(1L);
        assertNull(actualRideById.averagePower);
        assertNull(actualRideById.user);
        assertNull(actualRideById.titleRide);
        assertNull(actualRideById.timeRide);
        assertNull(actualRideById.subTitleRide);
        assertNull(actualRideById.normalizedPower);
        assertNull(actualRideById.id);
        assertNull(actualRideById.distance);
        assertNull(actualRideById.date);
        assertNull(actualRideById.bike);
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RideService#getRideById(Long)}
     */
    @Test
    void testGetRideById2() throws RecordNotFoundException {
        LocalDateTime date = LocalDate.of(1970, 1, 1).atStartOfDay();
        Bike bike = new Bike();
        User user = new User();
        Review review = new Review();
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new Ride(1L, "Dr", "Dr", 10.0d, date,
                1L, 1L, null, bike, user, review, new File("foo.txt", "text/plain", "https://example.org/example"))));
        RideOutputDto actualRideById = rideService.getRideById(1L);
        assertEquals(1L, actualRideById.averagePower.longValue());
        assertEquals("https://example.org/example", actualRideById.url);
        assertEquals(10.0d, actualRideById.distance.doubleValue());
        assertEquals("Dr", actualRideById.subTitleRide);
        assertNull(actualRideById.timeRide);
        assertEquals(1L, actualRideById.id.longValue());
        assertEquals("Dr", actualRideById.titleRide);
        assertEquals(1L, actualRideById.normalizedPower.longValue());
        assertNull(actualRideById.reviewRating);
        assertEquals("1970-01-01", actualRideById.date.toLocalDate().toString());
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RideService#getRideById(Long)}
     */
    @Test
    void testGetRideById3() throws RecordNotFoundException {
        Ride ride = mock(Ride.class);
        when(ride.getId()).thenThrow(new UsernameNotFoundException("janedoe"));
        Optional<Ride> ofResult = Optional.of(ride);
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class, () -> rideService.getRideById(1L));
        verify(rideRepository).findById(Mockito.<Long>any());
        verify(ride).getId();
    }

    /**
     * Method under test: {@link RideService#getRideById(Long)}
     */
    @Test
    void testGetRideById4() throws RecordNotFoundException {
        when(rideRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        new UsernameNotFoundException("janedoe");
        assertThrows(RecordNotFoundException.class, () -> rideService.getRideById(1L));
        verify(rideRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RideService#getAllRides()}
     */
    @Test
    void testGetAllRides() throws RecordNotFoundException {
        when(rideRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(RecordNotFoundException.class, () -> rideService.getAllRides());
        verify(rideRepository).findAll();
    }

    /**
     * Method under test: {@link RideService#getAllRides()}
     */
    @Test
    void testGetAllRides2() throws RecordNotFoundException {
        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(new Ride());
        when(rideRepository.findAll()).thenReturn(rideList);
        List<RideOutputDto> actualAllRides = rideService.getAllRides();
        assertEquals(1, actualAllRides.size());
        RideOutputDto getResult = actualAllRides.get(0);
        assertNull(getResult.averagePower);
        assertNull(getResult.user);
        assertNull(getResult.titleRide);
        assertNull(getResult.timeRide);
        assertNull(getResult.subTitleRide);
        assertNull(getResult.normalizedPower);
        assertNull(getResult.id);
        assertNull(getResult.distance);
        assertNull(getResult.date);
        assertNull(getResult.bike);
        verify(rideRepository).findAll();
    }

    /**
     * Method under test: {@link RideService#getAllRides()}
     */
    @Test
    void testGetAllRides3() throws RecordNotFoundException {
        when(rideRepository.findAll()).thenThrow(new RecordNotFoundException("An error occurred"));
        assertThrows(RecordNotFoundException.class, () -> rideService.getAllRides());
        verify(rideRepository).findAll();
    }

    /**
     * Method under test: {@link RideService#getAllRides()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllRides4() throws RecordNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.example.bikegarage.model.Ride.getId()" because "ride" is null
        //       at com.example.bikegarage.service.RideService.transferRideModelToRideOutputDto(RideService.java:138)
        //       at com.example.bikegarage.service.RideService.getAllRides(RideService.java:49)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(null);
        when(rideRepository.findAll()).thenReturn(rideList);
        rideService.getAllRides();
    }

    /**
     * Method under test: {@link RideService#getAllRides()}
     */
    @Test
    void testGetAllRides5() throws RecordNotFoundException {
        ArrayList<Ride> rideList = new ArrayList<>();
        LocalDateTime date = LocalDate.of(1970, 1, 1).atStartOfDay();
        Bike bike = new Bike();
        User user = new User();
        Review review = new Review();
        rideList.add(new Ride(1L, "Dr", "Dr", 10.0d, date, 1L, 1L, null, bike, user, review,
                new File("foo.txt", "text/plain", "https://example.org/example")));
        when(rideRepository.findAll()).thenReturn(rideList);
        List<RideOutputDto> actualAllRides = rideService.getAllRides();
        assertEquals(1, actualAllRides.size());
        RideOutputDto getResult = actualAllRides.get(0);
        assertEquals(1L, getResult.averagePower.longValue());
        assertSame(user, getResult.user);
        assertEquals("https://example.org/example", getResult.url);
        assertSame(bike, getResult.bike);
        assertEquals(10.0d, getResult.distance.doubleValue());
        assertEquals("Dr", getResult.subTitleRide);
        assertNull(getResult.timeRide);
        assertEquals(1L, getResult.id.longValue());
        assertEquals("Dr", getResult.titleRide);
        assertEquals(1L, getResult.normalizedPower.longValue());
        assertNull(getResult.reviewRating);
        assertEquals("1970-01-01", getResult.date.toLocalDate().toString());
        verify(rideRepository).findAll();
    }

    /**
     * Method under test: {@link RideService#getAllRides()}
     */
    @Test
    void testGetAllRides6() throws RecordNotFoundException {
        Review review = mock(Review.class);
        when(review.getRating()).thenThrow(new RecordNotFoundException("An error occurred"));
        LocalDateTime date = LocalDate.of(1970, 1, 1).atStartOfDay();
        Bike bike = new Bike();
        User user = new User();
        Ride ride = new Ride(1L, "Dr", "Dr", 10.0d, date, 1L, 1L, null, bike, user, review,
                new File("foo.txt", "text/plain", "https://example.org/example"));

        ArrayList<Ride> rideList = new ArrayList<>();
        rideList.add(ride);
        when(rideRepository.findAll()).thenReturn(rideList);
        assertThrows(RecordNotFoundException.class, () -> rideService.getAllRides());
        verify(rideRepository).findAll();
        verify(review).getRating();
    }

    /**
     * Method under test: {@link RideService#transferRideInputDtoToRide(RideInputDto)}
     */
    @Test
    void testTransferRideInputDtoToRide() {
        User user = new User();
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user));
        RideInputDto rideInputDto = new RideInputDto();
        rideInputDto.averagePower = 1L;
        Bike bike = new Bike();
        rideInputDto.bike = bike;
        rideInputDto.date = LocalDate.of(1970, 1, 1).atStartOfDay();
        rideInputDto.distance = 10.0d;
        rideInputDto.normalizedPower = 1L;
        rideInputDto.subTitleRide = "Dr";
        rideInputDto.timeRide = null;
        rideInputDto.titleRide = "Dr";
        rideInputDto.username = "janedoe";
        Ride actualTransferRideInputDtoToRideResult = rideService.transferRideInputDtoToRide(rideInputDto);
        assertEquals(1L, actualTransferRideInputDtoToRideResult.getAveragePower().longValue());
        assertSame(user, actualTransferRideInputDtoToRideResult.getUser());
        assertEquals("Dr", actualTransferRideInputDtoToRideResult.getTitleRide());
        assertSame(bike, actualTransferRideInputDtoToRideResult.getBike());
        assertEquals(10.0d, actualTransferRideInputDtoToRideResult.getDistance().doubleValue());
        assertEquals("Dr", actualTransferRideInputDtoToRideResult.getSubTitleRide());
        assertNull(actualTransferRideInputDtoToRideResult.getTimeRide());
        assertEquals(1L, actualTransferRideInputDtoToRideResult.getNormalizedPower().longValue());
        assertEquals("1970-01-01", actualTransferRideInputDtoToRideResult.getDate().toLocalDate().toString());
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link RideService#transferRideInputDtoToRide(RideInputDto)}
     */
    @Test
    void testTransferRideInputDtoToRide2() {
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        RideInputDto rideInputDto = new RideInputDto();
        rideInputDto.averagePower = 1L;
        rideInputDto.bike = new Bike();
        rideInputDto.date = LocalDate.of(1970, 1, 1).atStartOfDay();
        rideInputDto.distance = 10.0d;
        rideInputDto.normalizedPower = 1L;
        rideInputDto.subTitleRide = "Dr";
        rideInputDto.timeRide = null;
        rideInputDto.titleRide = "Dr";
        rideInputDto.username = "janedoe";
        assertThrows(RecordNotFoundException.class, () -> rideService.transferRideInputDtoToRide(rideInputDto));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link RideService#transferRideInputDtoToRide(RideInputDto)}
     */
    @Test
    void testTransferRideInputDtoToRide3() {
        when(userRepository.findByUsername(Mockito.<String>any()))
                .thenThrow(new RecordNotFoundException("An error occurred"));
        RideInputDto rideInputDto = new RideInputDto();
        rideInputDto.averagePower = 1L;
        rideInputDto.bike = new Bike();
        rideInputDto.date = LocalDate.of(1970, 1, 1).atStartOfDay();
        rideInputDto.distance = 10.0d;
        rideInputDto.normalizedPower = 1L;
        rideInputDto.subTitleRide = "Dr";
        rideInputDto.timeRide = null;
        rideInputDto.titleRide = "Dr";
        rideInputDto.username = "janedoe";
        assertThrows(RecordNotFoundException.class, () -> rideService.transferRideInputDtoToRide(rideInputDto));
        verify(userRepository).findByUsername(Mockito.<String>any());
    }
}

