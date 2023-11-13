package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.entity.DogShelter;

import java.util.List;

public interface DogShelterService<D, D1> {
    /**
     * Creating new dog shelter ib DB
     *
     * @param dogShelter object dog shelter
     * @return dog shelter
     */
    DogShelter create(DogShelter dogShelter);
    /**
     * Updating data of an existing dog shelter
     *
     * @param dogShelter dog shelter
     * @return Updated dog shelter
     */
    DogShelter update(DogShelter dogShelter);

    /**
     * Getting dog shelter by its ID
     *
     * @param id Dog shelter ID
     * @return shelter ID
     */
    DogShelter getShelterById(Long id);

    /**
     * Getting dog shelter by its name
     *
     * @param name Shelter's name
     * @return dog shelter
     */
    DogShelter getShelterByName(String name);

    /**
     * Getting list of dog shelters
     *
     * @return list of dog shelters
     */
    List<DogShelter> getShelter();
    /**
     * Getting list of animals by shelter ID
     *
     * @param id shelter ID
     * @return list of animals
     */
    List<Dog> getAnimal(Long id);

    /**
     * Deleting dog shelter by ID
     *
     * @param id shelter ID
     */
    String delete(Long id);


}
