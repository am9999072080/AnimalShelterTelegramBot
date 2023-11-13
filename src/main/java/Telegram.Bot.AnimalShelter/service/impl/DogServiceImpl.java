package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.repository.DogRepository;
import Telegram.Bot.AnimalShelter.service.DogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DogServiceImpl implements DogService {
    private final DogRepository dogRepository;
    @Override
    public Dog create(Dog dog) {
        return dogRepository.save(dog);
    }

    @Override
    public Dog update(Dog dog) {
        Optional<Dog> optionalDog = dogRepository.findById(dog.getId());
        if (optionalDog.isEmpty()) {
            throw new NotFoundException("Dog is not found");
        }
        Dog thisDog = optionalDog.get();
        ObjectUpdater.oldToNew(dog, thisDog);
        return dogRepository.save(thisDog);
    }

    @Override
    public Dog getById(Long id) {
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if (optionalDog.isEmpty()) {
            throw new NotFoundException("Dog is not found");
        }
        return optionalDog.get();
    }

    @Override
    public List<Dog> getAllByOwnerId(Long id) {
        List<Dog> dogList = dogRepository.findAllByOwnerId(id);
        if (dogList.isEmpty()) {
            throw new NotFoundException("Dog is not found");
        }
        return dogList;
    }

    @Override
    public List<Dog> getAll() {
        return dogRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        dogRepository.deleteById(getById(id).getId());
    }
}
