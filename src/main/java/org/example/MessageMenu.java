package org.example;

import org.example.dto.Subject.GetterSubject;
import org.example.dto.Subject.Subject;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MessageMenu {
    public static InlineKeyboardMarkup newsMenu(int id, int length) {
        if (length <= 1) {
            return null;
        }

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

    public static InlineKeyboardMarkup deleteButton(String type,int id) {
        List<InlineKeyboardRow> keyboardRows = new ArrayList<>() {};

        InlineKeyboardButton deleteButton = InlineKeyboardButton
                .builder()
                .text("удалить")
                .callbackData(type + '_' + id + "_delete")
                .build();

        keyboardRows.add(new InlineKeyboardRow(deleteButton));

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardRows);

        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup subject() {
        List<InlineKeyboardRow> keyboardRows = new ArrayList<>() {};

        Subject[] subjects = GetterSubject.getAllSubjects();

        for (Subject subject:subjects){
            keyboardRows.add(new InlineKeyboardRow(
                    InlineKeyboardButton
                            .builder()
                            .text(subject.getAbbreviation())
                            .callbackData("subject_" + subject.getId() + "_choose")
                            .build()
            ));
        }

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardRows);

        return keyboardMarkup;
    }

//    public static InlineKeyboardMarkup newsSettingMenu(int id) {
//        List<InlineKeyboardRow> keyboardRows = new ArrayList<>();
//        keyboardRows.add(new InlineKeyboardRow(
//                InlineKeyboardButton
//                        .builder()
//                        .text("удалить")
//                        .callbackData("update " + id)
//                        .build(),
//                InlineKeyboardButton
//                        .builder()
//                        .text("изменить")
//                        .callbackData("update " + id)
//                        .build()
//        ));
//
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardRows);
//
//        return keyboardMarkup;
//    }
}
