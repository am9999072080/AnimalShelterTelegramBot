package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.Cat;
import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.entity.Report;
import Telegram.Bot.AnimalShelter.repository.AdaptationRepository;
import Telegram.Bot.AnimalShelter.service.CatService;
import Telegram.Bot.AnimalShelter.service.DogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdaptationServiceImplTest {

    @Mock
    AdaptationRepository adaptationRepository;
    @Mock
    CatService catService;
    @Mock
    DogService dogService;
    @InjectMocks
    AdaptationServiceImpl adaptationService;

    private final Long id = 1L;
    private final Cat cat = new Cat("Cat", 1, true, true, id);
    private final Dog dog = new Dog("Dog", 1, true, true, id);
    private final LocalDate localDate = LocalDate.now();
    private final List<Report> reportList = new ArrayList<>();
    private final Adaptation adaptation = new Adaptation(id, id, Adaptation.AnimalType.CAT, localDate, localDate, localDate, reportList, Adaptation.Result.IN_PROGRESS);
    private final Adaptation nAdaptation = new Adaptation(id, id, Adaptation.AnimalType.CAT, localDate, localDate, localDate, reportList, Adaptation.Result.IN_PROGRESS);
    private final Adaptation adaptationDog = new Adaptation(reportList, id, id, id, Adaptation.AnimalType.DOG, localDate, localDate, localDate, Adaptation.Result.IN_PROGRESS);
    private final Adaptation adaptationCat = new Adaptation(id, id, Adaptation.AnimalType.CAT, localDate, localDate, localDate, reportList, Adaptation.Result.IN_PROGRESS);
    private final List<Adaptation> adaptationList = List.of(adaptationDog);


    @Test
    void shouldCreateAndReturn() {
        when(adaptationRepository.save(adaptation)).thenReturn(adaptation);
        Adaptation newAdaptation = adaptationService.create(nAdaptation);
        assertEquals(adaptation, newAdaptation);
        verify(adaptationRepository, times(1)).save(adaptation);
    }

    @Test
    void shouldCreateAndReturnAdaptationWithAnimalTypeCat() {
        when(adaptationRepository.save(adaptationCat)).thenReturn(adaptationCat);
        when(catService.getById(id)).thenReturn(cat);
        Adaptation newAdaptationCat = adaptationService.create(adaptationCat, Adaptation.AnimalType.CAT);
        assertEquals(adaptationCat, newAdaptationCat);
        verify(adaptationRepository, times(1)).save(adaptationCat);
        verify(catService, times(1)).getById(id);
    }

    @Test
    void shouldCreateAndReturnAdaptationWithAnimalTypeDog() {
        when(adaptationRepository.save(adaptationDog)).thenReturn(adaptationDog);
        when(dogService.getById(id)).thenReturn(dog);
        Adaptation newAdaptationDog = adaptationService.create(adaptationDog, Adaptation.AnimalType.DOG);
        assertEquals(adaptationDog, newAdaptationDog);
        verify(adaptationRepository, times(1)).save(adaptationDog);
        verify(dogService, times(1)).getById(id);
    }

    @Test
    void shouldUpdateAdaptationWithoutNullField() {
        when(adaptationRepository.findById(id)).thenReturn(Optional.of(adaptationDog));
        when(adaptationRepository.save(adaptationDog)).thenReturn(adaptationDog);
        Adaptation adaptationNew = adaptationService.update(adaptationDog);
        assertEquals(adaptationDog, adaptationNew);
        verify(adaptationRepository, times(1)).findById(id);
        verify(adaptationRepository, times(1)).save(adaptationDog);
    }

    @Test
    void shouldReturnAdaptationById() {
        when(adaptationRepository.findById(id)).thenReturn(Optional.of(adaptationDog));
        Adaptation newAdaptation = adaptationService.getById(id);
        assertEquals(adaptationDog, newAdaptation);
        verify(adaptationRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnAllAdaptationsByOwnerId() {
        when(adaptationRepository.findAllByOwnerId(id)).thenReturn(adaptationList);
        List<Adaptation> adaptationListNew = adaptationService.getAllByOwnerId(id);
        assertEquals(adaptationList, adaptationListNew);
        verify(adaptationRepository, times(1)).findAllByOwnerId(id);
    }

    @Test
    void shouldReturnAllAdaptationsList() {
        when(adaptationRepository.findAll()).thenReturn(adaptationList);
        List<Adaptation> adaptationListNew = adaptationService.getAll();
        assertEquals(adaptationList, adaptationListNew);
        verify(adaptationRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowNotFoundExeptionWhenDeletingAdaptation() {
        when(adaptationRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> adaptationService.deleteById(id));
        verify(adaptationRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowNotFoundExeptionWhenGetById() {
        when(adaptationRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> adaptationService.getById(id));
        verify(adaptationRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowNotFoundExeptionWhenGetAllByOwnerId() {
        when(adaptationRepository.findAllByOwnerId(id)).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> adaptationService.getAllByOwnerId(id));
        verify(adaptationRepository, times(1)).findAllByOwnerId(id);
    }

    @Test
    void shouldThrowNotFoundExeptionWhenGetAll() {
        when(adaptationRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> adaptationService.getAll());
        verify(adaptationRepository, times(1)).findAll();
    }
}