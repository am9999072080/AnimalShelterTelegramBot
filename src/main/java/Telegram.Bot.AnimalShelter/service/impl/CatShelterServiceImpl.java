package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.CatShelter;
import Telegram.Bot.AnimalShelter.entity.Cat;
import Telegram.Bot.AnimalShelter.repository.CatShelterRepository;
import Telegram.Bot.AnimalShelter.service.CatShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatShelterServiceImpl implements CatShelterService {

    private final CatShelterRepository catShelterRepository;
    @Override
    public CatShelter create(CatShelter catShelter) {
        return catShelterRepository.save(catShelter);
    }

    @Override
    public CatShelter update(CatShelter catShelter) {
        CatShelter thisCatShelter = getShelterById(catShelter.getId());
        ObjectUpdater.oldToNew(catShelter, thisCatShelter);
        return catShelterRepository.save(thisCatShelter);
    }

    @Override
    public CatShelter getShelterById(Long id) {
        Optional<CatShelter> catShelter = catShelterRepository.findById(id);
        if (catShelter.isEmpty()) {
            throw new NotFoundException("Shelter not found");
        }
        return catShelter.get();
    }

    @Override
    public CatShelter getShelterByName(String name) {
        Optional<CatShelter> catShelter = catShelterRepository.findByName(name);
        if (catShelter.isEmpty()) {
            throw new NotFoundException("Shelter not found");
        }
        return catShelter.get();
    }

    @Override
    public List<CatShelter> getShelter() {
        return catShelterRepository.findAll();
    }

    @Override
    public List<Cat> getAnimal(Long id) {
        return getShelterById(id).getList();
    }

    @Override
    public String delete(Long id) {
        String result;
        Optional<CatShelter> catShelter = catShelterRepository.findById(id);
        if (catShelter.isPresent()) {
            catShelterRepository.deleteById(id);
            result = "Entry deleted";
        } else {
            throw new NotFoundException("Shelter not found");
        }
        return result;
    }}
