package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("adaptation-periods")
@Tag(name = "Adaptation", description = "CRUD-methods for Adaptation Class")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully done."),
        @ApiResponse(responseCode = "400", description = "There is a mistake in params."),
        @ApiResponse(responseCode = "404", description = "URL is incorrect or there is no such action."),
        @ApiResponse(responseCode = "500", description = "Mistake occur on server during the process.")
})
public class AdaptationController {

    private final AdaptationService adaptationService;

    public AdaptationController(AdaptationService adaptationService) {
        this.adaptationService = adaptationService;
    }


    @PostMapping
    @Operation(summary = "Create adaptation")
    public Adaptation create(@RequestBody CreateAdoptionRqDto dto) {
        return adaptationService.create(new Adaptation(dto.getOwnerId(), dto.getAnimalId(), dto.getAnimalType(),
                dto.getStartDate(), dto.getStartDate().plusDays(30), dto.getStartDate().minusDays(1),
                new ArrayList<>(), dto.getResult()));
    }

    @GetMapping()
    @Operation(summary = "Return list with all adaptations")
    public List<Adaptation> getAll() {
        return adaptationService.getAll();
    }

    @GetMapping("owner")
    @Operation(summary = "Return list with all adaptations by owner ID")
    public List<Adaptation> getAllByOwnerId(@RequestParam @Parameter(description = "Owner ID") Long ownerId) {
        return adaptationService.getAllByOwnerId(ownerId);
    }

    @GetMapping("id")
    @Operation(summary = "Return adaptation by ID")
    public Adaptation getById(@RequestParam Long id) {
        return adaptationService.getById(id);
    }

    @PutMapping
    @Operation(summary = "Update adaptation info")
    public Adaptation update(@RequestBody UpdateAdaptationRqDto dto) {
        return adaptationService.update(new Adaptation(new ArrayList<>(), dto.getId(), dto.getOwnerId(), dto.getAnimalId(), dto.getAnimalType(), dto.getStartDate(), dto.getStartDate().plusDays(30),
                dto.getStartDate().minusDays(1), dto.getResult()));
    }

    @DeleteMapping("id")
    @Operation(summary = "Remove adaptation by ID")
    public String deleteById(@RequestParam @Parameter(description = "Adaptation ID") Long id) {
        adaptationService.deleteById(id);
        return "Adaptation removed successfully";
    }
}
