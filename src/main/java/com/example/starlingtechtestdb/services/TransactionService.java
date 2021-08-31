package com.example.starlingtechtestdb.services;

import com.example.starlingtechtestdb.model.Transaction;
import com.example.starlingtechtestdb.response.TransactionResponse;
import com.example.starlingtechtestdb.tools.CoreUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private final RestTemplate template;
    private final CoreUtils coreUtils;
    private final String url;

    public TransactionService(@Value("${transactions-url}") String url,
                              RestTemplate template,
                              CoreUtils coreUtils) {
        this.url = url;
        this.template = template;
        this.coreUtils = coreUtils;
    }

    /*
    Using https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate
    .html#
    */
    public List<Transaction> retrieveTransactions(String changesSince) {
        HttpEntity<TransactionResponse> getEntity = getHttpEntity();

        ResponseEntity<TransactionResponse> exchange = template.exchange(url + changesSince,
                HttpMethod.GET,
                getEntity,
                new ParameterizedTypeReference<TransactionResponse>() {
                });

        ResponseEntity<TransactionResponse> responseEntity =
                Objects.requireNonNull(exchange);

        return Objects.requireNonNull(responseEntity.getBody()).getFeedItems();

    }

    private HttpEntity<TransactionResponse> getHttpEntity() {
        final HttpHeaders httpHeaders = coreUtils.configureHeaders();
        return new HttpEntity<>(httpHeaders);
    }
}
