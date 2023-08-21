package com.example.bikegarage.controller;

import com.example.bikegarage.dto.output.PartOutputDto;
import com.example.bikegarage.model.Bike;
import com.example.bikegarage.model.Part;
import com.example.bikegarage.model.PartType;
import com.example.bikegarage.repository.BikeRepository;
import com.example.bikegarage.repository.PartRepository;
import com.example.bikegarage.service.PartService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ActiveProfiles("test")
class PartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private PartService partService;

    Part part1;
    Part part2;
    Bike bike1;
    PartOutputDto partOutputDto1;
    PartOutputDto partOutputDto2;


    @BeforeEach
    public void setUp() {
        partRepository.deleteAll();
        bike1 = new Bike();
        part1 = new Part(1L, PartType.CHAIN, 505.0, 5000.0, bike1, LocalDateTime.of(2023, 6, 1, 0,0));
        part2 = new Part(2L, PartType.CASSETTE, 205.0, 8000.0, bike1, LocalDateTime.of(2023, 6, 1, 0, 0));
        partOutputDto1 = new PartOutputDto(1L, PartType.CHAIN, 505.0, 5000.0, bike1, LocalDateTime.of(2023, 6, 1, 0, 0));
        partOutputDto2 = new PartOutputDto(999L, PartType.CASSETTE, 205.0, 8000.0, bike1, LocalDateTime.of(2023, 6, 1, 0, 0));
        bikeRepository.save(bike1);
        partRepository.save(part1);
        partRepository.save(part2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPartById() throws Exception {
//        given(partService.getPartById(1L)).willReturn(partOutputDto1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String expectedInstallationDate = part1.getInstallationDate().format(formatter);

        mockMvc.perform(MockMvcRequestBuilders.get("/bikeparts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(part1.getId()))
                .andExpect(jsonPath("$.currentDistanceDriven").value(part1.getCurrentDistanceDriven()))
                .andExpect(jsonPath("$.maxDistance").value(part1.getMaxDistance()))
                .andExpect(jsonPath("$.bike.id").value(part1.getBike().getId()))

                .andExpect(jsonPath("$.installationDate").value(expectedInstallationDate));
//                .andExpect(jsonPath("$.partType").value(part1.getPartType()));

    }

    @Test
    void getAllBikeParts() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String expectedInstallationDate1 = part1.getInstallationDate().format(formatter);
        String expectedInstallationDate2 = part2.getInstallationDate().format(formatter);

        mockMvc.perform(MockMvcRequestBuilders.get("/bikeparts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(part1.getId()))
                .andExpect(jsonPath("$[0].currentDistanceDriven").value(part1.getCurrentDistanceDriven()))
                .andExpect(jsonPath("$[0].maxDistance").value(part1.getMaxDistance()))
                .andExpect(jsonPath("$[0].bike.id").value(part1.getBike().getId()))
                .andExpect(jsonPath("$[0].installationDate").value(expectedInstallationDate1))
                .andExpect(jsonPath("$[1].id").value(part2.getId()))
                .andExpect(jsonPath("$[1].currentDistanceDriven").value(part2.getCurrentDistanceDriven()))
                .andExpect(jsonPath("$[1].maxDistance").value(part2.getMaxDistance()))
                .andExpect(jsonPath("$[1].bike.id").value(part2.getBike().getId()))
                .andExpect(jsonPath("$[1].installationDate").value(expectedInstallationDate2));
    }



}