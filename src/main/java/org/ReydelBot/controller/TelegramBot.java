package org.ReydelBot.controller;

import lombok.AllArgsConstructor;
import org.ReydelBot.config.BotConfig;
import org.ReydelBot.model.CarModel;
import org.ReydelBot.service.CarService;
import org.ReydelBot.service.KeyBoardService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final KeyBoardService keyBoardService;

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

        if(update.hasMessage() && update.getMessage().hasText()){
            //user chatId and chatText
            String messageText = update.getMessage().getText();
            String userName = update.getMessage().getChat().getFirstName();

            long chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    startCaseCommand(chatId);

                    break;
                case "Все бренды":
                    brandListCommand(chatId);

                    break;
                default:
                    String carStr = " ";
                    CarModel carModel = new CarModel();

                    try{
                        carStr = CarService.getCarInfo(messageText, carModel);
                    } catch (IOException e){
                        sendMessage(chatId, "Такого автомобиля в базе нет." + "\n" +
                                "Повторите попытку.");
                    } catch (ParseException e){
                        throw new RuntimeException("Exception");
                    }
                    messageCarInfo(chatId, carStr, messageText);
            }
        }
    }

    private void brandListCommand(Long chatId){

        //brands list
        String answer = "Вам доступны автомобили следующих брендов: " + "\n";

        try {
            for (CarModel car : CarService.getCarList()){
                answer += car.getCarId() + ". " + car.getCarBrand() + "\n";
            }
        }catch (Exception e){}

        sendMessage(chatId, answer);
    }

    private void startCaseCommand(Long chatId){
        String answer = "Добро пожаловать в библиотеку автомобилей!" + "\n"
                + "Доступные команды: " + "\n"
                + "1.Все бренды"+ "\n"
                + "2.Mercedes"+ "\n"
                + "3.Bmw"+ "\n"
                + "4.Audi";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend){

        SendMessage sendMessage = new SendMessage();
        ReplyKeyboardMarkup keyboardMarkup = keyBoardService.getKeyBoard();

        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(keyboardMarkup);
        sendMessage.setText(textToSend);

        try{
            execute(sendMessage);
        }catch (Exception e){}
    
    }

    private void messageCarInfo(Long chatId, String textToSend, String imageId){

        SendPhoto sendPhoto = new SendPhoto();
        File file = new File("src/main/resources/images/"+ imageId + ".jpg");
        InputFile inputFile = new InputFile(file);

        ReplyKeyboardMarkup keyboardMarkup = keyBoardService.getKeyBoard();

        //sendPhoto init
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setCaption(textToSend);
        sendPhoto.setReplyMarkup(keyboardMarkup);

        try{
            execute(sendPhoto);
        }catch (TelegramApiException e){}

    }

}
