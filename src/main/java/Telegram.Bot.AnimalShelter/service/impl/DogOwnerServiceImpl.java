package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.DogOwner;
import Telegram.Bot.AnimalShelter.exception.EntryAlreadyExistsException;
import Telegram.Bot.AnimalShelter.repository.DogOwnerRepository;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import Telegram.Bot.AnimalShelter.service.DogOwnerService;
import Telegram.Bot.AnimalShelter.service.DogService;
import Telegram.Bot.AnimalShelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DogOwnerServiceImpl implements DogOwnerService {

    private final DogOwnerRepository dogOwnerRepository;
    private final DogService dogService;
    private final UserService userService;
    private final AdaptationService adaptationService;

    @Override
    public DogOwner create(DogOwner dogOwner, Adaptation.AnimalType animalType, Long animalId) {
        if (dogService.getById(animalId).getOwnerId() != null) {
            throw new EntryAlreadyExistsException("The dog already has an owner");
        }
        adaptationService.create(new Adaptation(
                        dogOwner.getTelegramId(),
                        animalId,
                        animalType,
                        LocalDate.now(),
                        LocalDate.now().plusDays(30),
                        LocalDate.now().minusDays(1),
                        new ArrayList<>(),
                        Adaptation.Result.IN_PROGRESS),
                animalType);
        dogService.getById(animalId).setOwnerId(dogOwner.getTelegramId());
        return dogOwnerRepository.save(dogOwner);
    }

    @Override
    public DogOwner create(Long id, Adaptation.AnimalType animalType, Long animalId) {
        if (dogService.getById(animalId).getOwnerId() != null) {
            throw new EntryAlreadyExistsException("The dog already has an owner");
        }
        DogOwner dogOwner = new DogOwner(userService.getById(id));
        adaptationService.create(new Adaptation(
                id,
                        animalId,
                        animalType,
                        LocalDate.now(),
                        LocalDate.now().plusDays(30),
                        LocalDate.now().minusDays(1),
                        new ArrayList<>(),
                        Adaptation.Result.IN_PROGRESS),
                animalType);
        return dogOwnerRepository.save(dogOwner);

    }

    @Override
    public DogOwner update(DogOwner dogOwner) {
        DogOwner thisDogOwner = getById(dogOwner.getId());
        ObjectUpdater.oldToNew(dogOwner, thisDogOwner);
        return dogOwnerRepository.save(thisDogOwner);
    }

    @Override
    public DogOwner getById(Long id) {
        Optional<DogOwner> optionalDogOwner = dogOwnerRepository.findByTelegramId(id);
        if (optionalDogOwner.isEmpty()) {
            throw new NotFoundException("The dog owner is not found");
        }
        return optionalDogOwner.get();
    }

    @Override
    public List<DogOwner> getAll() {
        List<DogOwner> dogOwnerList = dogOwnerRepository.findAll();
        if (dogOwnerList.isEmpty()) {
            throw new NotFoundException("The dog owners are not found");
        }
        return dogOwnerList;
    }

    @Override
    public void delete(DogOwner dogOwner) {
        dogService.getAllByOwnerId(dogOwner.getTelegramId()).forEach(dog -> {
            dog.setOwnerId(null);
            dogService.update(dog);
        });
        dogOwnerRepository.delete(getById(dogOwner.getTelegramId()));

    }

    @Override
    public void deleteById(Long id) {
        delete(getById(id));
    }
}
