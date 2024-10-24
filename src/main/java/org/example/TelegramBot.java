package org.example;

import com.google.gson.Gson;
import okhttp3.*;
import org.example.dto.News;
import org.example.dto.Subject;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private TelegramClient telegramClient = new OkHttpTelegramClient("8195689343:AAE0vkGTCjZ3JsB_orV9in8fW_b2xygM1QA");

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            switch (message_text) {
                case "allSubject":
                    try {
                        Subject[] subjects = new Subject().getAllSubjects();

                        for (int i = 0; i < subjects.length; i++) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(chat_id)
                                    .text(subjects[i].toString())
                                    .build();

                            telegramClient.execute(message);
                        }
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "allNews":
                    try {
                        News[] news = new News().getAllNews();

                        for (int i = 0; i < news.length; i++) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(chat_id)
                                    .text(news[i].toString())
                                    .build();

                            telegramClient.execute(message);
                        }
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "postNews":
                    try {
                        News news = new News("testBot","1488").postNews();

                        SendMessage message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text(news.toString())
                                .build();
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "deleteNews":
                    try {
                        News news = new News("testBot","1488").postNews();

                        SendMessage message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text(news.toString())
                                .build();
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }
}
