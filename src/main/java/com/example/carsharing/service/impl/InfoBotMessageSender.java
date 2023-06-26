package com.example.carsharing.service.impl;

import com.example.carsharing.model.Rental;
import com.example.carsharing.model.User;
import com.example.carsharing.service.UserService;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class InfoBotMessageSender extends TelegramLongPollingBot {
    private final UserService userService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            Long telegramChatId = update.getMessage().getChatId();
            if (message.equals("/start")) {
                sendMessage(telegramChatId, "Hello, "
                        + update.getMessage().getChat().getUserName()
                        + ". Please enter your email address to receive notifications. ");
                return;
            } else if (message.contains("@")) {
                updateUserWhitChatId(message, telegramChatId);
                return;
            }
        }
    }

    public void updateUserWhitChatId(String email, Long chatId) {
        Optional<User> optionalUserByEmail = userService.findByEmail(email);
        if (optionalUserByEmail.isPresent()) {
            User userByEmail = optionalUserByEmail.get();
            userByEmail.setTelegramChatId(chatId);
            userService.update(userByEmail);
            sendMessage(chatId, "Your email has been received, expect a notification.");
        } else {
            sendMessage(chatId, "User with this email was not found, try again.");
        }

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
        return "CarSharingTetsBot";
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can`t send message to user with id ");
        }
    }

    public String getBotToken() {
        return "6165726109:AAHvih1gxPsSLDsplGlaomMBewR1S-OmsFs";
    }
}
