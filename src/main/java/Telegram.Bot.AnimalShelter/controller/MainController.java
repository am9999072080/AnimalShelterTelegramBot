package Telegram.Bot.AnimalShelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Main")
public class MainController {
    @GetMapping("/")
    @Operation(summary = "Main page")
    public String mainPage() {
        return "Home";
    }
}