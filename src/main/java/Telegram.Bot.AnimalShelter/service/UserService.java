package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.User;

import java.util.List;

public interface UserService {

    /**
     * Creating and saving user to DB
     *
     * @param user User
     * @return User
     */
    User create(User user);

    /**
     * Changing user data
     *
     * @param user User
     * @return Updated user
     */
    User update(User user);

    /**
     * Deleting user from DB
     *
     * @param user User
     */
    void delete(User user);

    /**
     * Deleting user from DB by ID
     *
     * @param id User ID
     */
    void deleteById(Long id);

    /**
     * Getting user by ID
     * @param id User ID
     * @return User
     */
    User getById(Long id);

    /**
     * Getting list of all users
     *
     * @return List of users
     */
    List<User> getAll();

    /**
     * Getting picked in bot shelter by user ID
     * @param id User ID
     * @return "CAT" or "DOG"
     */
    String getShelterById(Long id);
}
