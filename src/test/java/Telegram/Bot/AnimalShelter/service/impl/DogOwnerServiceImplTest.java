package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.*;
import Telegram.Bot.AnimalShelter.repository.DogOwnerRepository;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import Telegram.Bot.AnimalShelter.service.DogService;
import Telegram.Bot.AnimalShelter.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DogOwnerServiceImplTest {
    @InjectMocks
    DogOwnerServiceImpl dogOwnerServiceImpl;
    @Mock
    DogOwnerRepository dogOwnerRepository;
    @Mock
    DogService dogService;
    @Mock
    UserService userService;
    @Mock
    AdaptationService adaptationService;

    private final Long id = 1L;
    private final Dog dog = new Dog("Dog", 1, true, true, id);
    private final LocalDate localDate = LocalDate.now();
    private final LocalDate localDatePlus30 = LocalDate.now().plusDays(30);
    private final LocalDate localDateMinus1 = LocalDate.now().minusDays(1);
    private final List<Report> reportList = new ArrayList<>();
    private final Adaptation adaptationDog = new Adaptation(reportList, id, id, id, Adaptation.AnimalType.CAT, localDate, localDate, localDate, Adaptation.Result.IN_PROGRESS);
    private final Adaptation adaptation = new Adaptation(id, id, Adaptation.AnimalType.CAT, localDate, localDatePlus30, localDateMinus1, reportList, Adaptation.Result.IN_PROGRESS);
    private final List<Adaptation> adaptationList = List.of(adaptationDog);
    private final Long telegramId = 1L;
    private final String firstName = "Masha";
    private final String lastName = "Mashova";
    private final String phoneNumber = "89141584478";
    private final Dog dogWithOwner = new Dog(1L, "Barsik", 2, true, true, telegramId, 1L);
    private final Dog dogWithoutOwner = new Dog(1L, "Barsik", 2, true, true, null, 1L);
    private final List<Dog> dogList = List.of(dog);
    private final DogOwner dogOwner = new DogOwner(null, null, telegramId, firstName, lastName, phoneNumber, null);
    private final DogOwner dogOwner2 = new DogOwner(null, 1L, telegramId, firstName, lastName, phoneNumber, null);
    private final DogOwner dogOwner3 = new DogOwner(adaptationList, id, telegramId, firstName, lastName, phoneNumber, dogList);
    private final User user = new User(telegramId, firstName, lastName, phoneNumber);
    private final List<DogOwner> dogOwnerList = List.of(dogOwner);


    @Test
    void shouldCreateFromUserAndReturnDogOwnerWithAllArgs() {
        when(dogService.getById(id)).thenReturn(dog);
        when(userService.getById(telegramId)).thenReturn(user);
        when(adaptationService.create(adaptation, Adaptation.AnimalType.CAT)).thenReturn(adaptation);
        when(dogOwnerRepository.save(dogOwner)).thenReturn(dogOwner2);
        DogOwner actual = dogOwnerServiceImpl.create(telegramId, Adaptation.AnimalType.CAT, id);
        assertEquals(dogOwner2, actual);
    }

    @Test
    void shouldCreateFromUserAndReturnDogOwner() {
        when(dogService.getById(id)).thenReturn(dog);
        when(dogOwnerRepository.save(dogOwner)).thenReturn(dogOwner);
        when(adaptationService.create(adaptation, Adaptation.AnimalType.CAT)).thenReturn(adaptation);
        DogOwner actual = dogOwnerServiceImpl.create(dogOwner, Adaptation.AnimalType.CAT, id);
        assertEquals(dogOwner, actual);
    }


    @Test
    void shouldUpdateDogOwnerWithoutNullField() {
        when(dogOwnerRepository.findByTelegramId(id)).thenReturn(Optional.of(dogOwner3));
        when(dogOwnerRepository.save(dogOwner3)).thenReturn(dogOwner3);
        DogOwner dogOwnerNew = dogOwnerServiceImpl.update(dogOwner3);
        assertEquals(dogOwner3, dogOwnerNew);
        verify(dogOwnerRepository, times(1)).findByTelegramId(id);
        verify(dogOwnerRepository, times(1)).save(dogOwner3);
    }

    @Test
    void shouldReturnDogOwnerById() {
        when(dogOwnerRepository.findByTelegramId(id)).thenReturn(Optional.of(dogOwner));
        DogOwner dogOwnerNew = dogOwnerServiceImpl.getById(id);
        assertEquals(dogOwner, dogOwnerNew);
        verify(dogOwnerRepository, times(1)).findByTelegramId(id);
    }

    @Test
    void shouldReturnAllDogOwners() {
        when(dogOwnerRepository.findAll()).thenReturn(dogOwnerList);
        List<DogOwner> dogOwnerListNew = dogOwnerServiceImpl.getAll();
        assertEquals(dogOwnerList, dogOwnerListNew);
        verify(dogOwnerRepository, times(1)).findAll();
    }

    @Test
    void shouldDeletingDogOwner() {
        when(dogService.update(dogWithOwner)).thenReturn(dogWithoutOwner);
        when(dogService.getAllByOwnerId(telegramId)).thenReturn(List.of(dogWithOwner));
        when(dogOwnerRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(dogOwner));
        doNothing().when(dogOwnerRepository).delete(dogOwner);
        dogOwnerServiceImpl.delete(dogOwner);
        verify(dogService, times(1)).update(dogWithOwner);
        verify(dogService, times(1)).getAllByOwnerId(telegramId);
        verify(dogOwnerRepository, times(1)).findByTelegramId(telegramId);
    }

    @Test
    void shouldDeletingDogOwnerById() {
        when(dogService.update(dogWithOwner)).thenReturn(dogWithoutOwner);
        when(dogService.getAllByOwnerId(telegramId)).thenReturn(List.of(dogWithOwner));
        when(dogOwnerRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(dogOwner));
        doNothing().when(dogOwnerRepository).delete(dogOwner);
        dogOwnerServiceImpl.deleteById(dogOwner.getTelegramId());
        verify(dogService, times(1)).update(dogWithOwner);
        verify(dogService, times(1)).getAllByOwnerId(telegramId);
        verify(dogOwnerRepository, times(2)).findByTelegramId(telegramId);
    }
}