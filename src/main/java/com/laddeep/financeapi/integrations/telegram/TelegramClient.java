package com.laddeep.financeapi.integrations.telegram;

import com.laddeep.financeapi.integrations.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
@Component
public class TelegramClient extends RestClient {

    private static final String TOKEN = "1437601071:AAE8uLB1iZWALa0VrhrIPgQpMnPOrUSVq5I";

    @Value("${fabot.chat.id}")
    private String CHAT_ID;

    private static final String uri = "https://api.telegram.org";

    private HttpRequest request;

    @Override
    protected String endpoint() {
        return uri;
    }

    @Override
    protected String path() {
        return   "/bot"+TOKEN+"/sendMessage";
    }

    public TelegramClient(){
    }

    public void sendMessage(String message) throws IOException, InterruptedException {

        this.builder.queryParam("chat_id",CHAT_ID)
                .queryParam("text", message);

        request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        log.info(String.valueOf(response.statusCode()));
        log.info(response.body());
    }
}
