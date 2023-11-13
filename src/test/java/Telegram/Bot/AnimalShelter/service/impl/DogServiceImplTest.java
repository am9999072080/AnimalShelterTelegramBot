package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.repository.DogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogServiceImplTest {
    @Mock
    DogRepository dogRepository;
    @InjectMocks
    DogServiceImpl dogService;

    private final Dog dog1 = new Dog(1L, "name", 3, true, true, 1L, 1L);
    private final Dog dog2 = new Dog(1L, "name", 3, true, true, null, null);
    private final Dog dog3 = new Dog(1L, "name", 3, true, true, 1L, 1L);
    private final Long ownerId = 1L;

    private final Long id = 1L;

    @Test
    void shouldCreateAndReturnDogWithAllArgs() {
        when(dogRepository.save(dog1)).thenReturn(dog1);
        Dog newDog = dogService.create(dog1);
        assertEquals(dog1, newDog);
        verify(dogRepository, times(1)).save(dog1);


    }
    @Test
    void shouldUpdateDogWithOutNullFields() {
        when(dogRepository.findById(id)).thenReturn(Optional.of(dog1));
        when(dogRepository.save(dog3)).thenReturn(dog3);
        Dog actual = dogService.update(dog2);
        assertEquals(dog3, actual);
        verify(dogRepository, times(1)).findById(id);
        verify(dogRepository, times(1)).save(dog3);

    }

    @Test
    void shouldReturnDogFoundById() {
        when(dogRepository.findById(id)).thenReturn(Optional.of(dog1));
        Dog actual = dogService.getById(id);
        assertEquals(dog1,actual);
        verify(dogRepository,times(1)).findById(id);
    }

    @Test
    void ShouldReturnListOfOwnersGetAllById() {
        when(dogRepository.findAllByOwnerId(ownerId)).thenReturn(List.of(dog1));
        List<Dog>listDogs=dogService.getAllByOwnerId(ownerId);
        assertEquals(List.of(dog1),listDogs);
        verify(dogRepository,times(1)).findAllByOwnerId(ownerId);

    }

    @Test
    void shouldReturnListAllDogs() {
        List<Dog> listDogs = List.of(dog1);
        when(dogRepository.findAll()).thenReturn(listDogs);
        List<Dog> listDogs2 = dogService.getAll();
        assertEquals(listDogs, listDogs2);
        verify(dogRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowNotFoundExWhenDeletingDog() {
        when(dogRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> dogService.delete(id));
        verify(dogRepository, times(1)).findById(id);

    }

}