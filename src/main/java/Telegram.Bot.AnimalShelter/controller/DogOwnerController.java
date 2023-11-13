package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.DogOwner;
import Telegram.Bot.AnimalShelter.service.DogOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dogOwners")
@Tag(name = "Dog owner", description = "CRUD-methods for DogOwner class")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully done."),
        @ApiResponse(responseCode = "400", description = "There is a mistake in params."),
        @ApiResponse(responseCode = "404", description = "URL is incorrect or there is no such action."),
        @ApiResponse(responseCode = "500", description = "Mistake occur on server during the process.")
})
public class DogOwnerController {
    private final DogOwnerService dogOwnerService;

    public DogOwnerController(DogOwnerService dogOwnerService) {
        this.dogOwnerService = dogOwnerService;
    }

    @PostMapping
    @Operation(summary = "Create owner")
    public DogOwner create(@RequestParam @Parameter(description = "Dog owner telegram ID") Long telegramId,
                           @RequestParam @Parameter(description = "First name") String firstName,
                           @RequestParam @Parameter(description = "Last name") String lastName,
                           @RequestParam @Parameter(description = "Phone number") String phone,
                           @RequestParam @Parameter(description = "Dog ID") Long animalId) {
        return dogOwnerService.create(new DogOwner(telegramId, firstName, lastName, phone,
                null, null), Adaptation.AnimalType.DOG, animalId);
    }

    @PostMapping("/user")
    @Operation(summary = "Create owner from user")
    public DogOwner create(@RequestParam @Parameter(description = "User") Long id,
                           @RequestParam @Parameter(description = "Dog ID") Long animalId) {
        return dogOwnerService.create(id, Adaptation.AnimalType.DOG, animalId);
    }

    @GetMapping()
    @Operation(summary = "Return list of all owners")
    public List<DogOwner> getAll() {
        return dogOwnerService.getAll();
    }

    @GetMapping("id")
    @Operation(summary = "Return dog owner by ID")
    public DogOwner getById(@RequestParam Long dogOwnerId) {
        return dogOwnerService.getById(dogOwnerId);
    }

    @PutMapping
    @Operation(summary = "Update dog owner")
    public DogOwner update(@RequestParam @Parameter(description = "Owner telegram ID") Long telegramId,
                           @RequestParam(required = false) @Parameter(description = "First name") String firstName,
                           @RequestParam(required = false) @Parameter(description = "Last name") String lastName,
                           @RequestParam(required = false) @Parameter(description = "Phone number") String phone) {
        return dogOwnerService.update(new DogOwner(telegramId, firstName, lastName, phone,
                null, null));
    }

    @DeleteMapping("id")
    @Operation(summary = "Removing dog owner by ID")

    public String deleteById(@RequestParam @Parameter(description = "Owner ID") Long dogOwnerId) {
        dogOwnerService.deleteById(dogOwnerId);
        return "Dog owner successfully removed.";
    }
}