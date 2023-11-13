package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Cat;
import Telegram.Bot.AnimalShelter.repository.CatRepository;
import Telegram.Bot.AnimalShelter.service.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;
    @Override
    public Cat create(Cat cat) {
        return catRepository.save(cat);
    }

    @Override
    public Cat update(Cat cat) {
        Optional<Cat> optionalCat = catRepository.findById(cat.getId());
        if (optionalCat.isEmpty()) {
            throw new NotFoundException("Cat is not found");
        }
        Cat thisCat = optionalCat.get();
        ObjectUpdater.oldToNew(cat, thisCat);
        return catRepository.save(thisCat);
    }

    @Override
    public Cat getById(Long id) {
        Optional<Cat> optionalCat = catRepository.findById(id);
        if (optionalCat.isEmpty()) {
            throw new NotFoundException("Cat is not found");
        }
        return optionalCat.get();
    }

    @Override
    public List<Cat> getAllByOwnerId(Long id) {
        List<Cat> catList = catRepository.findAllByOwnerId(id);
        if (catList.isEmpty()) {
            throw new NotFoundException("Cat is not found");
        }
        return catList;
    }

    @Override
    public List<Cat> getAll() {
        return catRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        catRepository.deleteById(getById(id).getId());
    }
}
