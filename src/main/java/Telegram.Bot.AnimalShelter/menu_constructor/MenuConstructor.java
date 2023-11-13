package Telegram.Bot.AnimalShelter.menu_constructor;

import Telegram.Bot.AnimalShelter.buttons.Constants;
import Telegram.Bot.AnimalShelter.entity.CatShelter;
import Telegram.Bot.AnimalShelter.entity.DogShelter;
import Telegram.Bot.AnimalShelter.service.impl.CatShelterServiceImpl;
import Telegram.Bot.AnimalShelter.service.impl.DogShelterServiceImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class MenuConstructor {

    private final TelegramBot telegramBot;
    private final CatShelterServiceImpl catShelterService;
    private final DogShelterServiceImpl dogShelterService;
    private ResourceBundle messagesBundle = ResourceBundle.getBundle("messages");


    public void sendStartMenu(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(Constants.CAT_SHELTER),
                new KeyboardButton(Constants.DOG_SHELTER));
        replyKeyboardMarkup.addRow(new KeyboardButton(Constants.CALL_VOLUNTEER),
                new KeyboardButton(Constants.SEND_REPORT));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, messagesBundle.getString("WELCOME"));
    }


    public void sendMenuStage(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(Constants.INFORMATION_ABOUT_SHELTER),
                new KeyboardButton(Constants.FAQ),
                new KeyboardButton(Constants.SEND_REPORT));
        replyKeyboardMarkup.addRow(new KeyboardButton(Constants.CALL_VOLUNTEER));
        replyKeyboardMarkup.addRow(new KeyboardButton(Constants.MAIN_MENU));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Please choose an option");
    }

    public void sendListMenuCat(long chatId) {
        List<CatShelter> shelters = catShelterService.getShelter();
        List<KeyboardButton> buttons = new ArrayList<>();
        shelters.forEach(shelter -> buttons.add(new KeyboardButton(shelter.getName())));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(buttons.toArray(new KeyboardButton[0]));
        replyKeyboardMarkup.addRow(new KeyboardButton(Constants.MAIN_MENU));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "List of cat shelters");
    }

    public void sendListMenuDog(long chatId) {
        List<DogShelter> shelters = dogShelterService.getShelter();
        List<KeyboardButton> buttons = new ArrayList<>();
        shelters.forEach(shelter -> buttons.add(new KeyboardButton(shelter.getName())));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(buttons.toArray(new KeyboardButton[0]));
        replyKeyboardMarkup.addRow(new KeyboardButton(Constants.MAIN_MENU));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "List of dog shelters");
    }

    public void sendMenuCat(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(Constants.WORKING_HOURS),
                new KeyboardButton(Constants.LIST_OF_CATS),
                new KeyboardButton(Constants.ABOUT_SPECIFIC_SHELTER));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.SAFETY_POLICY_RECOMMENDATIONS),
                new KeyboardButton(Constants.CONTACT_DETAILS),
                new KeyboardButton(Constants.CONTACT_SECURITY));
        replyKeyboardMarkup.addRow(new KeyboardButton(Constants.CALL_VOLUNTEER));
        replyKeyboardMarkup.addRow(new KeyboardButton(Constants.MAIN_MENU));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "About shelter");
    }

    public void sendMenuDog(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(Constants.WORKING_HOURS),
                new KeyboardButton(Constants.LIST_OF_DOGS),
                new KeyboardButton(Constants.ABOUT_SPECIFIC_SHELTER));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.SAFETY_POLICY_RECOMMENDATIONS),
                new KeyboardButton(Constants.CONTACT_DETAILS),
                new KeyboardButton(Constants.CONTACT_SECURITY));
        replyKeyboardMarkup.addRow(new KeyboardButton(Constants.CALL_VOLUNTEER));
        replyKeyboardMarkup.addRow(new KeyboardButton(Constants.MAIN_MENU));

        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "About shelter");
    }

    public void menuCat(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(Constants.REQUIRED_DOCUMENTS),
                new KeyboardButton(Constants.LIST_OF_REASONS)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.RECOMMENDATIONS_FOR_CATS),
                new KeyboardButton(Constants.CALL_VOLUNTEER),
                new KeyboardButton(Constants.CONTACT_DETAILS)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.MAIN_MENU)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "About cats");
    }

    public void menuDog(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(Constants.REQUIRED_DOCUMENTS),
                new KeyboardButton(Constants.LIST_OF_REASONS)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.RECOMMENDATIONS_FOR_DOGS),
                new KeyboardButton(Constants.CALL_VOLUNTEER),
                new KeyboardButton(Constants.CONTACT_DETAILS)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.MAIN_MENU)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "About dogs");
    }

    public void rulesForDogs(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(Constants.RULES_FOR_DATING_A_DOG),
                new KeyboardButton(Constants.DOG_TRANSPORTATION),
                new KeyboardButton(Constants.PUPPY_SETUP)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.ADULT_DOG_SETUP),
                new KeyboardButton(Constants.SETUP_FOR_DOG_WITH_DISABILITIES)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.DOG_HANDLERS_ADVICE),
                new KeyboardButton(Constants.PROVEN_DOG_HANDLERS));
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.BACK_TO_ALL_ABOUT_DOGS)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, Constants.RECOMMENDATIONS_FOR_DOGS);
    }

    public void rulesForCats(long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(Constants.RULES_FOR_DATING_A_CAT),
                new KeyboardButton(Constants.CAT_TRANSPORTATION)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.ADULT_CAT_SETUP),
                new KeyboardButton(Constants.KITTEN_SETUP),
                new KeyboardButton(Constants.SETUP_FOR_CAT_WITH_DISABILITIES)
        );
        replyKeyboardMarkup.addRow(
                new KeyboardButton(Constants.BACK_TO_ALL_ABOUT_CATS)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, Constants.RECOMMENDATIONS_FOR_CATS);
    }

    public void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        telegramBot.execute(request);
    }
}
