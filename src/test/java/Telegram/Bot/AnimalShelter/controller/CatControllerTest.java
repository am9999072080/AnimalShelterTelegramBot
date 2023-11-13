package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Cat;
import Telegram.Bot.AnimalShelter.service.impl.CatServiceImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatController.class)
@ExtendWith(MockitoExtension.class)
class CatControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CatServiceImpl catService;

    static final Cat CAT_1 = new Cat(1L, "cat1", 1, true, true, 1L, 1L);
    static final Cat CAT_2 = new Cat(1L, "cat2", 1, false, false, 1L, 1L);
    static final Cat CAT_3 = new Cat(1L, "cat3", 1, true, true, 1L, 1L);

    @Test
    void shouldReturnCatsById() throws Exception {
        when(catService.getById(CAT_1.getId())).thenReturn(CAT_1);
        mockMvc.perform(get("/cats/id?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("cat1"))
                .andExpect(jsonPath("age").value(1))
                .andExpect(jsonPath("healthy").value(true))
                .andExpect((jsonPath("vaccinated").value(true)))
                .andExpect(jsonPath("ownerId").value(1L))
                .andExpect(jsonPath("shelterId").value(1L));
        verify(catService, times(1)).getById(CAT_1.getId());
    }

    @Test
    void shouldCreateNewCat() throws Exception {
        when(catService.create(any(Cat.class))).thenReturn(CAT_1);
        mockMvc.perform(post("/cats")
                        .param("name", "cat1")
                        .param("age", "1")
                        .param("isHealthy", "true")
                        .param("vaccinated", "true")
                        .param("shelterId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("cat1"))
                .andExpect(jsonPath("age").value(1))
                .andExpect(jsonPath("healthy").value(true))
                .andExpect((jsonPath("vaccinated").value(true)))
                .andExpect(jsonPath("shelterId").value(1L));
        verify(catService, times(1)).create(any(Cat.class));
    }

    @Test
    void shouldReturnAllCats() throws Exception {
        List<Cat> list = List.of(CAT_1, CAT_3);
        when(catService.getAll()).thenReturn(list);
        mockMvc.perform(get("/cats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("cat1"))
                .andExpect(jsonPath("$[0].age").value(1))
                .andExpect(jsonPath("$[0].healthy").value(true))
                .andExpect(jsonPath("$[0].vaccinated").value(true))
                .andExpect(jsonPath("$[0].ownerId").value(1L))
                .andExpect(jsonPath("$[0].shelterId").value(1L))
                .andExpect(jsonPath("$[1].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("cat3"))
                .andExpect(jsonPath("$[1].age").value(1))
                .andExpect(jsonPath("$[1].healthy").value(true))
                .andExpect(jsonPath("$[1].vaccinated").value(true))
                .andExpect(jsonPath("$[1].ownerId").value(1L))
                .andExpect(jsonPath("$[1].shelterId").value(1L));

    }

    @Test
    void shouldReturnOwnerById() throws Exception {
        when(catService.getAllByOwnerId(CAT_1.getOwnerId())).thenReturn(List.of(CAT_1));
        mockMvc.perform(get("/cats/ownerId?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].name").value("cat1"))
                .andExpect(jsonPath("$.[0].age").value(1))
                .andExpect(jsonPath("$.[0].healthy").value(true))
                .andExpect(jsonPath("$.[0].vaccinated").value(true))
                .andExpect(jsonPath("$.[0].ownerId").value(1L))
                .andExpect(jsonPath("$.[0].shelterId").value(1L));
        verify(catService, times(1)).getAllByOwnerId(CAT_1.getOwnerId());
    }

    @Test
    void shouldUpdateAndReturnCats() throws Exception {
        when(catService.update(any(Cat.class))).thenReturn(CAT_2);
        mockMvc.perform(put("/cats")
                        .param("id", "1")
                        .param("name", "cat2")
                        .param("age", "1")
                        .param("isHealthy", "false")
                        .param("vaccinated", "false")
                        .param("ownerId", "1")
                        .param("shelterId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("cat2"))
                .andExpect(jsonPath("age").value(1))
                .andExpect(jsonPath("healthy").value(false))
                .andExpect(jsonPath("vaccinated").value(false))
                .andExpect(jsonPath("ownerId").value(1L))
                .andExpect(jsonPath("shelterId").value(1L));
    }

    @Test
    void shouldDeleteCatById() throws Exception {
        mockMvc.perform(delete("/cats/id?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cat removed from list"));
        verify(catService, times(1)).delete(1L);
    }
}