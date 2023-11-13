package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Dog;
import Telegram.Bot.AnimalShelter.entity.DogShelter;

import Telegram.Bot.AnimalShelter.repository.DogShelterRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test - приюта для собак
 */
@ExtendWith(MockitoExtension.class)
class DogShelterServiceImplTest {
    @Mock
    private DogShelterRepository dogShelterRepository;
    @InjectMocks
    private DogShelterServiceImpl dogShelterService;

    Long ID_1 = 1L;
    Long ID_2 = 2L;
    String NAME_DOG_1 = "Буся";
    String NAME_DOG_2 = "Mуся";
    int AGE_DOG_1 = 1;
    String ABOUT_ME_DOG = "В нашем приюте вы можете бесплатно взять кошку или котенка.";
    boolean HEALTHY_TRUE = true;
    boolean VACCINATED_TRUE = true;
    Long OWNER_ID_1 = 111L;
    Long SHELTER_ID_1 = 111L;
    Long OWNER_ID_2 = 222L;
    Long SHELTER_ID_2 = 222L;
    String LOCATION = "Астана, Улица Аккорган, 5в";
    String SAFETY = "Если вы решите взять из приюта котенка, он сначала будет намного больше зависеть от вас" +
            ", чем взрослая кошка, и его нельзя будет надолго оставлять одного.";
    String SECURITY = "Запрещается: Кормить животных.";
    String WORKING_HOURS = "8:00-20:00";
    DogShelter DOG_SHELTER_1 = new DogShelter(ABOUT_ME_DOG, LOCATION, NAME_DOG_1, SAFETY, SECURITY, WORKING_HOURS);
    DogShelter DOG_SHELTER_2 = new DogShelter(ABOUT_ME_DOG, LOCATION, NAME_DOG_2, SAFETY, SECURITY, WORKING_HOURS);
    Dog DOG_1 = new Dog(ID_1, ABOUT_ME_DOG, AGE_DOG_1, HEALTHY_TRUE, VACCINATED_TRUE, OWNER_ID_1, SHELTER_ID_1);
    Dog DOG_2 = new Dog(ID_2, ABOUT_ME_DOG, AGE_DOG_1, HEALTHY_TRUE, VACCINATED_TRUE, OWNER_ID_2, SHELTER_ID_2);


    /**
     * creatDogShelterTest/ТЕСТ-создание приюта для собак
     * <p>
     * Проверка на False: Что такого DOG_SHELTER НЕТ
     * Добавление и возвращение DOG_SHELTER_1
     * Проверка, что созданный DOG_SHELTER_1 добавился и вернулся из сервиса
     */
    @Test
    void creatDogShelterTest() {
        assertFalse(dogShelterService.getShelter().contains(DOG_SHELTER_1));
        when(dogShelterRepository.save(DOG_SHELTER_1)).thenReturn(DOG_SHELTER_1);
        assertEquals(DOG_SHELTER_1, dogShelterService.create(DOG_SHELTER_1));
    }

    /**
     * updateDogShelterTest/ ТЕСТ - обновление приюта для собак
     * <p>
     * Проверка на False: Что такого DOG_SHELTER НЕТ
     * сохранения в dogShelterRepository и возвращение DOG_SHELTER_1
     * dogShelterRepository находит по идентификатору любой Long.class, возвращается DOG_SHELTER_1
     * DOG_SHELTER_2 изменение по ID SHELTER_ID_1
     * Проверка, что измененный по ID SHELTER_ID_1 на DOG_SHELTER_2 это DOG_SHELTER_2
     */

    @Test
    void updateDogShelterTest() {
        assertFalse(dogShelterService.getShelter().contains(DOG_SHELTER_1));
        when(dogShelterRepository.save(any(DogShelter.class))).thenReturn(DOG_SHELTER_1);
        when(dogShelterRepository.findById(any(Long.class))).thenReturn(Optional.of(DOG_SHELTER_1));

        DOG_SHELTER_2.setId(SHELTER_ID_1);
        assertEquals(DOG_SHELTER_2, dogShelterService.update(DOG_SHELTER_2));
    }

    /**
     * updateDogShelterExceptionTest/ТЕСТ - исключение: Обновление приюта Оля собак
     * <p>
     * Проверка на False: Что такого DOG_SHELTER НЕТ
     * Добавление и возвращение DOG_SHELTER_1
     * Проверка, что созданный DOG_SHELTER_1 добавился и вернулся из сервиса
     * NotFoundException выдает исключение, при добавлении уже существующего DOG_SHELTER_1
     */
    @Test
    void updateDogShelterExceptionTest() {
        assertFalse(dogShelterService.getShelter().contains(DOG_SHELTER_1));
        when(dogShelterRepository.save(DOG_SHELTER_1)).thenReturn(DOG_SHELTER_1);
        assertEquals(DOG_SHELTER_1, dogShelterService.create(DOG_SHELTER_1));

        assertThrows(NotFoundException.class,
                () -> dogShelterService.update(DOG_SHELTER_1));
    }

    /**
     * getDogShelterByIdTest/ТЕСТ-получение собачьего приют по Id Test
     * <p>
     * Проверка на False: Что такого DOG_SHELTER НЕТ
     * Добавление и возвращение DOG_SHELTER_1
     * Проверка, что созданный DOG_SHELTER_1 добавился и вернулся из сервиса
     * когда dogShelterRepository находит по идентификатору SHELTER_ID_1, затем возвращает DOG_SHELTER
     * Проверка, получаем DOG_SHELTER_1, при поиске по идентификатору SHELTER_ID_1
     */
    @Test
    void getDogShelterByIdTest() {
        assertFalse(dogShelterService.getShelter().contains(DOG_SHELTER_1));
        when(dogShelterRepository.save(DOG_SHELTER_1)).thenReturn(DOG_SHELTER_1);
        assertEquals(DOG_SHELTER_1, dogShelterService.create(DOG_SHELTER_1));
        when(dogShelterRepository.findById(SHELTER_ID_1)).thenReturn(Optional.of(DOG_SHELTER_1));
        assertEquals(DOG_SHELTER_1, dogShelterService.getShelterById(SHELTER_ID_1));

    }

