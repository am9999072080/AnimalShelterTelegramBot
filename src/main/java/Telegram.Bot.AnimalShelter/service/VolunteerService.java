package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.Volunteer;

import java.util.List;

public interface VolunteerService {
    /**
     * Creating and saving volunteer to DB
     *
     * @param volunteer Volunteer
     * @return Volunteer
     */
    Volunteer create(Volunteer volunteer);

    /**
     * Changing volunteer data
     *
     * @param volunteer Volunteer
     * @return Updated volunteer
     */
    Volunteer update(Volunteer volunteer);

    /**
     * Getting volunteer by ID
     * @param id Volunteer ID
     * @return Volunteer
     */
    Volunteer getById(Long id);

    /**
     * Getting list of all volunteers
     *
     * @return List of volunteers
     */
    List<Volunteer> getAll();

    /**
     * Deleting volunteer from DB
     *
     * @param volunteer Volunteer
     */
    void delete(Volunteer volunteer);

    /**
     * Deleting volunteer from DB by ID
     *
     * @param id Volunteer ID
     */
    void deleteById(Long id);
}
