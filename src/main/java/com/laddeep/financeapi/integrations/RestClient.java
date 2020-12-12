package com.laddeep.financeapi.integrations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.ws.rs.core.UriBuilder;
import java.net.http.HttpClient;
import java.time.Duration;

@Slf4j
@Service
public abstract class RestClient {

    protected abstract String endpoint();

    protected abstract String path();

    protected HttpClient client;

    protected UriBuilder builder;

    public RestClient(){
        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .version(HttpClient.Version.HTTP_2)
                .build();
        builder = UriBuilder
                .fromUri(endpoint())
                .path(path());
    }


}
