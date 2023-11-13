package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.repository.AdaptationRepository;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import Telegram.Bot.AnimalShelter.service.CatService;
import Telegram.Bot.AnimalShelter.service.DogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdaptationServiceImpl implements AdaptationService {

    private final AdaptationRepository adaptationRepository;
    private final CatService catService;
    private final DogService dogService;


    @Override
    public Adaptation create(Adaptation adaptation) {
        return adaptationRepository.save(adaptation);
    }
    @Override
    public Adaptation create(Adaptation adaptation, Adaptation.AnimalType animalType) {
        if (animalType.equals(Adaptation.AnimalType.CAT)) {
            catService.getById(adaptation.getAnimalId()).setOwnerId(adaptation.getOwnerId());
        } else if (animalType.equals(Adaptation.AnimalType.DOG)) {
            dogService.getById(adaptation.getAnimalId()).setOwnerId(adaptation.getOwnerId());
        }
        return adaptationRepository.save(adaptation);
    }

    @Override
    public Adaptation update(Adaptation adaptation) {
        Adaptation thisAdaptation = getById(adaptation.getId());
        ObjectUpdater.oldToNew(adaptation, thisAdaptation);
        return adaptationRepository.save(thisAdaptation);
    }

    @Override
    public Adaptation getById(Long id) {
        Optional<Adaptation> adaptationOptional = adaptationRepository.findById(id);
        if (adaptationOptional.isEmpty()) {
            throw new NotFoundException("Adaptation period is not found");
        }
        return adaptationOptional.get();
    }

    @Override
    public List<Adaptation> getAllByOwnerId(Long ownerId) {
        List<Adaptation> adaptationList = adaptationRepository.findAllByOwnerId(ownerId);
        if (adaptationList.isEmpty()) {
            throw new NotFoundException("Adaptation periods are not found");
        }
        return adaptationList;
    }

    @Override
    public List<Adaptation> getAll() {
        List<Adaptation> adaptationList = adaptationRepository.findAll();
        if (adaptationList.isEmpty()) {
            throw new NotFoundException("Adaptation periods are not found");
        }
        return adaptationList;
    }

    @Override
    public void delete(Adaptation adaptation) {
        adaptationRepository.delete(getById(adaptation.getId()));
    }

    @Override
    public void deleteById(Long id) {
        adaptationRepository.deleteById(getById(id).getId());
    }
}
