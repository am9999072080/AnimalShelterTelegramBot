package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.DogOwner;

import java.util.List;

public interface DogOwnerService {
    /**
     * Create and save dog owner to DB
     *
     * @param dogOwner Dog owner to save
     * @param animalType Type of an animal
     * @param animalId Same animal ID
     * @return Dog owner
     */
    DogOwner create(DogOwner dogOwner, Adaptation.AnimalType animalType, Long animalId);

    /**
     * Create dog owner from user and save to DB
     *
     * @param id of a user
     * @param animalType Type of an animal
     * @param animalId Same animal ID
     * @return Dog owner
     */
    DogOwner create(Long id, Adaptation.AnimalType animalType, Long animalId);

    /**
     * Changing dog owner's data
     *
     * @param dogOwner Dog owner
     * @return updated dog owner
     */
    DogOwner update(DogOwner dogOwner);

    /**
     * Getting dog owner by ID
     *
     * @param id dog owner's ID
     * @return Dog owner
     */
    DogOwner getById(Long id);

    /**
     * Getting list of all dog owners
     *
     * @return list of all dog owners
     */
    List<DogOwner> getAll();

    /**
     * Deleting a dog owner from DB
     * @param dogOwner Existing dog owner
     */
    void delete(DogOwner dogOwner);

    /**
     * Deleting a dog owner by ID
     *
     * @param id of this dog you want to delete
     */
    void deleteById(Long id);
}
