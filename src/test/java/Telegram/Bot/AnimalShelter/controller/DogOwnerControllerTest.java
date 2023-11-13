package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.DogOwner;
import Telegram.Bot.AnimalShelter.service.DogOwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DogOwnerController.class)
@ExtendWith(MockitoExtension.class)
class DogOwnerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    DogOwnerService dogOwnerService;

    private final DogOwner dogOwner1 = new DogOwner(1L, "fname", "lname", "123", null, null);
    private final DogOwner dogOwner2 = new DogOwner(2L, "fname", "lname", "321", null, null);
    private final List<DogOwner> dogOwnerList = List.of(dogOwner1, dogOwner2);


    @Test
    void shouldCreateAndReturnDogOwner() throws Exception {
        when(dogOwnerService.create(dogOwner1, Adaptation.AnimalType.DOG, 1L)).thenReturn(dogOwner1);
        mockMvc.perform(post("/dogOwners")
                        .param("telegramId", "1")
                        .param("firstName", "fname")
                        .param("lastName", "lname")
                        .param("phone", "123")
                        .param("animalId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("telegramId").value(1L))
                .andExpect(jsonPath("firstName").value("fname"))
                .andExpect(jsonPath("lastName").value("lname"))
                .andExpect(jsonPath("phoneNumber").value("123"));
        verify(dogOwnerService, times(1)).create(dogOwner1, Adaptation.AnimalType.DOG, 1L);
    }

    @Test
    void shouldCreateDogOwnerFromUserAndReturn() throws Exception {
        when(dogOwnerService.create(1L, Adaptation.AnimalType.DOG, 1L)).thenReturn(dogOwner1);
        mockMvc.perform(post("/dogOwners/user")
                        .param("id", "1")
                        .param("animalId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("telegramId").value(1L))
                .andExpect(jsonPath("firstName").value("fname"))
                .andExpect(jsonPath("lastName").value("lname"))
                .andExpect(jsonPath("phoneNumber").value("123"));
        verify(dogOwnerService, times(1)).create(1L, Adaptation.AnimalType.DOG, 1L);
    }

    @Test
    void shouldReturnAllDogOwners() throws Exception {
        when(dogOwnerService.getAll()).thenReturn(dogOwnerList);
        mockMvc.perform(get("/dogOwners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].telegramId").value(1L))
                .andExpect(jsonPath("$.[0].firstName").value("fname"))
                .andExpect(jsonPath("$.[0].lastName").value("lname"))
                .andExpect(jsonPath("$.[0].phoneNumber").value("123"))
                .andExpect(jsonPath("$.[1].telegramId").value(2L))
                .andExpect(jsonPath("$.[1].firstName").value("fname"))
                .andExpect(jsonPath("$.[1].lastName").value("lname"))
                .andExpect(jsonPath("$.[1].phoneNumber").value("321"));
        verify(dogOwnerService, times(1)).getAll();
    }

    @Test
    void shouldReturnDogOwnerById() throws Exception {
        when(dogOwnerService.getById(dogOwner1.getTelegramId())).thenReturn(dogOwner1);
        mockMvc.perform(get("/dogOwners/id")
                        .param("dogOwnerId", String.valueOf(dogOwner1.getTelegramId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("telegramId").value(1L))
                .andExpect(jsonPath("firstName").value("fname"))
                .andExpect(jsonPath("lastName").value("lname"));
        verify(dogOwnerService, times(1)).getById(dogOwner1.getTelegramId());
    }

    @Test
    void shouldUpdateAndReturnDogOwner() throws Exception {
        when(dogOwnerService.update(any(DogOwner.class))).thenReturn(dogOwner1);
        mockMvc.perform(put("/dogOwners")
                        .param("telegramId", "1")
                        .param("firstName", "fname")
                        .param("lastName", "lname")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("telegramId").value(1L))
                .andExpect(jsonPath("firstName").value("fname"))
                .andExpect(jsonPath("lastName").value("lname"));
        verify(dogOwnerService, times(1)).update(any(DogOwner.class));
    }

    @Test
    void shouldReturnMessageWhenDogOwnerDeleted() throws Exception {
        doNothing().when(dogOwnerService).deleteById(1L);
        mockMvc.perform(delete("/dogOwners/id")
                        .param("dogOwnerId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dog owner successfully removed."));
        verify(dogOwnerService, times(1)).deleteById(1L);
    }
}