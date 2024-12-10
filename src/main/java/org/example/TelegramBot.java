package org.example;

import org.example.dto.User.*;
import org.example.dto.Homework.GetterHomework;
import org.example.dto.Homework.Homework;
import org.example.dto.News.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private TelegramClient telegramClient = new OkHttpTelegramClient("8195689343:AAE0vkGTCjZ3JsB_orV9in8fW_b2xygM1QA");

    private int a = 0;

    @Override
    public void consume(Update update) {



        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            User current_user = GetterUser.getUserByChatId(chat_id);

            Activity activity = Activity.values()[current_user.getActivity()];

            System.out.println(chat_id);
            System.out.println(message_text);

            if (message_text.equals("/start")) {
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

            switch (activity){
                case Main: {
                    if (message_text.equalsIgnoreCase("новости")) {
                        News[] news = GetterNews.getAllNews();
                        SendMessage message;
                        if (news.length > 0) {
                            message = SendMessage
                                    .builder()
                                    .chatId(chat_id)
                                    .text(news[0].format())
                                    .replyMarkup(Menu.newsMenu(0, news.length))
                                    .linkPreviewOptions(
                                            LinkPreviewOptions
                                                .builder()
                                                .isDisabled(true)
                                                .build()
                                    )
                                    .build();
                            message.enableMarkdown(true);
                            try {
                                telegramClient.execute(message);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            message = SendMessage
                                    .builder()
                                    .chatId(chat_id)
                                    .text("Пока новостей нет(")
                                    .replyMarkup(InlineKeyboardMarkup.builder().clearKeyboard().build())
                                    .build();
                            try {
                                telegramClient.execute(message);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
//                    else if (message_text.equalsIgnoreCase("ДЗ")) {
//                        Homework[] homeworks = GetterHomework.getAllHomeworks();
//                        SendMessage message;
//                        if (homeworks.length > 0) {
//                            message = SendMessage
//                                    .builder()
//                                    .chatId(chat_id)
//                                    .text(homeworks[0].format())
//                                    .replyMarkup(Menu.homeworkMenu(0, homeworks.length,homeworks[0].isDone()))
//                                    .build();
//                            message.enableMarkdown(true);
//                        } else {
//                            message = SendMessage
//                                    .builder()
//                                    .chatId(chat_id)
//                                    .text("Пока дз нет(")
//                                    .replyMarkup(InlineKeyboardMarkup.builder().clearKeyboard().build())
//                                    .build();
//                        }
//                        try {
//                            telegramClient.execute(message);
//                        } catch (TelegramApiException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
                    else if (message_text.equalsIgnoreCase("дела старост")) {
                        SendMessage setMenu = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text(message_text)
                                .replyMarkup(Menu.headmenMenu())
                                .build();
                        try {
                            telegramClient.execute(setMenu);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }

                        News[] All_news = GetterNews.getAllNews();

                        if (All_news.length > 0) {
                            for (News news : All_news) {
                                SendMessage message = SendMessage
                                        .builder()
                                        .chatId(chat_id)
                                        .text(news.format())
                                        .replyMarkup(Menu.deleteButton(news.getId()))
                                        .build();
                                message.enableMarkdown(true);
                                try {
                                    telegramClient.execute(message);
                                } catch (TelegramApiException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } else {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(chat_id)
                                    .text("Пока дз нет(")
                                    .replyMarkup(InlineKeyboardMarkup.builder().clearKeyboard().build())
                                    .build();
                            try {
                                telegramClient.execute(message);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        }


                        SetterForUser.setActivity(current_user.getId(),1);
                    }
                    break;
                }
                case DelaStarost: {
                    if (message_text.equalsIgnoreCase("Создать новости")) {
                            SendMessage message = SendMessage
                                    .builder()
                                    .chatId(chat_id)
                                    .text("Введите текст в формате:\nНазвание статьи\nОсновной текст статьи")
                                    .replyMarkup(Menu.headmenMenu())
                                    .build();
                            message.enableMarkdown(true);
                            try {
                                telegramClient.execute(message);
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        SetterForUser.setActivity(current_user.getId(),2);
                    } else if (message_text.equalsIgnoreCase("назад")) {
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
                        SetterForUser.setActivity(current_user.getId(),0);
                    }
                    break;
                }
                case CreateNews: {
                    String[] components = message_text.split("\n", 2);
                    System.out.println(components[1]);
                    CreatorNews.createNews(new NewsContent(components[0], components[1]));
                    SetterForUser.setActivity(current_user.getId(),0);
                    break;
                }
            }
        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();


            if (call_data.contains("news")) {
                String[] commands = call_data.split("_");


                if (commands[2].equals("delete")) {
                    DeleterNews.deleteNews(Integer.parseInt(commands[1]));
                    DeleteMessage deleteMessage = DeleteMessage.builder()
                            .chatId(chat_id)
                            .messageId((int)message_id)
                            .build();
                    try {
                        telegramClient.execute(deleteMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                if (commands[2].equals("next") || commands[2].equals("previous")) {
                    News[] news = GetterNews.getAllNews();
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
                            .linkPreviewOptions(
                                    LinkPreviewOptions
                                            .builder()
                                            .isDisabled(true)
                                            .build()
                            )
                            .build();
                    new_message.enableMarkdown(true);
                    try {
                        telegramClient.execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
//            else if (call_data.contains("homework")) {
//                Homework[] homework = GetterHomework.getAllHomeworks();
//                String[] commands = call_data.split("_");
//                int next_id = Integer.parseInt(commands[1]);
//
//
//                if (commands[2].equals("next")) {
//                    next_id++;
//                } else if (commands[2].equals("previous"))  {
//                    next_id--;
//                }
//
//
//                EditMessageText new_message = EditMessageText.builder()
//                        .chatId(chat_id)
//                        .messageId((int)message_id)
//                        .text(homework[next_id].format())
//                        .replyMarkup(Menu.homeworkMenu(next_id, homework.length, homework[next_id].isDone()))
//                        .build();
//                new_message.enableMarkdown(true);
//                try {
//                    telegramClient.execute(new_message);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
}
