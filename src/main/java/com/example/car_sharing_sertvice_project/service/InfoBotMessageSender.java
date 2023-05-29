package com.example.car_sharing_sertvice_project.service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InfoBotMessageSender {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final String BOT_ID = "6043533330:AAFOfwvTBnARxFj4a41o7dbbn0RO8bZxejc";

    public String botMessage(String channelId, String message) {
        String url = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s".formatted(
                BOT_ID,
                channelId,
                URLEncoder.encode(message, StandardCharsets.UTF_8));
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return "Success";
        } catch (IOException e) {
            throw new RuntimeException("Can't fetch info from url " + url, e);
        }
    }

    @PostConstruct
    public void onStartup() {
        System.out.println("Bot must boot");
        String channelId = "-1001876562862";
        String message = "Bot has been booted \n expect more info soon";
        botMessage(channelId, message);
    }
}
