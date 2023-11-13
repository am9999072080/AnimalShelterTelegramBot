package Telegram.Bot.AnimalShelter.controller;

import Telegram.Bot.AnimalShelter.entity.Adaptation.AnimalType;
import Telegram.Bot.AnimalShelter.entity.Adaptation.Result;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateAdaptationRqDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate dateOfLastReport;
    private Result result;
    private Long ownerId;
    private AnimalType animalType;
    private Long animalId;

}
