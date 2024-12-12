package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    public static ReplyKeyboardMarkup mainMenu(String role) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Новости"), new KeyboardButton("Учеба")));
//        keyboardRows.add(new KeyboardRow(new KeyboardButton("Анонсы")));
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Настройки")));
        if (role.equals("HEADMAN")) {
            keyboardRows.add(new KeyboardRow(new KeyboardButton("Дела старост")));
        }

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup headmenMenu() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Новости"), new KeyboardButton("Создать новость")));
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Учеба"), new KeyboardButton("Создать ДЗ")));
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Назад")));

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup settings() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Запросить доступ")));
//        keyboardRows.add(new KeyboardRow(new KeyboardButton("Учеба"), new KeyboardButton("Создать ДЗ")));
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Назад")));

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }
}
