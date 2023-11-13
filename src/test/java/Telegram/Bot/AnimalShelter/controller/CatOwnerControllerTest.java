package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.CatOwner;
import Telegram.Bot.AnimalShelter.service.CatOwnerService;
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

@WebMvcTest(CatOwnerController.class)
@ExtendWith(MockitoExtension.class)
class CatOwnerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CatOwnerService catOwnerService;

    private final CatOwner catOwner1 = new CatOwner(1L, "fname", "lname", "123", null, null);
    private final CatOwner catOwner2 = new CatOwner(2L, "fname", "lname", "321", null, null);
    private final List<CatOwner> catOwnerList = List.of(catOwner1, catOwner2);


    @Test
    void shouldCreateAndReturnCatOwner() throws Exception {
        when(catOwnerService.create(catOwner1, Adaptation.AnimalType.CAT, 1L)).thenReturn(catOwner1);
        mockMvc.perform(post("/catOwners")
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
        verify(catOwnerService, times(1)).create(catOwner1, Adaptation.AnimalType.CAT, 1L);
    }

    @Test
    void shouldCreateCatOwnerFromUserAndReturn() throws Exception {
        when(catOwnerService.create(1L, Adaptation.AnimalType.CAT, 1L)).thenReturn(catOwner1);
        mockMvc.perform(post("/catOwners/user")
                        .param("id", "1")
                        .param("animalId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("telegramId").value(1L))
                .andExpect(jsonPath("firstName").value("fname"))
                .andExpect(jsonPath("lastName").value("lname"))
                .andExpect(jsonPath("phoneNumber").value("123"));
        verify(catOwnerService, times(1)).create(1L, Adaptation.AnimalType.CAT, 1L);
    }

    @Test
    void shouldReturnAllCatOwners() throws Exception {
        when(catOwnerService.getAll()).thenReturn(catOwnerList);
        mockMvc.perform(get("/catOwners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].telegramId").value(1L))
                .andExpect(jsonPath("$.[0].firstName").value("fname"))
                .andExpect(jsonPath("$.[0].lastName").value("lname"))
                .andExpect(jsonPath("$.[0].phoneNumber").value("123"))
                .andExpect(jsonPath("$.[1].telegramId").value(2L))
                .andExpect(jsonPath("$.[1].firstName").value("fname"))
                .andExpect(jsonPath("$.[1].lastName").value("lname"))
                .andExpect(jsonPath("$.[1].phoneNumber").value("321"));
        verify(catOwnerService, times(1)).getAll();
    }

    @Test
    void shouldReturnCatOwnerById() throws Exception {
        when(catOwnerService.getById(catOwner1.getTelegramId())).thenReturn(catOwner1);
        mockMvc.perform(get("/catOwners/id")
                        .param("catOwnerId", String.valueOf(catOwner1.getTelegramId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("telegramId").value(1L))
                .andExpect(jsonPath("firstName").value("fname"))
                .andExpect(jsonPath("lastName").value("lname"));
        verify(catOwnerService, times(1)).getById(catOwner1.getTelegramId());
    }

    @Test
    void shouldUpdateAndReturnCatOwner() throws Exception {
        when(catOwnerService.update(any(CatOwner.class))).thenReturn(catOwner1);
        mockMvc.perform(put("/catOwners")
                        .param("telegramId", "1")
                        .param("firstName", "fname")
                        .param("lastName", "lname")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("telegramId").value(1L))
                .andExpect(jsonPath("firstName").value("fname"))
                .andExpect(jsonPath("lastName").value("lname"));
        verify(catOwnerService, times(1)).update(any(CatOwner.class));
    }

    @Test
    void shouldReturnMessageWhenCatOwnerDeleted() throws Exception {
        doNothing().when(catOwnerService).deleteById(1L);
        mockMvc.perform(delete("/catOwners/id")
                        .param("catOwnerId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cat owner successfully removed."));
        verify(catOwnerService, times(1)).deleteById(1L);
    }
}