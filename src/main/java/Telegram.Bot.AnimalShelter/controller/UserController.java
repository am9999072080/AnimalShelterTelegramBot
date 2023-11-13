package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.User;
import Telegram.Bot.AnimalShelter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@Tag(name = "User", description = "CRUD-methods for User Class")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully done."),
        @ApiResponse(responseCode = "400", description = "There is a mistake in params."),
        @ApiResponse(responseCode = "404", description = "URL is incorrect or there is no such action."),
        @ApiResponse(responseCode = "500", description = "Mistake occur on server during the process.")
})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create user")
    public User create(@RequestParam @Parameter(description = "User telegram ID") Long telegramId,
                       @RequestParam @Parameter(description = "First Name") String firstName,
                       @RequestParam @Parameter(description = "Last Name") String lastName,
                       @RequestParam @Parameter(description = "Phone number") String phone) {
        return userService.create(new User(telegramId, firstName, lastName, phone));
    }

    @GetMapping()
    @Operation(summary = "Return list of all users")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("id")
    @Operation(summary = "Return user by ID")
    public User getById(@RequestParam @Parameter(description = "User ID") Long userId) {
        return userService.getById(userId);
    }

    @PutMapping
    @Operation(summary = "Update user")
    public User update(@RequestParam @Parameter(description = "User telegram ID") Long telegramId,
                       @RequestParam(required = false) @Parameter(description = "First Name") String firstName,
                       @RequestParam(required = false) @Parameter(description = "Last Name") String lastName,
                       @RequestParam(required = false) @Parameter(description = "Phone number") String phone) {
        return userService.update(new User(telegramId, firstName, lastName, phone));
    }

    @DeleteMapping("id")
    @Operation(summary = "Remove user")
    public String deleteById(@RequestParam @Parameter(description = "Uer ID") Long userId) {
        userService.deleteById(userId);
        return "User removed successfully";
    }
}
