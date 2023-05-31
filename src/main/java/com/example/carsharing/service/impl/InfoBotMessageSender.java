package com.example.carsharing.service.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class InfoBotMessageSender extends TelegramLongPollingBot {
    public InfoBotMessageSender() {
        super("6027715920:AAHCHRX5gOFuG4xV9rHFHRgRmWhIXnRUvd0");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update != null) {
            sendMessage(update.getMessage().getChatId(), "Please write you email");
        }
    }

    @Override
    public String getBotUsername() {
        return "CarSharingInfo";
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can`t send message to user with id ");
        }
    }

    public String getBotToken() {
        return "6043533330:AAFOfwvTBnARxFj4a41o7dbbn0RO8bZxejc";
    }
}
