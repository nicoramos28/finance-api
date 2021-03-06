package com.laddeep.financeapi.integrations.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
@Component
public class TelegramClient {

    private static final String TOKEN = "1437601071:AAE8uLB1iZWALa0VrhrIPgQpMnPOrUSVq5I";

    @Value("${fabot.chat.id}")
    private String CHAT_ID;

    private static final String endpoint = "https://api.telegram.org";

    private HttpRequest request;

    private HttpClient client;

    private UriBuilder builder;

    private String path() {
        return   "/bot"+TOKEN+"/sendMessage";
    }

    public TelegramClient(){
        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    public void sendMessage(String message) throws IOException, InterruptedException {
        builder = UriBuilder
                .fromUri(endpoint)
                .path(path());
        log.info("Message to send : \n{}", message);
        this.builder.queryParam("chat_id",CHAT_ID)
                .queryParam("text", message);

        request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        log.info(String.valueOf(response.statusCode()));
        log.info(response.body());
    }
}
