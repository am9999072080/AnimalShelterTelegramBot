package Telegram.Bot.AnimalShelter.listener;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.User;
import Telegram.Bot.AnimalShelter.entity.Volunteer;
import Telegram.Bot.AnimalShelter.menu_constructor.MenuConstructor;
import Telegram.Bot.AnimalShelter.repository.UserRepository;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import Telegram.Bot.AnimalShelter.service.ReportService;
import Telegram.Bot.AnimalShelter.service.UserService;
import Telegram.Bot.AnimalShelter.service.VolunteerService;
import Telegram.Bot.AnimalShelter.service.impl.CatShelterServiceImpl;
import Telegram.Bot.AnimalShelter.service.impl.DogShelterServiceImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class TelegramBotUpdatesListenerFunctions {

    private final TelegramBot telegramBot;
    private final UserService userService;
    private final CatShelterServiceImpl catShelterService;
    private final DogShelterServiceImpl dogShelterService;
    private final VolunteerService volunteerService;
    private final AdaptationService adaptationService;
    private final ReportService reportService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    public void sendReportPhotoToVolunteer(Long reportId, Long volunteerId) {
        GetFile request = new GetFile(reportService.getById(reportId).getPhotoId());
        GetFileResponse getFileResponse = telegramBot.execute(request);
        Adaptation adaptation = adaptationService.getById(reportService.getById(reportId).getAdaptationPeriodId());
        if (getFileResponse.isOk()) {
            try {
                byte[] image = telegramBot.getFileContent(getFileResponse.file());
                SendPhoto sendPhoto = new SendPhoto(volunteerId, image);
                sendPhoto.caption("Id Owner: " + adaptation.getOwnerId() + "\n" +
                        "Id Adaptation period: " + adaptation.getId() + "\n" +
                        "Id Report:" + reportId);
                telegramBot.execute(sendPhoto);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    void sendMessage(Long chatId, String message) {
        SendResponse sendResponse = telegramBot.execute(new SendMessage(chatId, message));
        if (!sendResponse.isOk()) {
            logger.error(sendResponse.description());
        }
    }

    void getContact(Long chatId, String text) {
        Pattern pattern = Pattern.compile("^(\\d{11})$");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            User byId = userService.getById(chatId);
            byId.setPhoneNumber(matcher.group(1));
            userService.update(byId);
            sendMessage(chatId, "Phone number accepted");
        } else {
            sendMessage(chatId, "Incorrect input");
        }
        logger.info("Пришел телефон от пользователя - ID:{} тел:{} ", chatId, text);
    }

    void sendMessageToVolunteers(Message message) {
        Long chatId = message.chat().id();
        Integer integer = message.messageId();
        for (Volunteer volunteer : volunteerService.getAll()) {
            telegramBot.execute(new ForwardMessage(volunteer.getTelegramId(), chatId, integer));
        }
    }

    void sendMessageToVolunteers(Long chatId) {
        for (Volunteer volunteer : volunteerService.getAll()) {
            telegramBot.execute(new SendMessage(volunteer.getTelegramId(), "The owner of the animal with id " + chatId +
                    "has not sent reports for more than two days!"));
        }
    }

    void sendReportExample(Long chatId) {
        try {
            byte[] photo = Files.readAllBytes(
                    Paths.get(Objects.requireNonNull(UpdatesListener.class.getResource("/img/cat.jpg")).toURI()));
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            sendPhoto.caption("Ration:_______; \n" +
                    "Overall health:_______; \n" +
                    "Behavior:_______;\n");
            telegramBot.execute(sendPhoto);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
    }

    void getReport(Message message) {
        PhotoSize photoSize = message.photo()[message.photo().length - 1];
        String caption = message.caption();
        Long chatId = message.chat().id();
        try {
            reportService.createWithTelegramData(photoSize.fileId(), caption, chatId);
            sendMessage(chatId, "Your report has been accepted.");
        } catch (Exception e) {
            sendMessage(chatId, e.getMessage());
        }
    }

    void sendMenuStage(String shelterType, Long chatId) {
        logger.info("Вызвано меню выбора приюта - ID:{}", chatId);
        MenuConstructor menuConstructor = new MenuConstructor(telegramBot, catShelterService, dogShelterService);
        User user = userService.getById(chatId);
        user.setShelterType(shelterType);
        userService.update(user);
        menuConstructor.sendMenuStage(chatId);
    }

    String getStringFromList(List<?> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(o -> sb.append(o)
                .append("\n")
                .append("============").append("\n"));
        return sb.toString();
    }

}
