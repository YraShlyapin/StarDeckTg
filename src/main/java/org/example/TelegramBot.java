package org.example;

import org.example.dto.Homework.*;
import org.example.dto.User.*;
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
            if (current_user == null) {
                UserContent userContent = new UserContent(chat_id);
                current_user = CreatorUser.createUser(userContent);
            }
            Activity activity = Activity.values()[current_user.getActivity()];

            System.out.println(chat_id);
            System.out.println(message_text);

            if (message_text.equals("/start")) {
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
                                    .replyMarkup(MessageMenu.newsMenu(0, news.length))
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
                    //TODO доделать
                    else if (message_text.equalsIgnoreCase("Учеба")) {
                        Homework[] homeworks = GetterHomework.getAllHomeworks();
                        SendMessage message;
                        if (homeworks.length > 0) {
                            message = SendMessage
                                    .builder()
                                    .chatId(chat_id)
                                    .text(homeworks[0].format())
                                    .replyMarkup(MessageMenu.homeworkMenu(0, homeworks.length,homeworks[0].isDone()))
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

                        SetterForUser.setActivity(current_user.getId(), Activity.DelaStarost.ordinal());
                    }
                    else if (message_text.equalsIgnoreCase("настройки")) {
                        SendMessage setMenu = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text(message_text)
                                .replyMarkup(Menu.settings())
                                .build();
                        try {
                            telegramClient.execute(setMenu);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }

                        SetterForUser.setActivity(current_user.getId(), Activity.Setting.ordinal());
                    }
                    break;
                }
                //TODO доделать
                case Setting: {
                    if (message_text.equalsIgnoreCase("запросить доступ")) {
                        String role = current_user.getRole().equalsIgnoreCase("HEADMAN") ? "USER" : "HEADMAN";

                        SetterForUser.setRole(current_user.getId(), role);
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text("Роль " + role + " установлена")
                                .build();
                        try {
                            telegramClient.execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (message_text.equalsIgnoreCase("назад")) {
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
                        SetterForUser.setActivity(current_user.getId(),Activity.Main.ordinal());
                    }
                }
                case DelaStarost: {
                    if (message_text.equalsIgnoreCase("новости")) {
                        News[] All_news = GetterNews.getAllNews();

                        if (All_news.length > 0) {
                            for (News news : All_news) {
                                SendMessage message = SendMessage
                                        .builder()
                                        .chatId(chat_id)
                                        .text(news.format())
                                        .replyMarkup(MessageMenu.deleteButton("news", news.getId()))
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
                    else if (message_text.equalsIgnoreCase("Создать новость")) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text("Введите текст в формате:\nНазвание статьи\nОсновной текст статьи")
                                .replyMarkup(Menu.headmenMenu())
                                .build();
                        try {
                            telegramClient.execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        SetterForUser.setActivity(current_user.getId(),Activity.CreateNews.ordinal());
                    }
                    //TODO доделать
                    else if (message_text.equalsIgnoreCase("учеба")) {
                        Homework[] All_homework = GetterHomework.getAllHomeworks();

                        if (All_homework.length > 0) {
                            for (Homework homework : All_homework) {
                                SendMessage message = SendMessage
                                        .builder()
                                        .chatId(chat_id)
                                        .text(homework.format())
                                        .replyMarkup(MessageMenu.deleteButton("homework", homework.getId()))
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
                    }
                    //TODO доделать
                    else if (message_text.equalsIgnoreCase("Создать ДЗ")) {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text("Введите предмет")
                                .replyMarkup(MessageMenu.subject())
                                .build();
                        try {
                            telegramClient.execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else if (message_text.equalsIgnoreCase("назад")) {
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
                        SetterForUser.setActivity(current_user.getId(),Activity.Main.ordinal());
                    }
                    break;
                }
                case CreateNews: {
                    if (message_text.equalsIgnoreCase("назад")) {
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
                        SetterForUser.setActivity(current_user.getId(), Activity.Main.ordinal());
                    } else {
                        String[] components = message_text.split("\n", 2);
                        System.out.println(components[1]);
                        CreatorNews.createNews(new NewsContent(components[0], components[1]));

                        SendMessage message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text("новость создана")
                                .replyMarkup(Menu.headmenMenu())
                                .build();
                        message.enableMarkdown(true);
                        try {
                            telegramClient.execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        SetterForUser.setActivity(current_user.getId(),Activity.DelaStarost.ordinal());
                    }
                    break;
                }
                case CreateDZ: {
                    if (message_text.equalsIgnoreCase("отменить")) {
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
                        SetterForUser.setActivity(current_user.getId(), Activity.Main.ordinal());
                    } else {
                        String[] components = message_text.split("\n", 2);
                        System.out.println(components[1]);
                        CreatorHomework.createHomework(new HomeworkContent(components[0], components[1]));

                        SendMessage message = SendMessage
                                .builder()
                                .chatId(chat_id)
                                .text("ДЗ создана")
                                .replyMarkup(Menu.headmenMenu())
                                .build();
                        try {
                            telegramClient.execute(message);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                        SetterForUser.setActivity(current_user.getId(),Activity.DelaStarost.ordinal());
                    }
                    break;
                }
            }
        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            User current_user = GetterUser.getUserByChatId(chat_id);

            String[] commands = call_data.split("_");

            if (call_data.contains("news")) {

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
                else if (commands[2].equals("next") || commands[2].equals("previous")) {
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
                            .replyMarkup(MessageMenu.newsMenu(next_id, news.length))
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
            else if (call_data.contains("homework")) {
                if (commands[2].equals("delete")) {
                    DeleterHomework.deleteHomework(Integer.parseInt(commands[1]));
                    DeleteMessage deleteMessage = DeleteMessage.builder()
                            .chatId(chat_id)
                            .messageId((int)message_id)
                            .build();
                    try {
                        telegramClient.execute(deleteMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else
                if (commands[2].equals("next") || commands[2].equals("previous")) {
                    Homework[] homework = GetterHomework.getAllHomeworks();
                    int next_id = Integer.parseInt(commands[1]);
                    if (commands[2].equals("next")) {
                        next_id++;
                    } else if (commands[2].equals("previous")) {
                        next_id--;
                    }

                    EditMessageText new_message = EditMessageText.builder()
                            .chatId(chat_id)
                            .messageId((int) message_id)
                            .text(homework[next_id].format())
                            .replyMarkup(MessageMenu.homeworkMenu(next_id, homework.length, homework[next_id].isDone()))
                            .build();
                    new_message.enableMarkdown(true);
                    try {
                        telegramClient.execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (call_data.contains("subject")) {
                if (commands[2].equals("choose")) {
                    DeleterHomework.deleteHomework(Integer.parseInt(commands[1]));
                    DeleteMessage deleteMessage = DeleteMessage.builder()
                            .chatId(chat_id)
                            .messageId((int)message_id)
                            .build();
                    try {
                        telegramClient.execute(deleteMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    SendMessage message = SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text("Введите текст в формате:\nНазвание ДЗ\nСуть ДЗ")
                            .build();
                    try {
                        telegramClient.execute(message);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    SetterForUser.setActivity(current_user.getId(), Activity.CreateDZ.ordinal());
                }
            }
        }
    }
}
