package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Report;
import Telegram.Bot.AnimalShelter.repository.ReportRepository;

import org.assertj.core.api.AssertionsForClassTypes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {
    @Mock
    private  ReportRepository reportRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    Long ADAPTATION_PERIOD_ID_1 = 1L;
    Long ADAPTATION_PERIOD_ID_2 = 1L;
    LocalDate LOCAL_DATE = LocalDate.now();
    LocalDate RECEIVE_DATA = LocalDate.now().plusDays(30);
    LocalDate DATE_OF_LAST_REPORT = LocalDate.now().minusDays(1);

    String PHOTO_ID = "photo_1";
    String FOOD_RATION ="50% - мясо и белковая пища, 20-30% отводится злаковым культурам, 20% – молочная продукция и не более 10% – овощи и фрукты";
    String ANIMAL_HEALTH = "встреча хозяина, прогулка";
    String ANIMAL_BEHAVIOR ="сигналы агрессии, страх, дружба";
    Report REPORT_1 = new Report(ADAPTATION_PERIOD_ID_1,RECEIVE_DATA, PHOTO_ID, FOOD_RATION, ANIMAL_HEALTH, ANIMAL_BEHAVIOR);
    Report REPORT_2 = new Report(ADAPTATION_PERIOD_ID_2,RECEIVE_DATA, PHOTO_ID, FOOD_RATION, ANIMAL_HEALTH, ANIMAL_BEHAVIOR);


    @Test
    void createReportTest() {

        when(reportRepository.save(REPORT_1)).thenReturn(REPORT_1);
        assertEquals(REPORT_1, reportService.create(REPORT_1));
    }


    @Test
    void updateReportTest() {
        when(reportService.create(REPORT_1)).thenReturn(REPORT_1);
        when(reportRepository.findById(any(Long.class))).thenReturn(Optional.of(REPORT_1));

        REPORT_2.setId(ADAPTATION_PERIOD_ID_1);
        assertEquals(REPORT_2, reportService.update(REPORT_2));
    }

    @Test
    void getByIdTest() {
        when(reportRepository.save(REPORT_1)).thenReturn(REPORT_1);
        assertEquals(REPORT_1, reportService.create(REPORT_1));

        when(reportRepository.findById(ADAPTATION_PERIOD_ID_1)).thenReturn(Optional.of(REPORT_1));
        assertEquals(REPORT_1, reportService.getById(ADAPTATION_PERIOD_ID_1));

    }

    @Test
    void getByIdExceptionTest() {
        when(reportRepository.save(REPORT_1)).thenReturn(REPORT_1);
        assertEquals(REPORT_1, reportService.create(REPORT_1));


        when(reportRepository.findById(ADAPTATION_PERIOD_ID_1)).thenThrow(new NotFoundException("NotFoundException"));
        assertThrows(NotFoundException.class, () -> reportService.getById(ADAPTATION_PERIOD_ID_1));

    }

    @Test
    void getByAdaptationPeriodIdTest() {
        ArrayList<Report> reportList = new ArrayList<>();
        reportList.add(REPORT_1);
        reportList.add(REPORT_2);

        when(reportRepository.findByAdaptationPeriodId(ADAPTATION_PERIOD_ID_1)).thenReturn(reportList);

        List<Report> reportList1 = reportService.getByAdaptationPeriodId(ADAPTATION_PERIOD_ID_1);

        AssertionsForClassTypes.assertThat(reportList1).isEqualTo(reportList);
        assertThat(reportList1).isEqualTo(reportList);
    }

    @Test
    void getByAdaptationPeriodIdExceptionTest() {
        ArrayList<Report> reportList = new ArrayList<>();
        reportList.add(REPORT_1);
        reportList.add(REPORT_2);

        when(reportRepository.findByAdaptationPeriodId(ADAPTATION_PERIOD_ID_1)).thenReturn(reportList);

        List<Report> reportList1 = reportService.getByAdaptationPeriodId(ADAPTATION_PERIOD_ID_1);

        AssertionsForClassTypes.assertThat(reportList1).isEqualTo(reportList);
        assertThat(reportList1).isEqualTo(reportList);

        when(reportRepository.findById(ADAPTATION_PERIOD_ID_1)).thenThrow(new NotFoundException("NotFoundException"));
        assertThrows(NotFoundException.class, () -> reportService.getById(ADAPTATION_PERIOD_ID_1));
    }

    @Test
    void getByDateAndAdaptationPeriodId() {


        when(reportRepository.findByReceiveDateAndAdaptationPeriodId(LOCAL_DATE, ADAPTATION_PERIOD_ID_1)).thenReturn(Optional.of(REPORT_1));
        Report newReport = reportService.getByDateAndAdaptationPeriodId(LOCAL_DATE, ADAPTATION_PERIOD_ID_1);
        assertEquals(REPORT_1, newReport);
        verify(reportRepository, times(1)).findByReceiveDateAndAdaptationPeriodId(LOCAL_DATE, ADAPTATION_PERIOD_ID_1);


    }


    @Test
    void getAllTest() {
        ArrayList<Report> reportList = new ArrayList<>();
        reportList.add(REPORT_1);
        reportList.add(REPORT_2);

        when(reportRepository.findAll()).thenReturn(reportList);
        Collection<Report> actual = reportService.getAll();

        assertThat(actual.size()).isEqualTo(reportList.size());
        assertThat(actual).isEqualTo(reportList);
    }
    @Test
    void getAllExceptionTest() {
        ArrayList<Report> reportList = new ArrayList<>();
        reportList.add(REPORT_1);
        reportList.add(REPORT_2);

        when(reportRepository.findAll()).thenThrow(new NotFoundException("NotFoundException"));
        assertThrows(NotFoundException.class, () -> reportService.getAll());
    }


    @Test
    void deleteTest() {
        when(reportService.create(REPORT_1)).thenReturn(REPORT_1);
        assertEquals(REPORT_1, reportService.create(REPORT_1));

        reportRepository.delete(REPORT_1);
        assertFalse(reportRepository.findAll().contains(REPORT_1));

    }

    @Test
    void deleteByIdTest() {
        when(reportService.create(REPORT_1)).thenReturn(REPORT_1);
        assertEquals(REPORT_1, reportService.create(REPORT_1));

        reportRepository.deleteById(ADAPTATION_PERIOD_ID_1);
        assertFalse(reportRepository.findAll().contains(REPORT_1));
    }
}