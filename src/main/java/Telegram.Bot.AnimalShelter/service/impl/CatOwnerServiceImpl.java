package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.CatOwner;
import Telegram.Bot.AnimalShelter.exception.EntryAlreadyExistsException;
import Telegram.Bot.AnimalShelter.repository.CatOwnerRepository;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import Telegram.Bot.AnimalShelter.service.CatOwnerService;
import Telegram.Bot.AnimalShelter.service.CatService;
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
public class CatOwnerServiceImpl implements CatOwnerService {

    private final CatOwnerRepository catOwnerRepository;
    private final CatService catService;
    private final UserService userService;
    private final AdaptationService adaptationService;

    @Override
    public CatOwner create(CatOwner catOwner, Adaptation.AnimalType animalType, Long animalId) {
        if (catService.getById(animalId).getOwnerId() != null) {
            throw new EntryAlreadyExistsException("The cat already has an owner");
        }
        adaptationService.create(new Adaptation(
                        catOwner.getTelegramId(),
                        animalId,
                        animalType,
                        LocalDate.now(),
                        LocalDate.now().plusDays(30),
                        LocalDate.now().minusDays(1),
                        new ArrayList<>(),
                        Adaptation.Result.IN_PROGRESS),
                animalType);
        catService.getById(animalId).setOwnerId(catOwner.getTelegramId());
        return catOwnerRepository.save(catOwner);
    }

    @Override
    public CatOwner create(Long id, Adaptation.AnimalType animalType, Long animalId) {
        if (catService.getById(animalId).getOwnerId() != null) {
            throw new EntryAlreadyExistsException("The cat already has an owner");
        }
        CatOwner catOwner = new CatOwner(userService.getById(id));
        adaptationService.create(new Adaptation(
                        id,
                        animalId,
                        animalType,
                        LocalDate.now(),
                        LocalDate.now().plusDays(30),
                        LocalDate.now().minusDays(1),
                        new ArrayList<>(),
                        Adaptation.Result.IN_PROGRESS
                ),
                animalType);
        return catOwnerRepository.save(catOwner);
    }

    @Override
    public CatOwner update(CatOwner catOwner) {
        CatOwner thisCatOwner = getById(catOwner.getId());
        ObjectUpdater.oldToNew(catOwner, thisCatOwner);
        return catOwnerRepository.save(thisCatOwner);
    }

    @Override
    public CatOwner getById(Long id) {
        Optional<CatOwner> optionalCatOwner = catOwnerRepository.findByTelegramId(id);
        if (optionalCatOwner.isEmpty()) {
            throw new NotFoundException("The cat owner is not found");
        }
        return optionalCatOwner.get();
    }

    @Override
    public List<CatOwner> getAll() {
        List<CatOwner> catOwnerList = catOwnerRepository.findAll();
        if (catOwnerList.isEmpty()) {
            throw new NotFoundException("The cat owners are not found");
        }
        return catOwnerList;
    }

    @Override
    public void delete(CatOwner catOwner) {
        catService.getAllByOwnerId(catOwner.getTelegramId()).forEach(cat -> {
            cat.setOwnerId(null);
            catService.update(cat);
        });
        catOwnerRepository.delete(getById(catOwner.getTelegramId()));

    }

    @Override
    public void deleteById(Long id) {
        delete(getById(id));
    }
}
