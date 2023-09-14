package org.ReydelBot.controller;

import lombok.AllArgsConstructor;
import org.ReydelBot.config.botConfig;
import org.ReydelBot.model.CarModel;
import org.ReydelBot.service.CarService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
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
                case "Бренды":
                    startCommandReceivedList(chatId, update.getMessage().getChat().getFirstName());

                    break;
                case "Фото":
                    //codes
                    startCommandReceivedImages(chatId);

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

    private void startCommandReceivedImages(Long chatId){
        try{
            sendPhoto(chatId);
        }catch (Exception e){}
    }
    private void sendPhoto(Long chatId){
        SendPhoto sendPhoto = new SendPhoto();
        File file = new File("src/main/resources/images/mercedes.jpg");
        InputFile inputFile = new InputFile(file);

        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setCaption("photo");

        try{
            execute(sendPhoto);
        }catch (Exception e){}
    }
    private void startCommandReceivedList(Long chatId, String name){
        //brands list
        String answer = "";
        try {
            for (CarModel car : CarService.getCarList()){
                answer += car.getCarBrand();
                answer += " ";
            }
        }catch (Exception e){}

        sendMessage(chatId, answer);
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

        KeyboardRow brands = new KeyboardRow();
        KeyboardRow photo = new KeyboardRow();

        brands.add("Бренды");
        photo.add("Фото");

        keyboardRows.add(brands);
        keyboardRows.add(photo);

        keyboardMarkup.setKeyboard(keyboardRows);

        sendMessage.setReplyMarkup(keyboardMarkup);

        try{
            execute(sendMessage);
        }catch (TelegramApiException e){

        }
    }

}
