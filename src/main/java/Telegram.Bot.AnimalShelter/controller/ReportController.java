package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Report;
import Telegram.Bot.AnimalShelter.listener.TelegramBotUpdatesListener;
import Telegram.Bot.AnimalShelter.listener.TelegramBotUpdatesListenerFunctions;
import Telegram.Bot.AnimalShelter.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("reports")
@Tag(name = "Report", description = "CRUD-methods for Report Class")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully done."),
        @ApiResponse(responseCode = "400", description = "There is a mistake in params."),
        @ApiResponse(responseCode = "404", description = "URL is incorrect or there is no such action."),
        @ApiResponse(responseCode = "500", description = "Mistake occur on server during the process.")
})
public class ReportController {

    private final ReportService reportService;

    private final TelegramBotUpdatesListenerFunctions telegramBotUpdatesListenerFunctions;

    public ReportController(ReportService reportService, TelegramBotUpdatesListenerFunctions telegramBotUpdatesListenerFunctions) {
        this.reportService = reportService;
        this.telegramBotUpdatesListenerFunctions = telegramBotUpdatesListenerFunctions;
    }

    @PostMapping
    @Operation(summary = "Create report")
    public Report create(@RequestParam @Parameter(description = "Photo ID") String photoId,
                         @RequestParam @Parameter(description = "Ration") String foodRation,
                         @RequestParam @Parameter(description = "Overall health") String animalHealth,
                         @RequestParam @Parameter(description = "Behaviour changes") String animalBehavior,
                         @RequestParam @Parameter(description = "Adaptation ID") Long adaptationPeriodId) {
        return reportService.create(new Report(adaptationPeriodId, LocalDate.now(), photoId, foodRation, animalHealth, animalBehavior));
    }


    @GetMapping()
    @Operation(summary = "Return list of all reports")
    public List<Report> getAll() {
        return reportService.getAll();
    }

    @GetMapping("adaptation-id")
    @Operation(summary = "Return all reports by adaptation ID")
    public List<Report> getByAdaptationPeriodId(@RequestParam @Parameter(description = "Adaptation ID") Long id) {
        return reportService.getByAdaptationPeriodId(id);
    }

    @GetMapping("date-and-adaptation")
    @Operation(summary = "Return by date and adaptation ID")
    public Report getByDateAndAdaptationPeriodId(
            @RequestParam @Parameter(description = "Receive date") String date,
            @RequestParam @Parameter(description = "Adaptation ID") Long id) {
        return reportService.getByDateAndAdaptationPeriodId(LocalDate.parse(date), id);
    }

    @GetMapping("id")
    @Operation(summary = "Return report by ID")
    public Report getById(@RequestParam @Parameter(description = "Adaptation ID") Long reportId) {
        return reportService.getById(reportId);
    }

    @PutMapping
    @Operation(summary = "Update report")
    public Report update(@RequestParam @Parameter(description = "Report ID") Long id,
                         @RequestParam(required = false) @Parameter(description = "Photo ID") String photoId,
                         @RequestParam(required = false) @Parameter(description = "Ration") String foodRation,
                         @RequestParam(required = false) @Parameter(description = "Overall health") String animalHealth,
                         @RequestParam(required = false) @Parameter(description = "Behaviour changes") String animalBehavior,
                         @RequestParam(required = false) @Parameter(description = "Receive date") LocalDate receiveDate,
                         @RequestParam(required = false) @Parameter(description = "Adaptation ID") Long adaptationPeriodId) {
        return reportService.update(new Report(id, adaptationPeriodId, receiveDate, photoId, foodRation, animalHealth, animalBehavior));
    }

    @DeleteMapping("id")
    @Operation(summary = "Remove report by ID")
    public String deleteById(@RequestParam Long id) {
        reportService.deleteById(id);
        return "Report removed successfully";
    }

    @GetMapping("report-photo")
    @Operation(summary = "Send photo to volunteer")
    public String getReportPhoto(@RequestParam @Parameter(description = "Report ID") Long reportId,
                                 @RequestParam @Parameter(description = "Volunteer ID") Long volunteerId) {
        telegramBotUpdatesListenerFunctions.sendReportPhotoToVolunteer(reportId, volunteerId);
        return "Photo sent successfully";
    }
}

