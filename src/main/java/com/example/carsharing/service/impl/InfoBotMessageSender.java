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

    public void updateUserWhitChatId(String email, Long chatId) {
        User userByEmail = userService.findByEmail(email).get();
        userByEmail.setTelegramChatId(chatId);
        userService.update(userByEmail);
    }

    public void sendMassageToUserAboutCreateRental(Rental rental) {
        String testMessage = createMessageToUserAboutCreateRental(rental);
        Long chatId = rental.getUser().getTelegramChatId();
        sendMessage(chatId, testMessage);
    }

    public void sendMassageToUserAboutRental(Rental rental) {
        if (rental.getReturnDate().isBefore(LocalDate.now())) {
            sendMessage(rental.getUser().getTelegramChatId(),
                    createMessageToUserAboutOverdueRental(rental));
        } else if (rental.getReturnDate().isAfter(LocalDate.now())) {
            sendMessage(rental.getUser().getTelegramChatId(),
                    createMessageToUserAboutNotOverdueRental(rental));
        }
    }

    private String createMessageToUserAboutCreateRental(Rental rental) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Congratulations, you have ordered a car of the brand ")
                .append(rental.getCar().getBrand())
                .append(". You need to return it to ")
                .append(rental.getRentalDate()).toString();
    }

    private String createMessageToUserAboutNotOverdueRental(Rental rental) {
        StringBuilder builder = new StringBuilder();
        return builder.append("No rentals overdue today!").toString();
    }

    private String createMessageToUserAboutOverdueRental(Rental rental) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Attention, you have overdue a ")
                .append(rental.getCar().getBrand())
                .append(" car rental.")
                .append("You had to return the car by the ")
                .append(rental.getReturnDate())
                .append("date, currently date is ")
                .append(LocalDate.now()).toString();
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
