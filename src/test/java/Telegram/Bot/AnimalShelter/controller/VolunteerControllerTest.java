package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Volunteer;
import Telegram.Bot.AnimalShelter.service.VolunteerService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VolunteerController.class)
@ExtendWith(MockitoExtension.class)
class VolunteerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    VolunteerService volunteerService;
    @MockBean
    TelegramBot telegramBot;
    private final Volunteer volunteer1 = new Volunteer(1L, "Name", "Nameov");
    private final Volunteer volunteer2 = new Volunteer(2L, "Name", "Nameov");
    private final List<Volunteer> volunteerList = List.of(volunteer1, volunteer2);

    @Test
    void shouldCreateAndReturnVolunteer() throws Exception {
        when(volunteerService.create(any(Volunteer.class))).thenReturn(volunteer1);
        mockMvc.perform(post("/volunteers").param("telegramId", "1").param("firstName", "Name").param("lastName", "Nameov").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("telegramId").value(1L)).andExpect(jsonPath("firstName").value("Name")).andExpect(jsonPath("lastName").value("Nameov"));
        verify(volunteerService, times(1)).create(any(Volunteer.class));
    }

    @Test
    void shouldReturnListOfVolunteers() throws Exception {
        when(volunteerService.getAll()).thenReturn(volunteerList);
        mockMvc.perform(get("/volunteers")).andExpect(status().isOk()).andExpect(jsonPath("$.[0].telegramId").value(1L)).andExpect(jsonPath("$.[0].firstName").value("Name")).andExpect(jsonPath("$.[0].lastName").value("Nameov")).andExpect(jsonPath("$.[1].telegramId").value(2L)).andExpect(jsonPath("$.[1].firstName").value("Name")).andExpect(jsonPath("$.[1].lastName").value("Nameov"));
        verify(volunteerService, times(1)).getAll();
    }

    @Test
    void shouldReturnVolunteerFoundById() throws Exception {
        when(volunteerService.getById(volunteer1.getTelegramId())).thenReturn(volunteer1);
        mockMvc.perform(get("/volunteers/id").param("volunteerId", String.valueOf(volunteer1.getTelegramId())).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("telegramId").value(1L)).andExpect(jsonPath("firstName").value("Name")).andExpect(jsonPath("lastName").value("Nameov"));
        verify(volunteerService, times(1)).getById(volunteer1.getTelegramId());
    }

    @Test
    void shouldUpdateAndReturnVolunteer() throws Exception {
        when(volunteerService.update(any(Volunteer.class))).thenReturn(volunteer1);
        mockMvc.perform(put("/volunteers").param("telegramId", "1").param("firstName", "Name").param("lastName", "Nameov").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("telegramId").value(1L)).andExpect(jsonPath("firstName").value("Name")).andExpect(jsonPath("lastName").value("Nameov"));
        verify(volunteerService, times(1)).update(any(Volunteer.class));
    }

    @Test
    void shouldReturnMessageWhenVolunteerDeleted() throws Exception {
        doNothing().when(volunteerService).deleteById(1L);
        mockMvc.perform(delete("/volunteers/id").param("volunteerId", "1")).andExpect(status().isOk()).andExpect(content().string("Volunteer removed successfully"));
        verify(volunteerService, times(1)).deleteById(1L);
    }

    @Test
    void shouldSendMessageToVolunteers() throws Exception {
        mockMvc.perform(post("/volunteers/warning-message").param("ownerId", volunteer1.getTelegramId().toString())).andExpect(status().isOk()).andExpect(content().string("Message sent successfully"));
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }
}