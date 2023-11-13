package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Cat;
import Telegram.Bot.AnimalShelter.entity.CatShelter;
import Telegram.Bot.AnimalShelter.service.impl.CatShelterServiceImpl;
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

@WebMvcTest(CatSheltersController.class)
@ExtendWith(MockitoExtension.class)
class CatSheltersControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CatShelterServiceImpl catShelterService;
    private final CatShelter catShelter = new CatShelter(1L, "qwe", "qwe", "qwe", "qwe", "qwe", "qwe");
    Cat cat1 = new Cat(1L, "cat", 1, true, true, null, 1L);
    private final List<Cat> animalList = List.of(cat1);
    private final List<CatShelter> shelterList = List.of(catShelter);

    @Test
    void shouldCreateAndReturnCatShelter() throws Exception {
        when(catShelterService.create(any(CatShelter.class))).thenReturn(catShelter);
        mockMvc.perform(post("/cats/shelters/")
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
        verify(catShelterService, times(1)).create(any(CatShelter.class));

    }

    @Test
    void shouldUpdateCatShelter() throws Exception {
        when(catShelterService.update(any(CatShelter.class))).thenReturn(catShelter);
        mockMvc.perform(put("/cats/shelters/")
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
        verify(catShelterService, times(1)).update(any(CatShelter.class));


    }

    @Test
    void shouldReturnAllCatShelters() throws Exception {
        when(catShelterService.getShelter()).thenReturn(shelterList);
        mockMvc.perform(get("/cats/shelters/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("qwe"))
                .andExpect(jsonPath("$.[0].location").value("qwe"))
                .andExpect(jsonPath("$.[0].workingHours").value("qwe"))
                .andExpect(jsonPath("$.[0].aboutMe").value("qwe"))
                .andExpect(jsonPath("$.[0].security").value("qwe"))
                .andExpect(jsonPath("$.[0].safety").value("qwe"));
        verify(catShelterService, times(1)).getShelter();
    }

    @Test
    void shouldReturnShelterById() throws Exception {
        when(catShelterService.getShelterById(catShelter.getId())).thenReturn(catShelter);
        mockMvc.perform(get("/cats/shelters/id" + catShelter.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("qwe"))
                .andExpect(jsonPath("location").value("qwe"))
                .andExpect(jsonPath("workingHours").value("qwe"))
                .andExpect(jsonPath("aboutMe").value("qwe"))
                .andExpect(jsonPath("security").value("qwe"))
                .andExpect(jsonPath("safety").value("qwe"));
        verify(catShelterService, times(1)).getShelterById(1L);
    }

    @Test
    void shouldReturnAnimalListByShelterId() throws Exception {
        when(catShelterService.getAnimal(catShelter.getId())).thenReturn(animalList);
        mockMvc.perform(get("/cats/shelters/list" + catShelter.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("cat"))
                .andExpect(jsonPath("$.[0].age").value(1))
                .andExpect(jsonPath("$.[0].healthy").value(true))
                .andExpect(jsonPath("$.[0].vaccinated").value(true));
        verify(catShelterService, times(1)).getAnimal(catShelter.getId());
    }


    @Test
    void shouldDeleteShelter() throws Exception {
        long shelterId = 1L;
        doReturn("Shelter removed").when(catShelterService).delete(shelterId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cats/shelters/{id}", shelterId))
                .andExpect(status().isOk())
                .andExpect(content().string("Shelter removed"));

        verify(catShelterService, times(1)).delete(shelterId);
    }

}