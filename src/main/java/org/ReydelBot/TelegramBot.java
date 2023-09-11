package org.ReydelBot;

import lombok.AllArgsConstructor;
import org.ReydelBot.config.botConfig;
import org.ReydelBot.model.CarModel;
import org.ReydelBot.service.CarService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final botConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }
    @Override
    public void onUpdateReceived(Update update) {
        CarModel carModel = new CarModel();
        String carStr = "";

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            //Keyboard button
            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());

                    break;
                default:
                    try{
                        carStr = CarService.getCarInfo(messageText, carModel);
                    } catch (IOException e){
                        sendMessage(chatId, "Такого нет." + "\n" +
                                "Введите код автомобиля." + "\n" +
                                "Пример: 1");
                    } catch (ParseException e){
                        throw new RuntimeException("Unable to parse date");
                    }
                    sendMessage(chatId, carStr);
            }
        }
    }
    private void startCommandReceived(Long chatId, String name){
        String answer = "Добрый день " + name + "\n" +
                "Введите код автомобиля: " + "\n" +
                "Пример: 1";
        sendMessage(chatId, answer);
    }
    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        row1.add("1");
        row2.add("2");
        row3.add("3");

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        keyboardMarkup.setKeyboard(keyboardRows);

        sendMessage.setReplyMarkup(keyboardMarkup);

        try{
            execute(sendMessage);
        }catch (TelegramApiException e){

        }
    }

}
