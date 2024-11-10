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
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Анонсы")));
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Настройки")));

        if (role == "HEADMEN") {
            keyboardRows.add(new KeyboardRow(new KeyboardButton("Дела старост")));
        }

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup headmenMenu() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(new KeyboardRow(new KeyboardButton("Создать новости"), new KeyboardButton("Удалить новость")));

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup newsMenu(int id, int length) {
        List<InlineKeyboardRow> keyboardRows = new ArrayList<>();

        InlineKeyboardButton leftButton = InlineKeyboardButton
                .builder()
                .text("⬅")
                .callbackData("news_"+id+"_previous")
                .build();

        InlineKeyboardButton rightButton = InlineKeyboardButton
                .builder()
                .text("➡")
                .callbackData("news_"+id+"_next")
                .build();

        if (id == 0) {
            keyboardRows.add(new InlineKeyboardRow(rightButton));
        } else if (id == length-1) {
            keyboardRows.add(new InlineKeyboardRow(leftButton));
        } else {
            keyboardRows.add(new InlineKeyboardRow(leftButton, rightButton));
        }

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardRows);

        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup homeworkMenu(int id, int length, boolean isDone) {
        List<InlineKeyboardRow> keyboardRows = new ArrayList<>();

        InlineKeyboardButton leftButton = InlineKeyboardButton
                .builder()
                .text("⬅")
                .callbackData("homework_"+id+"_previous")
                .build();

        InlineKeyboardButton rightButton = InlineKeyboardButton
                .builder()
                .text("➡")
                .callbackData("homework_"+id+"_next")
                .build();

        InlineKeyboardButton doneButton = InlineKeyboardButton
                .builder()
                .text("сделать ✅")
                .callbackData("homework_"+id+"_done")
                .build();

        InlineKeyboardButton undoneButton = InlineKeyboardButton
                .builder()
                .text("отменить ❌")
                .callbackData("homework_"+id+"_undone")
                .build();

        if (id == 0) {
            keyboardRows.add(new InlineKeyboardRow(rightButton));
        } else if (id == length-1) {
            keyboardRows.add(new InlineKeyboardRow(leftButton));
        } else {
            keyboardRows.add(new InlineKeyboardRow(leftButton, rightButton));
        }

        if (isDone) {
            keyboardRows.add(new InlineKeyboardRow(undoneButton));
        } else {
            keyboardRows.add(new InlineKeyboardRow(doneButton));
        }

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardRows);

        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup newsSettingMenu(int id) {
        List<InlineKeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(new InlineKeyboardRow(
                InlineKeyboardButton
                        .builder()
                        .text("удалить")
                        .callbackData("update " + id)
                        .build(),
                InlineKeyboardButton
                        .builder()
                        .text("изменить")
                        .callbackData("update " + id)
                        .build()
        ));

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardRows);

        return keyboardMarkup;
    }
}
