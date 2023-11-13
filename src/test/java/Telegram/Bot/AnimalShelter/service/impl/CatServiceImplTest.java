package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Cat;
import Telegram.Bot.AnimalShelter.repository.CatRepository;
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
class CatServiceImplTest {
    @Mock
    CatRepository catRepository;
    @InjectMocks
    CatServiceImpl catService;
    private final Cat cat1 = new Cat(1L, "name", 1, true, true, 1L, 1L);
    private final Cat cat2 = new Cat(1L, "name", 1, null, null, null, null);
    private final Cat cat3 = new Cat(1L, "name", 1, true, true, 1L, 1L);
    private final Long ownerId = 1L;
    private final Long id = 1L;


    @Test
    void shouldCreateAndReturnCatWithAllArgs() {
        when(catRepository.save(cat1)).thenReturn(cat1);
        Cat actual = catService.create(cat1);
        assertEquals(cat1, actual);
        verify(catRepository, times(1)).save(cat1);
    }
    @Test
    void shouldUpdateCatWithoutNullFields() {
        when(catRepository.findById(id)).thenReturn(Optional.of(cat1));
        when(catRepository.save(cat3)).thenReturn(cat3);
        Cat actual = catService.update(cat2);
        assertEquals(cat3, actual);
        verify(catRepository, times(1)).findById(id);
        verify(catRepository, times(1)).save(cat3);
    }

    @Test
    void shouldReturnCatFoundById() {
        when(catRepository.findById(id)).thenReturn(Optional.of(cat1));
        Cat actual = catService.getById(id);
        assertEquals(cat1, actual);
        verify(catRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnListOfOwnersWhenGetAllOwner() {
        when(catRepository.findAllByOwnerId(ownerId)).thenReturn(List.of(cat1));
        List<Cat> listCats = catService.getAllByOwnerId(ownerId);
        assertEquals(List.of(cat1), listCats);
        verify(catRepository, times(1)).findAllByOwnerId(ownerId);
    }

    @Test
    void shouldReturnListAllCats() {
        List<Cat> listCats = List.of(cat1);
        when(catRepository.findAll()).thenReturn(listCats);
        List<Cat> listCats2 = catService.getAll();
        assertEquals(listCats, listCats2);
        verify(catRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowNotFoundExWhenDeletingCat() {
        when(catRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> catService.delete(id));
        verify(catRepository, times(1)).findById(id);
    }


}
