package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Volunteer;
import Telegram.Bot.AnimalShelter.repository.VolunteerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceImplTest {

    @Mock
    VolunteerRepository volunteerRepository;
    @InjectMocks
    VolunteerServiceImpl volunteerService;
    private final Long id = 1L;
    private final String firstName = "Petya";

    private final String lastName = "Petin";
    private final  Volunteer volunteer1 = new Volunteer(id, firstName, lastName);

    private final Volunteer volunteer2 = new Volunteer(id, firstName, null);

    private final Volunteer volunteer3 = new Volunteer(id, firstName, lastName);

    private final List<Volunteer> listVolunteer = List.of(volunteer1, volunteer2, volunteer3);




    @Test
    void ShouldCreateAndReturnVolunteerWithAllArgs() {
        when(volunteerRepository.save(volunteer1)).thenReturn((volunteer1));
        Volunteer actual = volunteerService.create(volunteer1);
        assertEquals(volunteer1, actual);
        verify(volunteerRepository, times(1)).save(volunteer1);
    }
    @Test
    void shouldUpdateVolunteerWithOutNullFields() {
        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer1));
        when(volunteerRepository.save(volunteer3)).thenReturn(volunteer3);
        Volunteer actual = volunteerService.update(volunteer2);
        assertEquals(volunteer3, actual);
        verify(volunteerRepository, times(1)).findById(id);
        verify(volunteerRepository, times(1)).save(volunteer3);
    }


    @Test
    void shouldReturnVolunteerFoundByTelegramId() {
        when(volunteerRepository.findById(id)).thenReturn(Optional.of(volunteer1));
        Volunteer actual = volunteerService.getById(id);
        assertEquals(volunteer1, actual);
        verify(volunteerRepository, times(1)).findById(volunteer1.getTelegramId());

    }

    @Test
    void shouldReturnListOfAllVolunteers() {
        when(volunteerRepository.findAll()).thenReturn(listVolunteer);
        List<Volunteer> newListVolunteer = volunteerService.getAll();
        assertEquals(listVolunteer, newListVolunteer);
        verify(volunteerRepository, times(1)).findAll();

    }
    @Test
    void shouldThrowNotFoundExWhenDeletingVolunteer() {
        when(volunteerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> volunteerService.delete(volunteer1));
        verify(volunteerRepository, times(1)).findById(id);

    }

    @Test
    void shouldThrowNotFoundExWhenDeletingVolunteerById() {
        when(volunteerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> volunteerService.deleteById(id));
        verify(volunteerRepository, times(1)).findById(id);


    }

}
