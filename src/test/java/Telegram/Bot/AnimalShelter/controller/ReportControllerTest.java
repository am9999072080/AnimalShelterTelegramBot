package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Report;
import Telegram.Bot.AnimalShelter.listener.TelegramBotUpdatesListener;
import Telegram.Bot.AnimalShelter.listener.TelegramBotUpdatesListenerFunctions;
import Telegram.Bot.AnimalShelter.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TelegramBotUpdatesListenerFunctions telegramBotUpdatesListenerFunctions;

    @MockBean
    ReportService reportService;

    private final Report report1 = new Report(1L, 1L, LocalDate.now(), "photoid", "ration", "health", "behavior");
    private final Report report2 = new Report(2L, 2L, LocalDate.now(), "photoid1", "ration1", "health1", "behavior1");

    private final List<Report> reportList = List.of(report1, report2);

    @Test
    void shouldCreateAndReturnReport() throws Exception {
        when(reportService.create(any(Report.class))).thenReturn(report1);
        mockMvc.perform(post("/reports")
                        .param("photoId", "photoid")
                        .param("foodRation", "ration")
                        .param("animalHealth", "health")
                        .param("animalBehavior", "behavior")
                        .param("adaptationPeriodId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("adaptationPeriodId").value(1L))
                .andExpect(jsonPath("photoId").value("photoid"))
                .andExpect(jsonPath("foodRation").value("ration"))
                .andExpect(jsonPath("animalHealth").value("health"))
                .andExpect(jsonPath("animalBehavior").value("behavior"));
        verify(reportService, times(1)).create(any(Report.class));
    }

    @Test
    void shouldReturnListOfAllReports() throws Exception {
        when(reportService.getAll()).thenReturn(reportList);
        mockMvc.perform(get("/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].photoId").value("photoid"))
                .andExpect(jsonPath("$.[0].foodRation").value("ration"))
                .andExpect(jsonPath("$.[0].animalHealth").value("health"))
                .andExpect(jsonPath("$.[0].animalBehavior").value("behavior"))
                .andExpect(jsonPath("$.[0].adaptationPeriodId").value(1L))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[1].photoId").value("photoid1"))
                .andExpect(jsonPath("$.[1].foodRation").value("ration1"))
                .andExpect(jsonPath("$.[1].animalHealth").value("health1"))
                .andExpect(jsonPath("$.[1].animalBehavior").value("behavior1"))
                .andExpect(jsonPath("$.[1].adaptationPeriodId").value(2L));
        verify(reportService, times(1)).getAll();
    }

    @Test
    void shouldReturnListOfReportsByAdaptationPeriodId() throws Exception {
        when(reportService.getByAdaptationPeriodId(1L)).thenReturn(reportList);
        mockMvc.perform(get("/reports/adaptation-id")
                        .param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].photoId").value("photoid"))
                .andExpect(jsonPath("$.[0].foodRation").value("ration"))
                .andExpect(jsonPath("$.[0].animalHealth").value("health"))
                .andExpect(jsonPath("$.[0].animalBehavior").value("behavior"))
                .andExpect(jsonPath("$.[0].adaptationPeriodId").value(1L))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[1].photoId").value("photoid1"))
                .andExpect(jsonPath("$.[1].foodRation").value("ration1"))
                .andExpect(jsonPath("$.[1].animalHealth").value("health1"))
                .andExpect(jsonPath("$.[1].animalBehavior").value("behavior1"))
                .andExpect(jsonPath("$.[1].adaptationPeriodId").value(2L));
        verify(reportService, times(1)).getByAdaptationPeriodId(1L);
    }

    @Test
    void shouldReturnReportByAdaptationPeriodIdAndDate() throws Exception {
        when(reportService.getByDateAndAdaptationPeriodId(LocalDate.now(), 1L)).thenReturn(report1);
        mockMvc.perform(get("/reports/date-and-adaptation")
                        .param("date", String.valueOf(LocalDate.now()))
                        .param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("adaptationPeriodId").value(1L))
                .andExpect(jsonPath("photoId").value("photoid"))
                .andExpect(jsonPath("foodRation").value("ration"))
                .andExpect(jsonPath("animalHealth").value("health"))
                .andExpect(jsonPath("animalBehavior").value("behavior"));
        verify(reportService, times(1)).getByDateAndAdaptationPeriodId(LocalDate.now(), 1L);
    }

    @Test
    void shouldReturnReportFoundById() throws Exception {
        when(reportService.getById(report1.getId())).thenReturn(report1);
        mockMvc.perform(get("/reports/id")
                        .param("reportId", String.valueOf(report1.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("adaptationPeriodId").value(1L))
                .andExpect(jsonPath("photoId").value("photoid"))
                .andExpect(jsonPath("foodRation").value("ration"))
                .andExpect(jsonPath("animalHealth").value("health"))
                .andExpect(jsonPath("animalBehavior").value("behavior"));
        verify(reportService, times(1)).getById(report1.getId());
    }

    @Test
    void shouldUpdateAndReturnReport() throws Exception {
        when(reportService.update(any(Report.class))).thenReturn(report1);
        mockMvc.perform(put("/reports")
                        .param("id", "1")
                        .param("photoId", "photoid")
                        .param("foodRation", "ration")
                        .param("animalHealth", "health")
                        .param("animalBehavior", "behavior")
                        .param("adaptationPeriodId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("adaptationPeriodId").value(1L))
                .andExpect(jsonPath("photoId").value("photoid"))
                .andExpect(jsonPath("foodRation").value("ration"))
                .andExpect(jsonPath("animalHealth").value("health"))
                .andExpect(jsonPath("animalBehavior").value("behavior"));
        verify(reportService, times(1)).update(any(Report.class));
    }

    @Test
    void shouldReturnMessageWhenReportDeleted() throws Exception {
        doNothing().when(reportService).deleteById(1L);
        mockMvc.perform(delete("/reports/id")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Report removed successfully"));
        verify(reportService, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnMessageWhenReportPhotoWasSend() throws Exception {
        doNothing().when(telegramBotUpdatesListenerFunctions).sendReportPhotoToVolunteer(1L, 1L);
        mockMvc.perform(get("/reports/report-photo")
                        .param("reportId", "1")
                        .param("volunteerId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Photo sent successfully"));
        verify(telegramBotUpdatesListenerFunctions, times(1)).sendReportPhotoToVolunteer(1L, 1L);
    }
}