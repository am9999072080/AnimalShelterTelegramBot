package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.User;
import Telegram.Bot.AnimalShelter.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    private final User user1 = new User(1L, "Name", "Name", "1");
    private final User user2 = new User(2L, "Name", "Nameov", "2");
    private final List<User> userList = List.of(user1, user2);


    @Test
    void shouldReturnListOfUsers() throws Exception {
        when(userService.getAll()).thenReturn(userList);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].telegramId").value(1L))
                .andExpect(jsonPath("$.[0].firstName").value("Name"))
                .andExpect(jsonPath("$.[0].lastName").value("Name"));
        verify(userService, times(1)).getAll();
    }

    @Test
    void shouldReturnUserFoundById() throws Exception {
        when(userService.getById(user1.getTelegramId())).thenReturn(user1);
        mockMvc.perform(get("/users/id")
                        .param("userId", String.valueOf(user1.getTelegramId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("telegramId").value(1L))
                .andExpect(jsonPath("firstName").value("Name"))
                .andExpect(jsonPath("lastName").value("Name"));
        verify(userService, times(1)).getById(user1.getTelegramId());
    }

    @Test
    void shouldUpdateAndReturnUser() throws Exception {
        when(userService.update(any(User.class))).thenReturn(user1);
        mockMvc.perform(put("/users")
                        .param("telegramId", "1")
                        .param("firstName", "Name")
                        .param("lastName", "Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("telegramId").value(1L))
                .andExpect(jsonPath("firstName").value("Name"))
                .andExpect(jsonPath("lastName").value("Name"));
        verify(userService, times(1)).update(any(User.class));
    }

    @Test
    void shouldReturnMessageWhenUserDeleted() throws Exception {
        doNothing().when(userService).deleteById(1L);
        mockMvc.perform(delete("/users/id")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User removed successfully"));
        verify(userService, times(1)).deleteById(1L);
    }
}