package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.entity.DogShelter;
import Telegram.Bot.AnimalShelter.service.DogShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dogs/shelters")
@Tag(name = "Dog shelter", description = "CRUD-methods for DogShelter Class")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully done."),
        @ApiResponse(responseCode = "400", description = "There is a mistake in params."),
        @ApiResponse(responseCode = "404", description = "URL is incorrect or there is no such action."),
        @ApiResponse(responseCode = "500", description = "Mistake occur on server during the process.")
})
public class DogSheltersController {

    private final DogShelterService dogShelterService;

    @PostMapping("/")
    @Operation(summary = "Create new dog shelter")
    public DogShelter create(@RequestParam @Parameter(description = "Shelter name") String name,
                             @RequestParam @Parameter(description = "Location") String location,
                             @RequestParam @Parameter(description = "Working hours") String timetable,
                             @RequestParam @Parameter(description = "About shelter") String aboutMe,
                             @RequestParam @Parameter(description = "Security contacts") String security,
                             @RequestParam @Parameter(description = "Safety policy") String safetyAdvice
    ) {
        return dogShelterService.create(new DogShelter(name, location, timetable, aboutMe, security, safetyAdvice));
    }

    @PutMapping("/")
    @Operation(summary = "Update info about shelter")
    public DogShelter update(@RequestParam @Parameter(description = "Shelter ID") long id,
                             @RequestParam(required = false) @Parameter(description = "Shelter name") String name,
                             @RequestParam(required = false) @Parameter(description = "Location") String location,
                             @RequestParam(required = false) @Parameter(description = "Working hours") String timetable,
                             @RequestParam(required = false) @Parameter(description = "About shelter") String aboutMe,
                             @RequestParam(required = false) @Parameter(description = "Security contacts") String security,
                             @RequestParam(required = false) @Parameter(description = "Safety policy") String safetyAdvice) {
        return dogShelterService.update((new DogShelter(id, name, location, timetable, aboutMe, security, safetyAdvice)));
    }

    @GetMapping("/")
    @Operation(summary = "List of shelters")
    public List<DogShelter> getAll() {
        return dogShelterService.getShelter();
    }

    @GetMapping("/list{id}")
    @Operation(summary = "List of animals")
    public List<Dog> getAnimal(@PathVariable @Parameter(description = "Shelter ID") long id) {
        return dogShelterService.getAnimal(id);
    }

    @GetMapping("/id{id}")
    @Operation(summary = "Return shelter by ID")
    public DogShelter getShelterId(@PathVariable @Parameter(description = "Shelter ID") long id) {
        return (dogShelterService.getShelterById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Shelter removed")

    public String delete(@PathVariable @Parameter(description = "Shelter ID") long id) {
        return dogShelterService.delete(id);
    }

}