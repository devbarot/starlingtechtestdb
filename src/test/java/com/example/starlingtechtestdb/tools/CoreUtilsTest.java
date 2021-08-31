package com.example.starlingtechtestdb.tools;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class CoreUtilsTest {

    private final CoreUtils utils = mock(CoreUtils.class);

    @Test
    public void shouldGenerateHeaders() {
        HttpHeaders headers = getTestHeaders();

        doReturn(headers).when(utils).configureHeaders();

        HttpHeaders returnedHeaders = utils.configureHeaders();

        assertEquals(returnedHeaders.getAccept(), Collections.singletonList(APPLICATION_JSON));
        assertEquals(returnedHeaders.getContentType(), APPLICATION_JSON);
        assertNotNull(returnedHeaders.get("Authorization"), "Hello");
    }

    @Test
    public void shouldGenerateUuid() {
        String randomUuid = "testUuid";
        doReturn(randomUuid).when(utils).getId();

        String generatedUuid = utils.getId();
        assertEquals(randomUuid, generatedUuid);
    }

    private HttpHeaders getTestHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(APPLICATION_JSON);
        headers.add("Authorization",  "Hello");

        return headers;
    }


}
