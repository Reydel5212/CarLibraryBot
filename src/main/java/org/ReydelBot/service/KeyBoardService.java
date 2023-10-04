package org.ReydelBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyBoardService {
    public ReplyKeyboardMarkup getKeyBoard(){

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow brands = new KeyboardRow();
        KeyboardRow downRow = new KeyboardRow();

        brands.add("Mercedes");
        brands.add("BMW");
        brands.add("Audi");

        downRow.add("Все бренды");

        keyboardRows.add(brands);
        keyboardRows.add(downRow);

        keyboardMarkup.setKeyboard(keyboardRows);

        return keyboardMarkup;
    }
}
