package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdaptationController.class)
@ExtendWith(MockitoExtension.class)
class AdaptationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AdaptationService adaptationService;

    private final Adaptation adaptation1 = new Adaptation(new ArrayList<>(), 1L, 1L, 1L, Adaptation.AnimalType.CAT, LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now().minusDays(1), Adaptation.Result.IN_PROGRESS);
    private final Adaptation adaptation2 = new Adaptation(new ArrayList<>(), 2L, 2L, 2L, Adaptation.AnimalType.CAT, LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now().minusDays(1), Adaptation.Result.IN_PROGRESS);

    private final List<Adaptation> AdaptationList = List.of(adaptation1, adaptation2);

    @Test
    void shouldCreateAndReturnAdaptation() throws Exception {
        when(adaptationService.create(any(Adaptation.class))).thenReturn(adaptation1);

        JSONObject createAdoptionRq = new JSONObject();
        createAdoptionRq.put("startDate", LocalDate.now());
        createAdoptionRq.put("result", Adaptation.Result.IN_PROGRESS);
        createAdoptionRq.put("ownerId", 1L);
        createAdoptionRq.put("animalType", Adaptation.AnimalType.CAT);
        createAdoptionRq.put("animalId", 1L);

        mockMvc.perform(post("/adaptation-periods")
                        .content(createAdoptionRq.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("reports").value(new ArrayList<>()))
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("ownerId").value(1L))
                .andExpect(jsonPath("animalId").value(1L))
                .andExpect(jsonPath("animalType").value(String.valueOf(Adaptation.AnimalType.CAT)))
                .andExpect(jsonPath("startDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("endDate").value(String.valueOf(LocalDate.now().plusDays(30))))
                .andExpect(jsonPath("dateOfLastReport").value(String.valueOf(LocalDate.now().minusDays(1))))
                .andExpect(jsonPath("result").value(String.valueOf(Adaptation.Result.IN_PROGRESS)));

        verify(adaptationService, times(1)).create(any(Adaptation.class));
    }

    @Test
    void shouldReturnListOfAllAdaptations() throws Exception {
        when(adaptationService.getAll()).thenReturn(AdaptationList);
        mockMvc.perform(get("/adaptation-periods"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].startDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.[0].endDate").value(String.valueOf(LocalDate.now().plusDays(30))))
                .andExpect(jsonPath("$.[0].dateOfLastReport").value(String.valueOf(LocalDate.now().minusDays(1))))
                .andExpect(jsonPath("$.[0].reports").value(new ArrayList<>()))
                .andExpect(jsonPath("$.[0].result").value(String.valueOf(Adaptation.Result.IN_PROGRESS)))
                .andExpect(jsonPath("$.[0].ownerId").value(1L))
                .andExpect(jsonPath("$.[0].animalType").value(String.valueOf(Adaptation.AnimalType.CAT)))
                .andExpect(jsonPath("$.[0].animalId").value(1L))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[1].startDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.[1].endDate").value(String.valueOf(LocalDate.now().plusDays(30))))
                .andExpect(jsonPath("$.[1].dateOfLastReport").value(String.valueOf(LocalDate.now().minusDays(1))))
                .andExpect(jsonPath("$.[1].reports").value(new ArrayList<>()))
                .andExpect(jsonPath("$.[1].result").value(String.valueOf(Adaptation.Result.IN_PROGRESS)))
                .andExpect(jsonPath("$.[1].ownerId").value(2L))
                .andExpect(jsonPath("$.[1].animalType").value(String.valueOf(Adaptation.AnimalType.CAT)))
                .andExpect(jsonPath("$.[1].animalId").value(2L));
        verify(adaptationService, times(1)).getAll();
    }

    @Test
    void shouldReturnListOfAllAdaptationsByOwnerId() throws Exception {
        when(adaptationService.getAllByOwnerId(1L)).thenReturn(AdaptationList);
        mockMvc.perform(get("/adaptation-periods/owner")
                        .param("ownerId", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].startDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.[0].endDate").value(String.valueOf(LocalDate.now().plusDays(30))))
                .andExpect(jsonPath("$.[0].dateOfLastReport").value(String.valueOf(LocalDate.now().minusDays(1))))
                .andExpect(jsonPath("$.[0].reports").value(new ArrayList<>()))
                .andExpect(jsonPath("$.[0].result").value(String.valueOf(Adaptation.Result.IN_PROGRESS)))
                .andExpect(jsonPath("$.[0].ownerId").value(1L))
                .andExpect(jsonPath("$.[0].animalType").value(String.valueOf(Adaptation.AnimalType.CAT)))
                .andExpect(jsonPath("$.[0].animalId").value(1L))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[1].startDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.[1].endDate").value(String.valueOf(LocalDate.now().plusDays(30))))
                .andExpect(jsonPath("$.[1].dateOfLastReport").value(String.valueOf(LocalDate.now().minusDays(1))))
                .andExpect(jsonPath("$.[1].reports").value(new ArrayList<>()))
                .andExpect(jsonPath("$.[1].result").value(String.valueOf(Adaptation.Result.IN_PROGRESS)))
                .andExpect(jsonPath("$.[1].ownerId").value(2L))
                .andExpect(jsonPath("$.[1].animalType").value(String.valueOf(Adaptation.AnimalType.CAT)))
                .andExpect(jsonPath("$.[1].animalId").value(2L));
        verify(adaptationService, times(1)).getAllByOwnerId(1L);
    }

    @Test
    void shouldReturnAdaptationsById() throws Exception {
        when(adaptationService.getById(1L)).thenReturn(adaptation1);
        mockMvc.perform(get("/adaptation-periods/id")
                        .param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("startDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("endDate").value(String.valueOf(LocalDate.now().plusDays(30))))
                .andExpect(jsonPath("dateOfLastReport").value(String.valueOf(LocalDate.now().minusDays(1))))
                .andExpect(jsonPath("reports").value(new ArrayList<>()))
                .andExpect(jsonPath("result").value(String.valueOf(Adaptation.Result.IN_PROGRESS)))
                .andExpect(jsonPath("ownerId").value(1L))
                .andExpect(jsonPath("animalType").value(String.valueOf(Adaptation.AnimalType.CAT)))
                .andExpect(jsonPath("animalId").value(1L));
        verify(adaptationService, times(1)).getById(1L);
    }

    @Test
    void shouldUpdateAndReturnAdaptation() throws Exception {
        when(adaptationService.update(any(Adaptation.class))).thenReturn(adaptation1);
        JSONObject updateAdaptationRqDto = new JSONObject();
        updateAdaptationRqDto.put("id", 1L);
        updateAdaptationRqDto.put("startDate", String.valueOf(LocalDate.now()));
        updateAdaptationRqDto.put("result", String.valueOf(Adaptation.Result.IN_PROGRESS));
        updateAdaptationRqDto.put("ownerId", String.valueOf(1L));
        updateAdaptationRqDto.put("animalType", String.valueOf(Adaptation.AnimalType.CAT));
        updateAdaptationRqDto.put("animalId", String.valueOf(1L));

        mockMvc.perform(put("/adaptation-periods")
                        .content(updateAdaptationRqDto.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("reports").value(new ArrayList<>()))
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("ownerId").value(1L))
                .andExpect(jsonPath("animalId").value(1L))
                .andExpect(jsonPath("animalType").value(String.valueOf(Adaptation.AnimalType.CAT)))
                .andExpect(jsonPath("startDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("endDate").value(String.valueOf(LocalDate.now().plusDays(30))))
                .andExpect(jsonPath("dateOfLastReport").value(String.valueOf(LocalDate.now().minusDays(1))))
                .andExpect(jsonPath("result").value(String.valueOf(Adaptation.Result.IN_PROGRESS)));
        verify(adaptationService, times(1)).update(any(Adaptation.class))

        ;
    }

    @Test
    void shouldReturnMessageWhenAdaptationDeleted() throws Exception {
        doNothing().when(adaptationService).deleteById(1L);
        mockMvc.perform(delete("/adaptation-periods/id")
                        .param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(content().string("Adaptation removed successfully"));
        verify(adaptationService, times(1)).deleteById(1L);
    }
}