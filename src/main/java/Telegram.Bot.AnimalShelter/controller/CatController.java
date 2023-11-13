package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Cat;
import Telegram.Bot.AnimalShelter.service.CatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cats")
@Tag(name = "Cats", description = "CRUD-method for Cat class")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully done."),
        @ApiResponse(responseCode = "400", description = "There is a mistake in params."),
        @ApiResponse(responseCode = "404", description = "URL is incorrect or there is no such action."),
        @ApiResponse(responseCode = "500", description = "Mistake occur on server during the process.")
})
public class CatController {

    private final CatService catService;

    @GetMapping("/id")
    @Operation(summary = "Return cat by ID")
    public Cat getById(@RequestParam @Parameter(description = "Cat ID") Long id) {
        return catService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Add a cat to the shelter")
    public Cat create(
            @RequestParam @Parameter(description = "Cat name") String name,
            @RequestParam @Parameter(description = "age") int age,
            @RequestParam @Parameter(description = "Healthy") boolean isHealthy,
            @RequestParam @Parameter(description = "Vaccinated") boolean vaccinated,
            @RequestParam @Parameter(description = "Shelter ID") Long shelterId) {
        return catService.create(new Cat(name, age, isHealthy, vaccinated, shelterId));
    }

    @GetMapping()
    @Operation(summary = "Return all cats")
    public List<Cat> getAll() {
        return catService.getAll();
    }

    @GetMapping("/ownerId")
    @Operation(summary = "Return list of cats by owner ID")
    public List<Cat> getOwnerById(@RequestParam @Parameter(description = "Owner ID") Long id) {
        return catService.getAllByOwnerId(id);
    }


    @PutMapping
    @Operation(summary = "Update the cat's info")
    public Cat update(
            @RequestParam @Parameter(description = "Cat ID") Long id,
            @RequestParam(required = false) @Parameter(description = "Cat name") String name,
            @RequestParam(required = false) @Parameter(description = "age") Integer age,
            @RequestParam(required = false) @Parameter(description = "Healthy") Boolean isHealthy,
            @RequestParam(required = false) @Parameter(description = "Vaccinated") Boolean vaccinated,
            @RequestParam(required = false) @Parameter(description = "Shelter ID") Long shelterId,
            @RequestParam(required = false) @Parameter(description = "Owner ID") Long ownerId) {
        return catService.update(new Cat(id, name, age, isHealthy, vaccinated, shelterId, ownerId));
    }

    @DeleteMapping("/id")
    @Operation(summary = "Remove cat from its list")
    public String deleteById(@RequestParam Long id) {
        catService.delete(id);
        return "Cat removed from list";
    }

}