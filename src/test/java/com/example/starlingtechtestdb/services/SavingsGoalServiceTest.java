package com.example.starlingtechtestdb.services;

import com.example.starlingtechtestdb.model.SavingsGoal;
import com.example.starlingtechtestdb.response.SavingsGoalResponse;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

public class SavingsGoalServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final CoreUtils coreUtils = mock(CoreUtils.class);
    private final ResponseEntity entity = mock(ResponseEntity.class);

    private final String createGoalUrl = "hello";
    private final String getGoalUrl = "world";
    public static final String TEST_UUID = "testUUid";

    @InjectMocks
    private final SavingsGoalService service = new SavingsGoalService(createGoalUrl, getGoalUrl, restTemplate, coreUtils);

    @Test
    public void testCreateSavingsGoal() {
        when(coreUtils.getId()).thenReturn(TEST_UUID);
        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("goalId", TEST_UUID);

        String savingsGoal = service.createGoal();

        verify(restTemplate).exchange(eq(createGoalUrl),
                eq(PUT), any(HttpEntity.class), eq(Void.class), eq(urlParams));

        assertSame(TEST_UUID, savingsGoal);
    }

    @Test
    public void shouldRetrieveAllGoals() throws IOException {
        SavingsGoalResponse savingsGoalResponse = mock(SavingsGoalResponse.class);

        List<SavingsGoal> response = savingsGoalList();

        doReturn(response).when(savingsGoalResponse).getSavingsGoalList();
        doReturn(savingsGoalResponse).when(entity).getBody();

        when(restTemplate.exchange(eq(getGoalUrl), eq(GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entity);

        List<SavingsGoal> savingsGoals = service.getAllSavingsGoals();

        assertEquals(response, savingsGoals);
    }

    @Test
    public void shouldRetrieveSingleGoal() throws IOException {
        SavingsGoal savingsGoal = mock(SavingsGoal.class);

        SavingsGoal singleSavingsGoal = savingsGoalList().get(0);
        final String savingsGoalUuid = singleSavingsGoal.getSavingsGoalUid();

        doReturn(singleSavingsGoal.getName()).when(savingsGoal).getName();
        doReturn(singleSavingsGoal.getTarget()).when(savingsGoal).getTarget();
        doReturn(savingsGoal).when(entity).getBody();

        when(restTemplate.exchange(eq(getGoalUrl + savingsGoalUuid), eq(GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entity);

        SavingsGoal returnedGoal = service.getSingleSavingsGoal(savingsGoalUuid);

        assertEquals("Christmas", returnedGoal.getName());
        assertEquals(BigDecimal.valueOf(106797), returnedGoal.getTarget().getMinorUnits());
        assertNotEquals(BigDecimal.valueOf(20), returnedGoal.getTarget().getMinorUnits());
    }


    private List<SavingsGoal> savingsGoalList() throws IOException {
        String resourceName = "mock-goals.json";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());

        ObjectMapper mapper = new ObjectMapper();
        SavingsGoalResponse savingsGoalResponse = mapper.readValue(file, SavingsGoalResponse.class);

        return savingsGoalResponse.getSavingsGoalList();
    }
}
