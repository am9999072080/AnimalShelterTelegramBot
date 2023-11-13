package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.entity.DogShelter;
import Telegram.Bot.AnimalShelter.repository.DogShelterRepository;
import Telegram.Bot.AnimalShelter.service.DogShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DogShelterServiceImpl implements DogShelterService<DogShelter, Dog> {

    private final DogShelterRepository dogShelterRepository;
    @Override
    public DogShelter create(DogShelter dogShelter) {
        return dogShelterRepository.save(dogShelter);
    }

    @Override
    public DogShelter update(DogShelter dogShelter) {
        DogShelter thisDogShelter = getShelterById(dogShelter.getId());
        ObjectUpdater.oldToNew(dogShelter, thisDogShelter);
        return dogShelterRepository.save(thisDogShelter);
    }

    @Override
    public DogShelter getShelterById(Long id) {
        Optional<DogShelter> dogShelter = dogShelterRepository.findById(id);
        if (dogShelter.isEmpty()) {
            throw new NotFoundException("Shelter not found");
        }
        return dogShelter.get();
    }

    @Override
    public DogShelter getShelterByName(String name) {
        Optional<DogShelter> dogShelter = dogShelterRepository.findByName(name);
        if (dogShelter.isEmpty()) {
            throw new NotFoundException("Shelter not found");
        }
        return dogShelter.get();
    }

    @Override
    public List<DogShelter> getShelter() {
        return dogShelterRepository.findAll();
    }

    @Override
    public List<Dog> getAnimal(Long id) {
        return getShelterById(id).getList();
    }

    @Override
    public String delete(Long id) {
        String result;
        Optional<DogShelter> dogShelter = dogShelterRepository.findById(id);
        if (dogShelter.isPresent()) {
            dogShelterRepository.deleteById(id);
            result = "Entry deleted";
        } else {
            throw new NotFoundException("Shelter not found");
        }
        return result;
    }
}
