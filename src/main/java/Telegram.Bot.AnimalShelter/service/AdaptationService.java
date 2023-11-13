package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.repository.AdaptationRepository;
import Telegram.Bot.AnimalShelter.service.impl.AdaptationServiceImpl;
import org.webjars.NotFoundException;

import java.util.List;

public interface AdaptationService {

    /**
     * Saving an adaptation to DB<br>
     *{@link AdaptationRepository#save(Object)} repository method is used
     *
     * @param adaptation Adaptation period to save
     * @return Created adaptation period
     */
    Adaptation create(Adaptation adaptation);

    /**
     * Saving an adaptation to DB when owner created<br>
     * {@link AdaptationRepository#save(Object)} repository method is used
     *
     * @param adaptation Adaptation period to save
     * @param animalType Type of an animal
     * @return Created adaptation period
     */
    Adaptation create(Adaptation adaptation, Adaptation.AnimalType animalType);

    /**
     * Updating existing adaptation period<br>
     * {@link AdaptationServiceImpl#getById(Long)} service method is used
     * @param adaptation  Adaptation period to update
     * @return Updated adaptation period
     * @throws NotFoundException if adaptation does not have an ID or there is no adaptation with such ID in DB
     */
    Adaptation update(Adaptation adaptation);

    /**
     * Getting adaptation period from DB by ID<br>
     * {@link AdaptationRepository#findById(Object)} repository method is used
     * @param id Adaptation period ID
     * @return Adaptation period
     * @throws NotFoundException if there is no adaptation in DB with such ID
     */
    Adaptation getById(Long id);

    /**
     * Getting all adaptations by ID of particular owner<br>
     * {@link AdaptationRepository#findAllByOwnerId(Long)} repository method is used
     * @param ownerId Owner's ID
     * @return List of adaptations
     * @throws NotFoundException If owner does not have any adaptations
     */
    List<Adaptation> getAllByOwnerId(Long ownerId);

    /**
     * Getting list of all adaptation periods<br>
     * {@link AdaptationRepository#findAll()} repository method is used
     * @return List of adaptations
     * @throws NotFoundException If there are no adaptations in DB
     */
    List<Adaptation> getAll();

    /**
     * Deleting an adaptation from DB<br>
     * {@link AdaptationServiceImpl#getById(Long)} service method is used
     * @param adaptation Existing adaptation
     * @throws NotFoundException If there is no adaptation in DB
     */
    void delete(Adaptation adaptation);

    /**
     * Deleting an adaptation from DB by ID<br>
     * {@link AdaptationServiceImpl#getById(Long)} service method is used
     * @param id Id of existing adaptation
     * @throws NotFoundException If there is no adaptation in DB with such ID
     */
    void deleteById(Long id);

}
