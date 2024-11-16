package org.example;

import org.example.dto.User.CreatorUser;
import org.example.dto.User.GetterUser;
import org.example.dto.Homework.GetterHomework;
import org.example.dto.Homework.Homework;
import org.example.dto.News.*;
import org.example.dto.User.User;
import org.example.dto.User.UserContent;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private TelegramClient telegramClient = new OkHttpTelegramClient("8195689343:AAE0vkGTCjZ3JsB_orV9in8fW_b2xygM1QA");

    Activity activity = Activity.Main;
    private int a = 0;

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            System.out.println(chat_id);
            System.out.println(message_text);

            if (message_text.equals("/start")) {
                User current_user = GetterUser.getUserByChatId(chat_id);
                if (current_user == null) {
                    UserContent userContent = new UserContent(chat_id);
                    current_user = CreatorUser.createUser(userContent);
                }
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text(message_text)
                        .replyMarkup(Menu.mainMenu(current_user.getRole()))
                        .build();
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            else if (message_text.equalsIgnoreCase("новоститест")) {
                News[] news = GetterNews.getAllNews();
                SendMessage message;
                if (news.length > 0) {
                    message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text(news[0].format())
                            .replyMarkup(Menu.newsMenu(0, news.length))
                            .build();
                    message.enableMarkdown(true);
                } else {
                    message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text("Пока новостей нет(")
                            .replyMarkup(InlineKeyboardMarkup.builder().clearKeyboard().build())
                            .build();
                }
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            else if (message_text.equalsIgnoreCase("ДЗтест")) {
                Homework[] homeworks = GetterHomework.getAllHomeworks();
                SendMessage message;
                if (homeworks.length > 0) {
                    message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text(homeworks[0].format())
                            .replyMarkup(Menu.homeworkMenu(0, homeworks.length,homeworks[0].isDone()))
                            .build();
                    message.enableMarkdown(true);
                } else {
                    message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text("Пока дз нет(")
                            .replyMarkup(InlineKeyboardMarkup.builder().clearKeyboard().build())
                            .build();
                }
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

            else if (message_text.equalsIgnoreCase("новости")) {
                for (NewsContent news : GetterNews.getAllNews()) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text(news.format())
                            .build();
                    message.enableMarkdown(true);
                    try {
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (message_text.equalsIgnoreCase("дела старост")) {
                for (NewsContent news : GetterNews.getAllNews()) {
                    SendMessage message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text(news.format())
                            .replyMarkup(Menu.headmenMenu())
                            .build();
                    message.enableMarkdown(true);
                    try {
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }




        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();


            if (call_data.contains("news")) {
                News[] news = GetterNews.getAllNews();
                String[] commands = call_data.split("_");
                int next_id = Integer.parseInt(commands[1]);


                if (commands[2].equals("next")) {
                    next_id++;
                } else if (commands[2].equals("previous")) {
                    next_id--;
                }


                EditMessageText new_message = EditMessageText.builder()
                        .chatId(chat_id)
                        .messageId((int)message_id)
                        .text(news[next_id].format())
                        .replyMarkup(Menu.newsMenu(next_id, news.length))
                        .build();
                new_message.enableMarkdown(true);
                try {
                    telegramClient.execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (call_data.contains("homework")) {
                Homework[] homework = GetterHomework.getAllHomeworks();
                String[] commands = call_data.split("_");
                int next_id = Integer.parseInt(commands[1]);


                if (commands[2].equals("next")) {
                    next_id++;
                } else if (commands[2].equals("previous"))  {
                    next_id--;
                }


                EditMessageText new_message = EditMessageText.builder()
                        .chatId(chat_id)
                        .messageId((int)message_id)
                        .text(homework[next_id].format())
                        .replyMarkup(Menu.homeworkMenu(next_id, homework.length, homework[next_id].isDone()))
                        .build();
                new_message.enableMarkdown(true);
                try {
                    telegramClient.execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
