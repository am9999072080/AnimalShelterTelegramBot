package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.Cat;
import Telegram.Bot.AnimalShelter.entity.CatShelter;

import java.util.List;

public interface CatShelterService {
    /**
     * Creating new cat shelter ib DB
     *
     * @param catShelter object cat shelter
     * @return cat shelter
     */
    CatShelter create(CatShelter catShelter);

    /**
     * Updating data of an existing cat shelter
     *
     * @param catShelter cat shelter
     * @return Updated cat shelter
     */
    CatShelter update(CatShelter catShelter);

    /**
     * Getting cat shelter by its ID
     *
     * @param id Cat shelter ID
     * @return shelter ID
     */
    CatShelter getShelterById(Long id);

    /**
     * Getting cat shelter by its name
     *
     * @param name Shelter's name
     * @return cat shelter
     */
    CatShelter getShelterByName(String name);

    /**
     * Getting list of cat shelters
     *
     * @return list of cat shelters
     */
    List<CatShelter> getShelter();

    /**
     * Getting list of animals by shelter ID
     *
     * @param id shelter ID
     * @return list of animals
     */
    List<Cat> getAnimal(Long id);

    /**
     * Deleting cat shelter by ID
     *
     * @param id shelter ID
     */
    String delete(Long id);


}
