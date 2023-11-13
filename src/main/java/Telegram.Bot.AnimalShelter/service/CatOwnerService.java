package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.CatOwner;

import java.util.List;

public interface CatOwnerService {
    /**
     * Create and save cat owner to DB
     *
     * @param catOwner Cat owner to save
     * @param animalType Type of an animal
     * @param animalId Same animal ID
     * @return Cat owner
     */
    CatOwner create(CatOwner catOwner, Adaptation.AnimalType animalType, Long animalId);

    /**
     * Create cat owner from user and save to DB
     *
     * @param id of a user
     * @param animalType Type of an animal
     * @param animalId Same animal ID
     * @return Cat owner
     */
    CatOwner create(Long id, Adaptation.AnimalType animalType, Long animalId);

    /**
     * Changing cat owner's data
     *
     * @param catOwner Cat owner
     * @return updated cat owner
     */
    CatOwner update(CatOwner catOwner);

    /**
     * Getting cat owner by ID
     *
     * @param id cat owner's ID
     * @return Cat owner
     */
    CatOwner getById(Long id);

    /**
     * Getting list of all cat owners
     * @return list of all cat owners
     */
    List<CatOwner> getAll();

    /**
     * Deleting a cat owner from DB
     * @param catOwner Existing cat owner
     */
    void delete(CatOwner catOwner);

    /**
     * Deleting a cat owner by ID
     *
     * @param id of this cat you want to delete
     */
    void deleteById(Long id);
}
