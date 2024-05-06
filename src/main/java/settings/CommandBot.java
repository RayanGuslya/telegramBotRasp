package settings;

import org.apache.commons.logging.Log;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.w3c.dom.UserDataHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLClientInfoException;
import java.sql.Statement;
import java.util.*;
import java.util.List;


@Component
public class CommandBot extends TelegramLongPollingBot {
    ReplyKeyboardMarkup keyboardMarkup;
    Map<Long, UserState> userStates = new HashMap<>();//2.хранит состояние диалога

    Map<Long, PanelState> PanelStates = new HashMap<>();//2.P хранит состояние диалога

    // Аннотация @Value позволяет задавать значение полю путем считывания из application.yaml
//    @Value("${bot.name}")
//    private String botUsername;
//
//    @Value("${bot.token}")
//    private String botToken;

    private sendRasp sendRasp = new sendRasp();
    @Override
    public String getBotUsername() {
        return "имя бота";
    }

    @Override
    public String getBotToken() {
        return "ключ бота";
    }
    boolean iksCheck = false;
    boolean gnsCheck = false;
    boolean mssCheck = false;
    boolean fcCheck = false;
    @Override
    public void onUpdateReceived(Update update) {


        database database = new database();
        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage message = new SendMessage();
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            message.setChatId(chatId);

            UserState userState = userStates.get(chatId);
            PanelState panelState = PanelStates.get(chatId);
            if (panelState != null && panelState.getWaiting()){
                if(!iksCheck){
                System.out.println("введеный текст " + messageText);
                sendRasp.setIKS(messageText);
                System.out.println("сылка: " + sendRasp.getIKS());
                panelState.setWaiting(false);
                System.out.println("сылка2: " + sendRasp.getIKS());
                iksCheck = false;
            }
                if(!mssCheck){
                    System.out.println("введеный текст " + messageText);
                    sendRasp.setMSS(messageText);
                    System.out.println("сылка: " + sendRasp.getMSS());
                    panelState.setWaiting(false);
                    System.out.println("сылка2: " + sendRasp.getMSS());
                    mssCheck = false;
                }
                if(!fcCheck){
                    System.out.println("введеный текст " + messageText);
                    sendRasp.setFC(messageText);
                    System.out.println("сылка: " + sendRasp.getFC());
                    panelState.setWaiting(false);
                    System.out.println("сылка2: " + sendRasp.getFC());
                    fcCheck = false;
                }

                if (!gnsCheck){
                    System.out.println("введеный текст " + messageText);
                    sendRasp.setGNS(messageText);
                    System.out.println("сылка: " + sendRasp.getGNS());
                    panelState.setWaiting(false);
                    System.out.println("сылка2: " + sendRasp.getGNS());
                    gnsCheck = false;
                }

            }


            if (userState != null && userState.getWaitingPassword()) {
                //4.Обрабатываем введенный пароль
                if (messageText.equals("12345")) {
                    SendText(chatId, "Пароль верный! Доступ разрешен.");
                     InlineKeyboardUpdateRasp(chatId);
                    // Здесь можно выполнить действия, связанные с админ панелью

                    /** нужно исправить ввод пароля(что бы не повторялся)*/
                    panelState = PanelStates.get(chatId);
                    if (panelState == null) {
                        panelState = new PanelState();
                        PanelStates.put(chatId, panelState);

                    }
                    panelState.setWaiting(true);
                    userState.setWaitingPassword(false); // Устанавливаем состояние ожидания ввода пароля в false
                } else {
                    SendText(chatId, "Неверный пароль! Попробуйте еще раз.");
                }
            }
            System.out.println("введеный текст2 " + messageText);

            String name = update.getMessage().getChat().getUserName();
            String firstname = update.getMessage().getChat().getFirstName();

            /**
             сделать поиск имени по ид
             */
            /*
            int numId= 3;
            String a = database.getUsernameById(35);

            if (a != null) {
                    System.out.println("Имя пользователя: " + a);
                    SendText(chatId, "Имя пользователя: " + a);
            } else {

                System.out.println("Пользователь не найден");
                SendText(chatId, "Пользователь не найден");
            }
            */

            switch (messageText) {


                case "/start":
                    //SendText(chatId, "Бот запущен");
                    database.settingsDatabase("ALTER TABLE usersbot AUTO_INCREMENT = 1");

                    database.settingsDatabase("INSERT INTO usersbot (name, chatID, FirstName) value ('" + name + "', "
                            + chatId + ", '" + firstname + "');");

                    //database.settingsDatabase("DELETE FROM usersbot");

                    MainKeyboard(message);
                    break;
                case "/panel":
                    //3.отправление запроса на ввод пароля
                    userState = userStates.get(chatId);
                    if (userState == null) {
                        userState = new UserState();
                        userStates.put(chatId, userState);

                    }
                    userState.setWaitingPassword(true);
                    SendText(chatId, "Вход в админ панель. Введите пароль:");
                    break;
                case "Расписание пар":
                    InlineKeyboard(chatId);
                    break;
                case "Расписание звонков":
                    sendPhoto(chatId, sendRasp.getCALL(), "расписание звонков");
                    break;
                default:
                    break;
            }
        } else if (update.hasCallbackQuery()) {
            if (update.hasCallbackQuery()) {
                SendMessage message = new SendMessage();

                CallbackQuery callbackQuery = update.getCallbackQuery();
                String callbackData = callbackQuery.getData();
                long chatId = callbackQuery.getMessage().getChatId();

                message.setChatId(chatId);


                switch (callbackData) {
                    case "IKS_BUTTON":
                        if (sendRasp.getIKS() != "") {
                            sendPhoto(chatId, sendRasp.getIKS(), "расписание ИКС");
                        } else
                            SendText(chatId, "расписания нету");
                        break;
                    case "GNS_BUTTON":
                        if (sendRasp.getGNS() != "") {
                            sendPhoto(chatId, sendRasp.getGNS(), "расписание ГНС");
                        } else
                            SendText(chatId, "расписания нету");
                        break;
                    case "MSS_BUTTON":
                        if (sendRasp.getMSS() != "") {
                            sendPhoto(chatId, sendRasp.getMSS(), "расписание МСС");
                        } else
                            SendText(chatId, "расписания нету");
                        break;
                    case "FC_BUTTON":
                        if (sendRasp.getFC() != "") {
                            sendPhoto(chatId, sendRasp.getFC(), "расписание ФЭС");
                        } else
                            SendText(chatId, "расписания нету");
                }
                switch (callbackData){
                    case "IKS_BUTTON_UPDATE":
                        SendText(chatId, "Отправте ссылку на новое изображение икс");
                        iksCheck = true;
                        break;
                    case "GNS_BUTTON_UPDATE":
                        SendText(chatId, "Отправте ссылку на новое изображение гнс");
                        gnsCheck = true;
                        break;
                    case "MSS_BUTTON_UPDATE":
                        SendText(chatId, "Отправте ссылку на новое изображение мсс");
                        mssCheck = true;
                        break;
                    case "FC_BUTTON_UPDATE":
                        SendText(chatId, "Отправте ссылку на новое изображение фэс");
                        fcCheck = true;
                        break;
                }
            }
        }
    }
    /*
    public String getText(Update update){
        if (update.hasMessage() && update.getMessage().hasText())
        {
            String messageText = update.getMessage().getText();
            return messageText;
        }
        return "error";
    }
    */
    public void InlineKeyboardUpdateRasp(long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите отделение где хотите изменить расписание.");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowUpLine = new ArrayList<>();
        List<InlineKeyboardButton> rowDownLine = new ArrayList<>();

        var IKS_BUTTON = new InlineKeyboardButton();
        IKS_BUTTON.setText("ИКС");
        IKS_BUTTON.setCallbackData("IKS_BUTTON_UPDATE");

        var GNS_BUTTON = new InlineKeyboardButton();
        GNS_BUTTON.setText("ГНС");
        GNS_BUTTON.setCallbackData("GNS_BUTTON_UPDATE");

        var MSS_BUTTON = new InlineKeyboardButton();
        MSS_BUTTON.setText("МСС");
        MSS_BUTTON.setCallbackData("MSS_BUTTON_UPDATE");

        var FC_BUTTON = new InlineKeyboardButton();
        FC_BUTTON.setText("ФЭС");
        FC_BUTTON.setCallbackData("FC_BUTTON_UPDATE");

        rowUpLine.add(IKS_BUTTON);
        rowUpLine.add(MSS_BUTTON);
        rowDownLine.add(FC_BUTTON);
        rowDownLine.add(GNS_BUTTON);

        rowsInLine.add(rowUpLine);
        rowsInLine.add(rowDownLine);

        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void InlineKeyboard(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выбор вашего отделения, чтобы увидеть расписание");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowUpLine = new ArrayList<>();
        List<InlineKeyboardButton> rowDownLine = new ArrayList<>();

        var IKS_BUTTON = new InlineKeyboardButton();
        IKS_BUTTON.setText("ИКС");
        IKS_BUTTON.setCallbackData("IKS_BUTTON");

        var GNS_BUTTON = new InlineKeyboardButton();
        GNS_BUTTON.setText("ГНС");
        GNS_BUTTON.setCallbackData("GNS_BUTTON");

        var MSS_BUTTON = new InlineKeyboardButton();
        MSS_BUTTON.setText("МСС");
        MSS_BUTTON.setCallbackData("MSS_BUTTON");

        var FC_BUTTON = new InlineKeyboardButton();
        FC_BUTTON.setText("ФЭС");
        FC_BUTTON.setCallbackData("FC_BUTTON");

        rowUpLine.add(IKS_BUTTON);
        rowUpLine.add(MSS_BUTTON);
        rowDownLine.add(FC_BUTTON);
        rowDownLine.add(GNS_BUTTON);

        rowsInLine.add(rowUpLine);
        rowsInLine.add(rowDownLine);

        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void SendText(long chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendPhoto(long chatId, String imagePath,String caption) {
        SendMessage message = new SendMessage();
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setCaption(caption);
        message.setChatId(chatId);
        sendPhoto.setChatId(message.getChatId());

        System.out.println("caption " + imagePath);

        InputFile inputFile = new InputFile(imagePath);
        sendPhoto.setPhoto(inputFile);

        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("erron send Photo");
        }
    }

    public void MainKeyboard(SendMessage sendMessage){
        sendMessage.setText("Бот запущен");
        keyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(keyboardMarkup);

        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Расписание пар"));
        row.add(new KeyboardButton("Расписание звонков"));
        keyboardRowList.add(row);

        keyboardMarkup.setKeyboard(keyboardRowList);

        try
        {
            execute(sendMessage);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    /*
    public void ReplyKeyBoardBack(SendMessage sendMessage, boolean setOneTimeKeyboardBack)
    {
        sendMessage.setText("sendMainKeyboard");
        keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(setOneTimeKeyboardBack);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Вернутся назад");
        keyboardRowList.add(row);

        sendMessage.setReplyMarkup(keyboardMarkup);
        keyboardMarkup.setKeyboard(keyboardRowList);

        try{
            execute(sendMessage);
        }catch(TelegramApiException e){
            e.printStackTrace();
        }

    }
     */
}
