package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.User;
import Telegram.Bot.AnimalShelter.repository.UserRepository;
import Telegram.Bot.AnimalShelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User thisUser = getById(user.getTelegramId());
        ObjectUpdater.oldToNew(user, thisUser);
        return userRepository.save(thisUser);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(getById(user.getTelegramId()));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(getById(id).getTelegramId());
    }

    @Override
    public User getById(Long id) {
        Optional<User> optionalUser = userRepository.findByTelegramId(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return optionalUser.get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public String getShelterById(Long id) {
        return getById(id).getShelterType();
    }
}
