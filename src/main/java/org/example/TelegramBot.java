package org.example;

import org.example.dto.News;
import org.example.dto.Subject;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private TelegramClient telegramClient = new OkHttpTelegramClient("8195689343:AAE0vkGTCjZ3JsB_orV9in8fW_b2xygM1QA");
    Activity activity = Activity.Main;

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();


            switch (activity) {
                case Main:
                    System.out.println("asd1");
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
                                SendMessage message = SendMessage
                                        .builder()
                                        .chatId(chat_id)
                                        .text("Введите данные в формате:\ntitle\ncontent")
                                        .build();
                                telegramClient.execute(message);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                            activity = Activity.PostNews;
                            break;
                        case "deleteNews":
                            try {
                                SendMessage message = SendMessage
                                        .builder()
                                        .chatId(chat_id)
                                        .text("Введите id новости")
                                        .build();
                                telegramClient.execute(message);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                            activity = Activity.DeleteNews;
                            break;
                    }
                    break;
                case DeleteNews:
                    try {
                        try {
                            int a = Integer.parseInt(message_text);
                            News news = new News().deleteNews(a);

                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(chat_id)
                                    .text(news.toString())
                                    .build();
                            telegramClient.execute(message);
                            activity = Activity.Main;
                        } catch (NumberFormatException e) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(chat_id)
                                    .text("введите корректный id")
                                    .build();
                            telegramClient.execute(message);
                        }
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case PostNews:
                    try {
                        String[] all =  message_text.split("\n");
                        News news = new News(all[0], all[1]).postNews();

                        SendMessage message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text(news.toString())
                                .build();
                        telegramClient.execute(message);
                        activity = Activity.Main;
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }
}
