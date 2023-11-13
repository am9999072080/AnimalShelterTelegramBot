package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Cat;
import Telegram.Bot.AnimalShelter.entity.CatShelter;
import Telegram.Bot.AnimalShelter.repository.CatShelterRepository;

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
 * Test - приюта для кошек
 */
@ExtendWith(MockitoExtension.class)
class CatShelterServiceImplTest {
    @Mock
    private CatShelterRepository catShelterRepository;
    @InjectMocks
    private CatShelterServiceImpl catShelterService;

    Long ID_1 = 1L;
    Long ID_2 = 2L;
    String NAME_CAT_1 = "Буся";
    String NAME_CAT_2 = "Mуся";
    int AGE_CAT_1 = 1;
    String ABOUT_ME_CAT = "В нашем приюте вы можете бесплатно взять кошку или котенка.";
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
    CatShelter CAT_SHELTER_1 = new CatShelter(ABOUT_ME_CAT, LOCATION, NAME_CAT_1, SAFETY, SECURITY, WORKING_HOURS);
    CatShelter CAT_SHELTER_2 = new CatShelter(ABOUT_ME_CAT, LOCATION, NAME_CAT_2, SAFETY, SECURITY, WORKING_HOURS);
    Cat CAT_1 = new Cat(ID_1, ABOUT_ME_CAT, AGE_CAT_1, HEALTHY_TRUE, VACCINATED_TRUE, OWNER_ID_1, SHELTER_ID_1);
    Cat CAT_2 = new Cat(ID_2, ABOUT_ME_CAT, AGE_CAT_1, HEALTHY_TRUE, VACCINATED_TRUE, OWNER_ID_2, SHELTER_ID_2);


    /**
     * creatCatShelterTest/ТЕСТ-создание приюта для кошек
     * <p>
     * Проверка на False: Что такого CAT_SHELTER НЕТ
     * Добавление и возвращение CAT_SHELTER_1
     * Проверка, что созданный CAT_SHELTER_1 добавился и вернулся из сервиса
     */
    @Test
    void creatCatShelterTest() {
        assertFalse(catShelterService.getShelter().contains(CAT_SHELTER_1));
        when(catShelterRepository.save(CAT_SHELTER_1)).thenReturn(CAT_SHELTER_1);
        assertEquals(CAT_SHELTER_1, catShelterService.create(CAT_SHELTER_1));
    }

    /**
     * updateCatShelterTest/ ТЕСТ - обновление приюта для кошек
     * <p>
     * Проверка на False: Что такого CAT_SHELTER НЕТ
     * сохранения в catShelterRepository и возвращение CAT_SHELTER_1
     * catShelterRepository находит по идентификатору любой Long.class, возвращается CAT_SHELTER_1
     * CAT_SHELTER_2 изменение по ID SHELTER_ID_1
     * Проверка, что измененный по ID SHELTER_ID_1 на CAT_SHELTER_2 это CAT_SHELTER_2
     */

    @Test
    void updateCatShelterTest() {
        assertFalse(catShelterService.getShelter().contains(CAT_SHELTER_1));
        when(catShelterRepository.save(any(CatShelter.class))).thenReturn(CAT_SHELTER_1);
        when(catShelterRepository.findById(any(Long.class))).thenReturn(Optional.of(CAT_SHELTER_1));

        CAT_SHELTER_2.setId(SHELTER_ID_1);
        assertEquals(CAT_SHELTER_2, catShelterService.update(CAT_SHELTER_2));
    }

    /**
     * updateCatShelterExceptionTest/ТЕСТ - исключение: Обновление приюта Оля кошек
     * <p>
     * Проверка на False: Что такого CAT_SHELTER НЕТ
     * Добавление и возвращение CAT_SHELTER_1
     * Проверка, что созданный CAT_SHELTER_1 добавился и вернулся из сервиса
     * NotFoundException выдает исключение, при добавлении уже существующего CAT_SHELTER_1
     */
    @Test
    void updateCatShelterExceptionTest() {
        assertFalse(catShelterService.getShelter().contains(CAT_SHELTER_1));
        when(catShelterRepository.save(CAT_SHELTER_1)).thenReturn(CAT_SHELTER_1);
        assertEquals(CAT_SHELTER_1, catShelterService.create(CAT_SHELTER_1));

        assertThrows(NotFoundException.class,
                () -> catShelterService.update(CAT_SHELTER_1));
    }

    /**
     * getCatShelterByIdTest/ТЕСТ-получение кошачьего приют по Id Test
     * <p>
     * Проверка на False: Что такого CAT_SHELTER НЕТ
     * Добавление и возвращение CAT_SHELTER_1
     * Проверка, что созданный CAT_SHELTER_1 добавился и вернулся из сервиса
     * когда catShelterRepository находит по идентификатору SHELTER_ID_1, затем возвращает CAT_SHELTER
     * Проверка, получаем CAT_SHELTER_1, при поиске по идентификатору SHELTER_ID_1
     */
    @Test
    void getCatShelterByIdTest() {
        assertFalse(catShelterService.getShelter().contains(CAT_SHELTER_1));
        when(catShelterRepository.save(CAT_SHELTER_1)).thenReturn(CAT_SHELTER_1);
        assertEquals(CAT_SHELTER_1, catShelterService.create(CAT_SHELTER_1));
        when(catShelterRepository.findById(SHELTER_ID_1)).thenReturn(Optional.of(CAT_SHELTER_1));
        assertEquals(CAT_SHELTER_1, catShelterService.getShelterById(SHELTER_ID_1));

    }

