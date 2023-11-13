package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateAdoptionRqDto {
    private LocalDate startDate;
    private Adaptation.Result result;
    private Long ownerId;
    private Adaptation.AnimalType animalType;
    private Long animalId;
}
