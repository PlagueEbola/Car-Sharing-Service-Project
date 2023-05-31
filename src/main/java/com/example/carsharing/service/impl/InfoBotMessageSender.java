package com.example.carsharing.service.impl;

import com.example.carsharing.model.Rental;
import com.example.carsharing.model.User;
import com.example.carsharing.service.UserService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class InfoBotMessageSender extends TelegramLongPollingBot {
    @Autowired
    private UserService userService;

    public InfoBotMessageSender() {
        super("6027715920:AAHCHRX5gOFuG4xV9rHFHRgRmWhIXnRUvd0");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update != null) {
            sendMessage(update.getMessage().getChatId(), "Please write you email");
        } else if (update.getMessage().getText().contains("@gmail.com")) {
            updateUserWhitChatId(update.getMessage().getText(), update.getMessage().getChatId());
        }
    }

    @Override
    public String getBotUsername() {
        return "CarSharingInfo";
    }

    public void updateUserWhitChatId(String email, Long chatId) {
        User userByEmail = userService.findUserByEmail(email);
        userByEmail.setTelegramChatId(chatId);
        userService.save(userByEmail);
    }

    public void sendMassageToUser(Rental rental) {
        String testMessage = createMessageToUser(rental.getReturnDate(),
                rental.getCar().getBrand());
        Long chatId = rental.getUser().getTelegramChatId();
        sendMessage(chatId, testMessage);
    }

    private String createMessageToUser(LocalDate returnDate, String carBrand) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Congratulations, you have ordered a car of the brand ")
                .append(carBrand)
                .append(". You need to return it to ")
                .append(returnDate).toString();
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can`t send message to user with chat id " + chatId);
        }
    }

    public String getBotToken() {
        return "6043533330:AAFOfwvTBnARxFj4a41o7dbbn0RO8bZxejc";
    }
}