    /**
     * getCatShelterByIdExceptionTest/ТЕСТ - исключение: получение кошачьего приют по Id Test
     * <p>
     * Проверка на False: Что такого CAT_SHELTER НЕТ
     * Добавление и возвращение CAT_SHELTER_1
     * Проверка, что созданный CAT_SHELTER_1 добавился и вернулся из сервиса
     * когда catShelterRepository находит по идентификатору SHELTER_ID_1, затем возвращает CAT_SHELTER
     * Проверка, получаем CAT_SHELTER_1, при поиске по идентификатору SHELTER_ID_1
     * assert Выдает класс Not Found Exception,
     * NotFoundException выдает исключение, при поиске несуществующего CAT_SHELTER_2
     */

    @Test
    void getCatShelterByIdExceptionTest() {
        assertFalse(catShelterService.getShelter().contains(CAT_SHELTER_1));
        when(catShelterRepository.save(CAT_SHELTER_1)).thenReturn(CAT_SHELTER_1);
        assertEquals(CAT_SHELTER_1, catShelterService.create(CAT_SHELTER_1));
        when(catShelterRepository.findById(SHELTER_ID_1)).thenReturn(Optional.of(CAT_SHELTER_1));
        assertEquals(CAT_SHELTER_1, catShelterService.getShelterById(SHELTER_ID_1));
        assertThrows(NotFoundException.class,
                () -> catShelterService.getShelterById(SHELTER_ID_2));
    }

    /**
     * getCatShelterByNameTest / ТЕСТ - получение приют для кошек по имени
     * <p>
     * Проверка на False: Что такого CAT_SHELTER НЕТ
     * Добавление CAT_SHELTER_1 в catShelterRepository
     * когда catShelterRepository находит по имени NAME_CAT_1, то возвращается CAT_SHELTER_1
     * Проверка, получаем CAT_SHELTER_1, при поиске по имени NAME_CAT_1
     */

    @Test
    void getCatShelterByNameTest() {
        assertFalse(catShelterService.getShelter().contains(CAT_SHELTER_1));
        catShelterRepository.save(CAT_SHELTER_1);
        when(catShelterRepository.findByName(NAME_CAT_1)).thenReturn(Optional.of(CAT_SHELTER_1));
        assertEquals(CAT_SHELTER_1, catShelterService.getShelterByName(NAME_CAT_1));
    }

    /**
     * getCatShelterByNameExceptionTest / ТЕСТ - исключение: получение приют для кошек по имени
     * <p>
     * Проверка на False: Что такого CAT_SHELTER НЕТ
     * Добавление CAT_SHELTER_1 в catShelterRepository
     * когда catShelterRepository находит по имени NAME_CAT_1, то возвращается CAT_SHELTER_1
     * Проверка, получаем CAT_SHELTER_1, при поиске по имени NAME_CAT_1
     */
    @Test
    void getCatShelterByNameExceptionTest() {
        assertFalse(catShelterService.getShelter().contains(CAT_SHELTER_1));
        catShelterRepository.save(CAT_SHELTER_1);
        assertThrows(NotFoundException.class,
                () -> catShelterService.getShelterByName(NAME_CAT_2));
    }

    /**
     * getCatShelterTest / ТЕСТ - получение кошачьего приюта
     * <p>
     * В List<CatShelter> передаем CAT_SHELTER_1
     * когда catShelterRepository.findAll, вернуть кошачьи приют
     * Проверка, получаем catShelters, при поиске получение приюта
     */
    @Test
    void getCatShelterTest() {
        List<CatShelter> catShelters = new ArrayList<>();
        catShelters.add(CAT_SHELTER_1);
        when(catShelterRepository.findAll()).thenReturn(catShelters);
        assertEquals(catShelters, catShelterService.getShelter());
    }


    @Test
    void getAnimalTest() {
        List<Cat> catList = new ArrayList<>();
        catList.add(CAT_1);
        catList.add(CAT_2);

        CatShelter CAT_SHELTER1_1 = new CatShelter(catList, 1L, "ABOUT_ME_CAT", "LOCATION", "NAME_CAT_1", "SAFETY", "SECURITY", "WORKING_HOURS");

        when(catShelterRepository.findById(CAT_SHELTER1_1.getId())).thenReturn(Optional.of(CAT_SHELTER1_1));

        List<Cat> catList1 = catShelterService.getAnimal(CAT_SHELTER1_1.getId());

        assertThat(catList1).isEqualTo(catList);


    }

    @Test
    void deleteTest() {
        assertFalse(catShelterService.getShelter().contains(CAT_SHELTER_1));
        when(catShelterRepository.save(CAT_SHELTER_1)).thenReturn(CAT_SHELTER_1);
        assertEquals(CAT_SHELTER_1, catShelterService.create(CAT_SHELTER_1));

        catShelterRepository.delete(CAT_SHELTER_1);

        assertFalse(catShelterRepository.findAll().contains(CAT_SHELTER_1));
    }
}