package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.*;
import Telegram.Bot.AnimalShelter.repository.CatOwnerRepository;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import Telegram.Bot.AnimalShelter.service.CatService;
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
class CatOwnerServiceImplTest {
    @InjectMocks
    CatOwnerServiceImpl catOwnerServiceImpl;
    @Mock
    CatOwnerRepository catOwnerRepository;
    @Mock
    CatService catService;
    @Mock
    UserService userService;
    @Mock
    AdaptationService adaptationService;

    private final Long id = 1L;
    private final Cat cat = new Cat("Cat", 1, true, true, id);
    private final LocalDate localDate = LocalDate.now();
    private final LocalDate localDatePlus30 = LocalDate.now().plusDays(30);
    private final LocalDate localDateMinus1 = LocalDate.now().minusDays(1);
    private final List<Report> reportList = new ArrayList<>();
    private final Adaptation adaptationCat = new Adaptation(reportList, id, id, id, Adaptation.AnimalType.CAT, localDate, localDate, localDate, Adaptation.Result.IN_PROGRESS);
    private final Adaptation adaptation = new Adaptation(id, id, Adaptation.AnimalType.CAT, localDate, localDatePlus30, localDateMinus1, reportList, Adaptation.Result.IN_PROGRESS);
    private final List<Adaptation> adaptationList = List.of(adaptationCat);
    private final Long telegramId = 1L;
    private final String firstName = "Masha";
    private final String lastName = "Mashova";
    private final String phoneNumber = "89141584478";
    private final Cat catWithOwner = new Cat(1L, "Barsik", 2, true, true, telegramId, 1L);
    private final Cat catWithoutOwner = new Cat(1L, "Barsik", 2, true, true, null, 1L);
    private final List<Cat> catList = List.of(cat);
    private final CatOwner catOwner = new CatOwner(null, null, telegramId, firstName, lastName, phoneNumber, null);
    private final CatOwner catOwner2 = new CatOwner(null, 1L, telegramId, firstName, lastName, phoneNumber, null);
    private final CatOwner catOwner3 = new CatOwner(adaptationList, id, telegramId, firstName, lastName, phoneNumber, catList);
    private final User user = new User(telegramId, firstName, lastName, phoneNumber);
    private final List<CatOwner> catOwnerList = List.of(catOwner);


    @Test
    void shouldCreateFromUserAndReturnCatOwnerWithAllArgs() {
        when(catService.getById(id)).thenReturn(cat);
        when(userService.getById(telegramId)).thenReturn(user);
        when(adaptationService.create(adaptation, Adaptation.AnimalType.CAT)).thenReturn(adaptation);
        when(catOwnerRepository.save(catOwner)).thenReturn(catOwner2);
        CatOwner actual = catOwnerServiceImpl.create(telegramId, Adaptation.AnimalType.CAT, id);
        assertEquals(catOwner2, actual);
    }

    @Test
    void shouldCreateFromUserAndReturnCatOwner() {
        when(catService.getById(id)).thenReturn(cat);
        when(catOwnerRepository.save(catOwner)).thenReturn(catOwner);
        when(adaptationService.create(adaptation, Adaptation.AnimalType.CAT)).thenReturn(adaptation);
        CatOwner actual = catOwnerServiceImpl.create(catOwner, Adaptation.AnimalType.CAT, id);
        assertEquals(catOwner, actual);
    }


    @Test
    void shouldUpdateCatOwnerWithoutNullField() {
        when(catOwnerRepository.findByTelegramId(id)).thenReturn(Optional.of(catOwner3));
        when(catOwnerRepository.save(catOwner3)).thenReturn(catOwner3);
        CatOwner catOwnerNew = catOwnerServiceImpl.update(catOwner3);
        assertEquals(catOwner3, catOwnerNew);
        verify(catOwnerRepository, times(1)).findByTelegramId(id);
        verify(catOwnerRepository, times(1)).save(catOwner3);
    }

    @Test
    void shouldReturnCatOwnerById() {
        when(catOwnerRepository.findByTelegramId(id)).thenReturn(Optional.of(catOwner));
        CatOwner catOwnerNew = catOwnerServiceImpl.getById(id);
        assertEquals(catOwner, catOwnerNew);
        verify(catOwnerRepository, times(1)).findByTelegramId(id);
    }

    @Test
    void shouldReturnAllCatOwners() {
        when(catOwnerRepository.findAll()).thenReturn(catOwnerList);
        List<CatOwner> catOwnerListNew = catOwnerServiceImpl.getAll();
        assertEquals(catOwnerList, catOwnerListNew);
        verify(catOwnerRepository, times(1)).findAll();
    }

    @Test
    void shouldDeletingCatOwner() {
        when(catService.update(catWithOwner)).thenReturn(catWithoutOwner);
        when(catService.getAllByOwnerId(telegramId)).thenReturn(List.of(catWithOwner));
        when(catOwnerRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(catOwner));
        doNothing().when(catOwnerRepository).delete(catOwner);
        catOwnerServiceImpl.delete(catOwner);
        verify(catService, times(1)).update(catWithOwner);
        verify(catService, times(1)).getAllByOwnerId(telegramId);
        verify(catOwnerRepository, times(1)).findByTelegramId(telegramId);
    }

    @Test
    void shouldDeletingCatOwnerById() {
        when(catService.update(catWithOwner)).thenReturn(catWithoutOwner);
        when(catService.getAllByOwnerId(telegramId)).thenReturn(List.of(catWithOwner));
        when(catOwnerRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(catOwner));
        doNothing().when(catOwnerRepository).delete(catOwner);
        catOwnerServiceImpl.deleteById(catOwner.getTelegramId());
        verify(catService, times(1)).update(catWithOwner);
        verify(catService, times(1)).getAllByOwnerId(telegramId);
        verify(catOwnerRepository, times(2)).findByTelegramId(telegramId);
    }
}