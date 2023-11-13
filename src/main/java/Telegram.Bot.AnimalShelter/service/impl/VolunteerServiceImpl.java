package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Volunteer;
import Telegram.Bot.AnimalShelter.repository.VolunteerRepository;
import Telegram.Bot.AnimalShelter.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;
    @Override
    public Volunteer create(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer update(Volunteer volunteer) {
        Volunteer thisVolunteer = getById(volunteer.getTelegramId());
        ObjectUpdater.oldToNew(volunteer, thisVolunteer);
        return volunteerRepository.save(thisVolunteer);
    }

    @Override
    public Volunteer getById(Long id) {
        Optional<Volunteer> optionalVolunteer = volunteerRepository.findById(id);
        if (optionalVolunteer.isEmpty()) {
            throw new NotFoundException("There is no volunteer with such id");
        }
        return optionalVolunteer.get();
    }

    @Override
    public List<Volunteer> getAll() {
        List<Volunteer> all = volunteerRepository.findAll();
        if (all.isEmpty()) {
            throw new NotFoundException("There is no volunteers");
        }
        return all;
    }

    @Override
    public void delete(Volunteer volunteer) {
        volunteerRepository.delete(getById(volunteer.getTelegramId()));
    }

    @Override
    public void deleteById(Long id) {
        volunteerRepository.deleteById(getById(id).getTelegramId());
    }
}
