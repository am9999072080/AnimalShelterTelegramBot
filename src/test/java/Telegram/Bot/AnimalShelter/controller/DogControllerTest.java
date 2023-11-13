package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.service.impl.DogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DogController.class)
@ExtendWith(MockitoExtension.class)
class DogControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    DogServiceImpl dogService;

    static final Dog Dog_1 = new Dog(1L, "dog1", 1, true, true, 1L, 1L);
    static final Dog Dog_2 = new Dog(1L, "dog2", 1, false, false, 1L, 1L);
    static final Dog Dog_3 = new Dog(1L, "dog3", 1, true, true, 1L, 1L);

    @Test
    void shouldReturnDogsById() throws Exception {
        when(dogService.getById(Dog_1.getId())).thenReturn(Dog_1);
        mockMvc.perform(get("/dogs/id?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("dog1"))
                .andExpect(jsonPath("age").value(1))
                .andExpect(jsonPath("healthy").value(true))
                .andExpect((jsonPath("vaccinated").value(true)))
                .andExpect(jsonPath("ownerId").value(1L))
                .andExpect(jsonPath("shelterId").value(1L));
        verify(dogService, times(1)).getById(Dog_1.getId());
    }

    @Test
    void shouldCreateNewDog() throws Exception {
        when(dogService.create(any(Dog.class))).thenReturn(Dog_1);
        mockMvc.perform(post("/dogs")
                        .param("name", "dog1")
                        .param("age", "1")
                        .param("isHealthy", "true")
                        .param("vaccinated", "true")
                        .param("shelterId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("dog1"))
                .andExpect(jsonPath("age").value(1))
                .andExpect(jsonPath("healthy").value(true))
                .andExpect((jsonPath("vaccinated").value(true)))
                .andExpect(jsonPath("shelterId").value(1L));
        verify(dogService, times(1)).create(any(Dog.class));
    }

    @Test
    void shouldReturnAllDogs() throws Exception {
        List<Dog> list = List.of(Dog_1, Dog_3);
        when(dogService.getAll()).thenReturn(list);
        mockMvc.perform(get("/dogs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("dog1"))
                .andExpect(jsonPath("$[0].age").value(1))
                .andExpect(jsonPath("$[0].healthy").value(true))
                .andExpect(jsonPath("$[0].vaccinated").value(true))
                .andExpect(jsonPath("$[0].ownerId").value(1L))
                .andExpect(jsonPath("$[0].shelterId").value(1L))
                .andExpect(jsonPath("$[1].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("dog3"))
                .andExpect(jsonPath("$[1].age").value(1))
                .andExpect(jsonPath("$[1].healthy").value(true))
                .andExpect(jsonPath("$[1].vaccinated").value(true))
                .andExpect(jsonPath("$[1].ownerId").value(1L))
                .andExpect(jsonPath("$[1].shelterId").value(1L));

    }

    @Test
    void shouldReturnOwnerById() throws Exception {
        when(dogService.getAllByOwnerId(Dog_1.getOwnerId())).thenReturn(List.of(Dog_1));
        mockMvc.perform(get("/dogs/ownerId?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("dog1"))
                .andExpect(jsonPath("$.[0].age").value(1))
                .andExpect(jsonPath("$.[0].healthy").value(true))
                .andExpect(jsonPath("$.[0].vaccinated").value(true))
                .andExpect(jsonPath("$.[0].ownerId").value(1L))
                .andExpect(jsonPath("$.[0].shelterId").value(1L));
        verify(dogService, times(1)).getAllByOwnerId(Dog_1.getOwnerId());
    }

    @Test
    void shouldUpdateAndReturnDogs() throws Exception {
        when(dogService.update(any(Dog.class))).thenReturn(Dog_2);
        mockMvc.perform(put("/dogs")
                        .param("id", "1")
                        .param("name", "dog2")
                        .param("age", "1")
                        .param("isHealthy", "false")
                        .param("vaccinated", "false")
                        .param("ownerId", "1")
                        .param("shelterId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("dog2"))
                .andExpect(jsonPath("age").value(1))
                .andExpect(jsonPath("healthy").value(false))
                .andExpect(jsonPath("vaccinated").value(false))
                .andExpect(jsonPath("ownerId").value(1L))
                .andExpect(jsonPath("shelterId").value(1L));
    }

    @Test
    void shouldDeleteDogById() throws Exception {
        mockMvc.perform(delete("/dogs/id?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dog removed from list"));
        verify(dogService, times(1)).delete(1L);
    }
}