package com.example.starlingtechtestdb.services;

import com.example.starlingtechtestdb.model.Transaction;
import com.example.starlingtechtestdb.response.TransactionResponse;
import com.example.starlingtechtestdb.tools.CoreUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.GET;

public class TransactionServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final CoreUtils coreUtils = mock(CoreUtils.class);
    private final TransactionResponse transactionResponse = mock(TransactionResponse.class);
    private final ResponseEntity entity = mock(ResponseEntity.class);

    private final String url = "hello";

    @InjectMocks
    private final TransactionService service = new TransactionService(url, restTemplate, coreUtils);

    @Test
    public void shouldRetrieveTransactions() throws IOException {
        final String changesSince = "2020-01-01T12:34:56.000Z";

        List<Transaction> response = transactionList();

        doReturn(response).when(transactionResponse).getFeedItems();
        doReturn(transactionResponse).when(entity).getBody();

        when(restTemplate.exchange(eq(url + changesSince), eq(GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entity);

        List<Transaction> transactions = service.retrieveTransactions(changesSince);

        assertEquals(response, transactions);
    }


    private List<Transaction> transactionList() throws IOException {
        String resourceName = "mock-transactions.json";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());

        ObjectMapper mapper = new ObjectMapper();
        TransactionResponse transaction = mapper.readValue(file, TransactionResponse.class);

        return transaction.getFeedItems();
    }
}
