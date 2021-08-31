package com.example.starlingtechtestdb.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class CoreUtils {
    @Value("${token}")
    private String token;

    public HttpHeaders configureHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }

    /*UUID Generator*/
    public String getId() {
        return UUID.randomUUID().toString();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
