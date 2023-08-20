package com.example.bikegarage.service;

import com.example.bikegarage.dto.input.PartInputDto;
import com.example.bikegarage.dto.output.PartOutputDto;
import com.example.bikegarage.exception.ForbiddenException;
import com.example.bikegarage.exception.RecordNotFoundException;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Part;
import com.example.bikegarage.model.Ride;
import com.example.bikegarage.model.User;
import com.example.bikegarage.repository.BikeRepository;
import com.example.bikegarage.repository.PartRepository;
import com.example.bikegarage.repository.RideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PartServiceTest {

    @Mock
    private PartRepository partRepository;
    @Mock
    private RideRepository rideRepository;
    @Mock
    private BikeRepository bikeRepository;

    @InjectMocks
    private PartService partService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPartById() {
        Long id = 1L;
        Part part = new Part();
        part.setId(id);
        when(partRepository.findById(id)).thenReturn(Optional.of(part));

        PartOutputDto result = partService.getPartById(id);

        assertNotNull(result);
        assertEquals(id, result.id);
        verify(partRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllParts() {
        List<Part> parts = new ArrayList<>();
        parts.add(new Part());
        parts.add(new Part());
        when(partRepository.findAll()).thenReturn(parts);

        List<PartOutputDto> result = partService.getAllParts();

        assertNotNull(result);
        assertEquals(parts.size(), result.size());
        verify(partRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPartsByBike() {
        Long bikeId = 1L;
        Bike bike = new Bike();
        bike.setId(bikeId);
        Set<Part> parts = new HashSet<>();
        parts.add(new Part());
        parts.add(new Part());
        bike.setBikeParts(parts);
        when(bikeRepository.findById(bikeId)).thenReturn(Optional.of(bike));

        List<PartOutputDto> result = partService.getAllPartsByBike(bikeId);

        assertNotNull(result);
        assertEquals(parts.size(), result.size());
        verify(bikeRepository, times(1)).findById(bikeId);
    }

    @Test
    void testCreateBikePart() {
        Long bikeId = 1L;
        Bike bike = new Bike();
        bike.setId(bikeId);
        PartInputDto inputDto = new PartInputDto();
        Part part = new Part();
        part.setBike(bike);
        when(bikeRepository.findById(bikeId)).thenReturn(Optional.of(bike));
        when(rideRepository.findByBikeAndDateAfterOrDateEquals(eq(bike), any(), any())).thenReturn(new ArrayList<>());
        when(partRepository.save(any())).thenReturn(part);

        PartOutputDto result = partService.createBikePart(inputDto, bikeId);

        assertNotNull(result);
        assertEquals(bikeId, result.bike.getId());
        verify(bikeRepository, times(1)).findById(bikeId);
        verify(rideRepository, times(1)).findByBikeAndDateAfterOrDateEquals(eq(bike), any(), any());
        verify(partRepository, times(1)).save(any());
    }

    @Test
    void testUpdateBikePart() {
        Long partId = 1L;
        PartInputDto inputDto = new PartInputDto();
        Part existingPart = new Part();
        existingPart.setId(partId);
        existingPart.setBike(new Bike());
        when(partRepository.findById(partId)).thenReturn(Optional.of(existingPart));
        when(rideRepository.findByBikeAndDateAfterOrDateEquals(any(), any(), any())).thenReturn(new ArrayList<>());
        when(partRepository.save(any())).thenReturn(existingPart);

        PartOutputDto result = partService.updateBikePart(partId, inputDto);

        assertNotNull(result);
        assertEquals(partId, result.id);
        verify(partRepository, times(1)).findById(partId);
        verify(rideRepository, times(1)).findByBikeAndDateAfterOrDateEquals(any(), any(), any());
        verify(partRepository, times(1)).save(any());
    }

    @Test
    void testDeletePart() {
        Long partId = 1L;
        Part part = new Part();
        Bike bike = new Bike();
        bike.setUser(new User());
        part.setBike(bike);
        when(partRepository.findById(partId)).thenReturn(Optional.of(part));

        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThrows(ForbiddenException.class, () -> partService.deletePart(partId));

        Authentication adminAuthentication = new UsernamePasswordAuthenticationToken("admin", "admin", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(adminAuthentication);

        String result = partService.deletePart(partId);

        assertNotNull(result);
        assertEquals("Well well I hope you know what you're doing, because you just removed " + part.getPartType() + "!", result);
        verify(partRepository, times(1)).findById(partId);
        verify(partRepository, times(1)).deleteById(partId);
    }
}