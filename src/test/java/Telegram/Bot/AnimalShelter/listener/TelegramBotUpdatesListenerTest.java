package Telegram.Bot.AnimalShelter.listener;

import Telegram.Bot.AnimalShelter.buttons.Constants;
import Telegram.Bot.AnimalShelter.entity.*;
import Telegram.Bot.AnimalShelter.repository.UserRepository;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import Telegram.Bot.AnimalShelter.service.ReportService;
import Telegram.Bot.AnimalShelter.service.UserService;
import Telegram.Bot.AnimalShelter.service.VolunteerService;
import Telegram.Bot.AnimalShelter.service.impl.CatShelterServiceImpl;
import Telegram.Bot.AnimalShelter.service.impl.DogShelterServiceImpl;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {
    @Mock
    TelegramBot telegramBot;
    @Mock
    UserService userService;
    @Mock
    CatShelterServiceImpl catShelterService;
    @Mock
    DogShelterServiceImpl dogShelterService;
    @Mock
    VolunteerService volunteerService;
    @Mock
    ReportService reportService;
    @Mock
    AdaptationService adaptationService;
    @Mock
    UserRepository userRepository;
    @Mock
    TelegramBotUpdatesListenerFunctions telegramBotUpdatesListenerFunctions;
    @InjectMocks
    TelegramBotUpdatesListener telegramBotUpdatesListener;

    private ResourceBundle messagesBundle = ResourceBundle.getBundle("messages");

    private final SendResponse sendResponse = BotUtils.fromJson("{ok: true}", SendResponse.class);
    private final GetFileResponse getFileResponse = BotUtils.fromJson("{ok : true, file : {file_id: lalala}}", GetFileResponse.class);

    private final String messageTextJson = Files.readString(
            Path.of(Objects.requireNonNull(TelegramBotUpdatesListenerTest.class.getResource("/message_update.json")).toURI()));

    private final byte[] photo = Files.readAllBytes(
            Paths.get(Objects.requireNonNull(UpdatesListener.class.getResource("/img/cat.jpg")).toURI()));


    private final User catUser = new User(1L, "CatName", "", "", "CAT", "ShelterName");
    private final User dogUser = new User(1L, "CatName", "", "", "DOG", "ShelterName");
    private final CatShelter catShelter = new CatShelter(1L, "ShelterName", "location",
            "timetable", "about me", "security", "safety advice");
    private final DogShelter dogShelter = new DogShelter(1L, "ShelterName", "location",
            "timetable", "about me", "security", "safety advice");
    private final Cat cat = new Cat(1L, "CatName", 2, true, true, null, 1L);
    private final Dog dog = new Dog(1L, "DogName", 2, true, true, null, 1L);
    private final Report report = new Report(1L, 1L, LocalDate.now(), "lalala", "ration", "health", "behavior");
    private final Adaptation adaptation;

    {
        adaptation = new Adaptation(List.of(report), 1L, 1L, 1L,
                Adaptation.AnimalType.CAT, LocalDate.now(), LocalDate.now().plusDays(30),
                LocalDate.now().minusDays(1), Adaptation.Result.IN_PROGRESS);
    }

    TelegramBotUpdatesListenerTest() throws IOException, URISyntaxException {
    }

    private SendMessage getSendMessage(Update update) {
        when(telegramBot.execute(any())).thenReturn(sendResponse);
        telegramBotUpdatesListener.process(Collections.singletonList(update));
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        Mockito.reset(telegramBot);
        return argumentCaptor.getValue();
    }

    private SendMessage testerForCatShelter(Update update) {
        when(userService.getById(any())).thenReturn(catUser);
        when(catShelterService.getShelterByName(any())).thenReturn(catShelter);
        return getSendMessage(update);
    }

    private SendMessage testerForDogShelter(Update update) {
        when(userService.getById(any())).thenReturn(dogUser);
        when(dogShelterService.getShelterByName(any())).thenReturn(dogShelter);
        return getSendMessage(update);
    }

    @Test
    void handleRecommendationsForAnimalCommands() {
        when(userService.getById(any())).thenReturn(catUser);
        Update update = BotUtils.fromJson(messageTextJson.replace("%text%", "About dogs"), Update.class);
        SendMessage actual = getSendMessage(update);
        Assertions.assertEquals(update.message().chat().id(), actual.getParameters().get("chat_id"));
        Assertions.assertEquals("About dogs", actual.getParameters().get("text"));
        update = BotUtils.fromJson(messageTextJson.replace("%text%", "About cats"), Update.class);
        actual = getSendMessage(update);
        Assertions.assertEquals(update.message().chat().id(), actual.getParameters().get("chat_id"));
        Assertions.assertEquals("About cats", actual.getParameters().get("text"));
    }

}


