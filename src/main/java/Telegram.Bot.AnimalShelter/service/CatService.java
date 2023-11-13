package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.Cat;
import org.webjars.NotFoundException;

import java.util.List;

public interface CatService {
    /**
     * Create a new cat in DB
     *
     * @param cat A cat object
     * @return Cat with ID
     */
    Cat create(Cat cat);

    /**
     * Updating data of a cat
     *
     * @param cat Cat object with needed data
     * @return Updated cat
     */
    Cat update(Cat cat);

    /**
     * Getting a cat by its ID
     *
     * @param id Cat's ID
     * @return Cat
     * @throws NotFoundException If there is no cat with such ID
     */
    Cat getById(Long id);

    /**
     * Getting a list of cats by owner ID
     *
     * @param id Owner's ID
     * @return list of cats
     * @throws NotFoundException if the owner does not have cats
     */
    List<Cat> getAllByOwnerId(Long id);

    /**
     * Getting all cats
     *
     * @return list of all cats
     * @throws NotFoundException if there are no cats
     */
    List<Cat> getAll();

    /**
     * Deleting a cat by its ID
     * @param id Cat's ID
     */
    void delete(Long id);
}
