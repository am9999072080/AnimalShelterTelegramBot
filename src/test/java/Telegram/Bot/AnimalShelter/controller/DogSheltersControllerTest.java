package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.entity.DogShelter;
import Telegram.Bot.AnimalShelter.service.impl.DogShelterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DogSheltersController.class)
@ExtendWith(MockitoExtension.class)
class DogSheltersControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    DogShelterServiceImpl dogShelterService;
    private final DogShelter dogShelter = new DogShelter(1L, "qwe", "qwe", "qwe", "qwe", "qwe", "qwe");
    Dog dog1 = new Dog(1L, "dog", 1, true, true, null, 1L);
    private final List<Dog> animalList = List.of(dog1);
    private final List<DogShelter> shelterList = List.of(dogShelter);

    @Test
    void shouldCreateAndReturnDogShelter() throws Exception {
        when(dogShelterService.create(any(DogShelter.class))).thenReturn(dogShelter);
        mockMvc.perform(post("/dogs/shelters/")
                        .param("name", "qwe")
                        .param("location", "qwe")
                        .param("timetable", "qwe")
                        .param("aboutMe", "qwe")
                        .param("security", "qwe")
                        .param("safetyAdvice", "qwe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("qwe"))
                .andExpect(jsonPath("location").value("qwe"))
                .andExpect(jsonPath("workingHours").value("qwe"))
                .andExpect(jsonPath("aboutMe").value("qwe"))
                .andExpect(jsonPath("security").value("qwe"))
                .andExpect(jsonPath("safety").value("qwe"));
        verify(dogShelterService, times(1)).create(any(DogShelter.class));

    }

    @Test
    void shouldUpdateDogShelter() throws Exception {
        when(dogShelterService.update(any(DogShelter.class))).thenReturn(dogShelter);
        mockMvc.perform(put("/dogs/shelters/")
                        .param("id", "1")
                        .param("name", "qwe")
                        .param("location", "qwe")
                        .param("workingHours", "qwe")
                        .param("aboutMe", "qwe")
                        .param("security", "qwe")
                        .param("safetyAdvice", "qwe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("qwe"))
                .andExpect(jsonPath("location").value("qwe"))
                .andExpect(jsonPath("workingHours").value("qwe"))
                .andExpect(jsonPath("aboutMe").value("qwe"))
                .andExpect(jsonPath("security").value("qwe"))
                .andExpect(jsonPath("safety").value("qwe"));
        verify(dogShelterService, times(1)).update(any(DogShelter.class));


    }

    @Test
    void shouldReturnAllDogShelters() throws Exception {
        when(dogShelterService.getShelter()).thenReturn(shelterList);
        mockMvc.perform(get("/dogs/shelters/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("qwe"))
                .andExpect(jsonPath("$.[0].location").value("qwe"))
                .andExpect(jsonPath("$.[0].workingHours").value("qwe"))
                .andExpect(jsonPath("$.[0].aboutMe").value("qwe"))
                .andExpect(jsonPath("$.[0].security").value("qwe"))
                .andExpect(jsonPath("$.[0].safety").value("qwe"));
        verify(dogShelterService, times(1)).getShelter();
    }

    @Test
    void shouldReturnShelterById() throws Exception {
        when(dogShelterService.getShelterById(dogShelter.getId())).thenReturn(dogShelter);
        mockMvc.perform(get("/dogs/shelters/id" + dogShelter.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("qwe"))
                .andExpect(jsonPath("location").value("qwe"))
                .andExpect(jsonPath("workingHours").value("qwe"))
                .andExpect(jsonPath("aboutMe").value("qwe"))
                .andExpect(jsonPath("security").value("qwe"))
                .andExpect(jsonPath("safety").value("qwe"));
        verify(dogShelterService, times(1)).getShelterById(1L);
    }

    @Test
    void shouldReturnAnimalListByShelterId() throws Exception {
        when(dogShelterService.getAnimal(dogShelter.getId())).thenReturn(animalList);
        mockMvc.perform(get("/dogs/shelters/list" + dogShelter.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("dog"))
                .andExpect(jsonPath("$.[0].age").value(1))
                .andExpect(jsonPath("$.[0].healthy").value(true))
                .andExpect(jsonPath("$.[0].vaccinated").value(true));
        verify(dogShelterService, times(1)).getAnimal(dogShelter.getId());
    }


    @Test
    void shouldDeleteShelter() throws Exception {
        long shelterId = 1L;
        doReturn("Shelter removed").when(dogShelterService).delete(shelterId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/dogs/shelters/{id}", shelterId))
                .andExpect(status().isOk())
                .andExpect(content().string("Shelter removed"));

        verify(dogShelterService, times(1)).delete(shelterId);
    }

}