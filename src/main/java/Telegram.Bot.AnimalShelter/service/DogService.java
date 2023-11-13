package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.Dog;
import org.webjars.NotFoundException;

import java.util.List;

public interface DogService {

    /**
     * Create a new dog in DB
     *
     * @param dog A dog object
     * @return Dog with ID
     */
    Dog create(Dog dog);

    /**
     * Updating data of a dog
     *
     * @param dog Dog object with needed data
     * @return Updated dog
     */
    Dog update(Dog dog);

    /**
     * Getting a dog by its ID
     *
     * @param id Dog's ID
     * @return Dog
     * @throws NotFoundException If there is no dog with such ID
     */
    Dog getById(Long id);

    /**
     * Getting a list of dogs by owner ID
     *
     * @param id Owner's ID
     * @return list of dogs
     * @throws NotFoundException if the owner does not have dogs
     */
    List<Dog> getAllByOwnerId(Long id);

    /**
     * Getting all dogs
     *
     * @return list of all dogs
     * @throws NotFoundException if there are no dogs
     */
    List<Dog> getAll();

    /**
     * Deleting a dog by its ID
     * @param id Dog's ID
     */
    void delete(Long id);
}
