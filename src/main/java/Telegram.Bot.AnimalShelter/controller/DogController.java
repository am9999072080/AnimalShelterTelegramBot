package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dogs")
@Tag(name = "Dogs", description = "CRUD-method for Dog class")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully done."),
        @ApiResponse(responseCode = "400", description = "There is a mistake in params."),
        @ApiResponse(responseCode = "404", description = "URL is incorrect or there is no such action."),
        @ApiResponse(responseCode = "500", description = "Mistake occur on server during the process.")
})
public class DogController {

    private final DogService dogService;

    @GetMapping("/id")
    @Operation(summary = "Return dog by ID")
    public Dog getById(@RequestParam @Parameter(description = "Dog ID") Long id) {
        return dogService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Add a dog to the shelter")
    public Dog create(
            @RequestParam @Parameter(description = "Dog name") String name,
            @RequestParam @Parameter(description = "age") int age,
            @RequestParam @Parameter(description = "Healthy") boolean isHealthy,
            @RequestParam @Parameter(description = "Vaccinated") boolean vaccinated,
            @RequestParam @Parameter(description = "Shelter ID") Long shelterId) {
        return dogService.create(new Dog(name, age, isHealthy, vaccinated, shelterId));
    }

    @GetMapping()
    @Operation(summary = "Return all dogs")
    public List<Dog> getAll() {
        return dogService.getAll();
    }

    @GetMapping("/ownerId")
    @Operation(summary = "Return list of dogs by owner ID")
    public List<Dog> getOwnerById(@RequestParam @Parameter(description = "Owner ID") Long id) {
        return dogService.getAllByOwnerId(id);
    }


    @PutMapping
    @Operation(summary = "Update the dog's info")
    public Dog update(
            @RequestParam @Parameter(description = "Dog ID") Long id,
            @RequestParam(required = false) @Parameter(description = "Dog name") String name,
            @RequestParam(required = false) @Parameter(description = "age") Integer age,
            @RequestParam(required = false) @Parameter(description = "Healthy") Boolean isHealthy,
            @RequestParam(required = false) @Parameter(description = "Vaccinated") Boolean vaccinated,
            @RequestParam(required = false) @Parameter(description = "Shelter ID") Long shelterId,
            @RequestParam(required = false) @Parameter(description = "Owner ID") Long ownerId) {
        return dogService.update(new Dog(id, name, age, isHealthy, vaccinated, shelterId, ownerId));
    }

    @DeleteMapping("/id")
    @Operation(summary = "Remove dog from its list")
    public String deleteById(@RequestParam Long id) {
        dogService.delete(id);
        return "Dog removed from list";
    }
}
