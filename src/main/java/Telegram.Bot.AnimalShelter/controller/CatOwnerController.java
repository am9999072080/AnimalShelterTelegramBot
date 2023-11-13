package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.CatOwner;
import Telegram.Bot.AnimalShelter.service.CatOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("catOwners")
@Tag(name = "Cat owner", description = "CRUD-methods for CatOwner class")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully done."),
        @ApiResponse(responseCode = "400", description = "There is a mistake in params."),
        @ApiResponse(responseCode = "404", description = "URL is incorrect or there is no such action."),
        @ApiResponse(responseCode = "500", description = "Mistake occur on server during the process.")
})
public class CatOwnerController {
    private final CatOwnerService catOwnerService;

    public CatOwnerController(CatOwnerService catOwnerService) {
        this.catOwnerService = catOwnerService;
    }

    @PostMapping
    @Operation(summary = "Create owner")
    public CatOwner create(@RequestParam @Parameter(description = "Cat owner telegram ID") Long telegramId,
                           @RequestParam @Parameter(description = "First name") String firstName,
                           @RequestParam @Parameter(description = "Last name") String lastName,
                           @RequestParam @Parameter(description = "Phone number") String phone,
                           @RequestParam @Parameter(description = "Cat ID") Long animalId) {
        return catOwnerService.create(new CatOwner(telegramId, firstName, lastName, phone,
                null, null), Adaptation.AnimalType.CAT, animalId);
    }

    @PostMapping("/user")
    @Operation(summary = "Create owner from user")
    public CatOwner create(@RequestParam @Parameter(description = "User") Long id,
                           @RequestParam @Parameter(description = "Cat ID") Long animalId) {
        return catOwnerService.create(id, Adaptation.AnimalType.CAT, animalId);
    }

    @GetMapping()
    @Operation(summary = "Return list of all owners")
    public List<CatOwner> getAll() {
        return catOwnerService.getAll();
    }

    @GetMapping("id")
    @Operation(summary = "Return cat owner by ID")

    public CatOwner getById(@RequestParam @Parameter(description = "Owner ID") Long catOwnerId) {
        return catOwnerService.getById(catOwnerId);
    }

    @PutMapping
    @Operation(summary = "Update cat owner")
    public CatOwner update(@RequestParam @Parameter(description = "Owner telegram ID") Long telegramId,
                           @RequestParam(required = false) @Parameter(description = "First name") String firstName,
                           @RequestParam(required = false) @Parameter(description = "Last name") String lastName,
                           @RequestParam(required = false) @Parameter(description = "Phone number") String phone) {
        return catOwnerService.update(new CatOwner(telegramId, firstName, lastName, phone,
                null, null));
    }

    @DeleteMapping("id")
    @Operation(summary = "Removing cat owner by ID")

    public String deleteById(@RequestParam @Parameter(description = "Owner ID") Long catOwnerId) {
        catOwnerService.deleteById(catOwnerId);
        return "Cat owner successfully removed.";
    }
}