    /**
     * getDogShelterByIdExceptionTest/ТЕСТ - исключение: получение собачьего приют по Id Test
     * <p>
     * Проверка на False: Что такого DOG_SHELTER НЕТ
     * Добавление и возвращение DOG_SHELTER_1
     * Проверка, что созданный DOG_SHELTER_1 добавился и вернулся из сервиса
     * когда dogShelterRepository находит по идентификатору SHELTER_ID_1, затем возвращает DOG_SHELTER
     * Проверка, получаем DOG_SHELTER_1, при поиске по идентификатору SHELTER_ID_1
     * assert Выдает класс Not Found Exception,
     * NotFoundException выдает исключение, при поиске несуществующего DOG_SHELTER_2
     */

    @Test
    void getDogShelterByIdExceptionTest() {
        assertFalse(dogShelterService.getShelter().contains(DOG_SHELTER_1));
        when(dogShelterRepository.save(DOG_SHELTER_1)).thenReturn(DOG_SHELTER_1);
        assertEquals(DOG_SHELTER_1, dogShelterService.create(DOG_SHELTER_1));
        when(dogShelterRepository.findById(SHELTER_ID_1)).thenReturn(Optional.of(DOG_SHELTER_1));
        assertEquals(DOG_SHELTER_1, dogShelterService.getShelterById(SHELTER_ID_1));
        assertThrows(NotFoundException.class,
                () -> dogShelterService.getShelterById(SHELTER_ID_2));
    }

    /**
     * getDogShelterByNameTest / ТЕСТ - получение приюта для собак по имени
     * <p>
     * Проверка на False: Что такого DOG_SHELTER НЕТ
     * Добавление DOG_SHELTER_1 в dogShelterRepository
     * когда dogShelterRepository находит по имени NAME_DOG_1, то возвращается DOG_SHELTER_1
     * Проверка, получаем DOG_SHELTER_1, при поиске по имени NAME_DOG_1
     */

    @Test
    void getDogShelterByNameTest() {
        assertFalse(dogShelterService.getShelter().contains(DOG_SHELTER_1));
        dogShelterRepository.save(DOG_SHELTER_1);
        when(dogShelterRepository.findByName(NAME_DOG_1)).thenReturn(Optional.of(DOG_SHELTER_1));
        assertEquals(DOG_SHELTER_1, dogShelterService.getShelterByName(NAME_DOG_1));
    }

    /**
     * getDogShelterByNameExceptionTest / ТЕСТ - исключение: получение приюта для собак по имени
     * <p>
     * Проверка на False: Что такого DOG_SHELTER НЕТ
     * Добавление DOG_SHELTER_1 в dogShelterRepository
     * когда dogShelterRepository находит по имени NAME_DOG_1, то возвращается DOG_SHELTER_1
     * Проверка, получаем DOG_SHELTER_1, при поиске по имени NAME_DOG_1
     */
    @Test
    void getDogShelterByNameExceptionTest() {
        assertFalse(dogShelterService.getShelter().contains(DOG_SHELTER_1));
        dogShelterRepository.save(DOG_SHELTER_1);
        assertThrows(NotFoundException.class,
                () -> dogShelterService.getShelterByName(NAME_DOG_2));
    }

    /**
     * getDogShelterTest / ТЕСТ - получение собачьего приюта
     * <p>
     * В List<DogShelter> передаем DOG_SHELTER_1
     * когда dogShelterRepository.findAll, вернуть собачий приют
     * Проверка, получаем dogShelters, при поиске получение приюта
     */
    @Test
    void getDogShelterTest() {
        List<DogShelter> dogShelters = new ArrayList<>();
        dogShelters.add(DOG_SHELTER_1);
        when(dogShelterRepository.findAll()).thenReturn(dogShelters);
        assertEquals(dogShelters, dogShelterService.getShelter());
    }

    @Test
    void getAnimalTest() {
        List<Dog> dogList = new ArrayList<>();
        dogList.add(DOG_1);
        dogList.add(DOG_2);

        DogShelter DOG_SHELTER1_1 = new DogShelter(dogList, SHELTER_ID_1, ABOUT_ME_DOG, LOCATION, NAME_DOG_1, SAFETY, SECURITY, WORKING_HOURS);

        when(dogShelterRepository.findById(DOG_SHELTER1_1.getId())).thenReturn(Optional.of(DOG_SHELTER1_1));

        List<Dog> dogList1 = dogShelterService.getAnimal(DOG_SHELTER1_1.getId());

        assertThat(dogList1).isEqualTo(dogList);
    }

    @Test
    void deleteTest() {
        assertFalse(dogShelterService.getShelter().contains(DOG_SHELTER_1));
        when(dogShelterRepository.save(DOG_SHELTER_1)).thenReturn(DOG_SHELTER_1);
        assertEquals(DOG_SHELTER_1, dogShelterService.create(DOG_SHELTER_1));

        dogShelterRepository.delete(DOG_SHELTER_1);

        assertFalse(dogShelterRepository.findAll().contains(DOG_SHELTER_1));
    }
}