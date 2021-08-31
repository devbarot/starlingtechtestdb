package com.example.starlingtechtestdb.services;

import com.example.starlingtechtestdb.tools.CoreUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.PUT;

public class PutSavingsIntoGoalServiceTest {

    private final CoreUtils utils = mock(CoreUtils.class);
    private final RestTemplate restTemplate = mock(RestTemplate.class);

    public static final String SAVINGS_GOAL_UUID = "savingGoalUuid";
    public static final String TRANSFER_UUID = "transferUuid";
    public static final String putSavingsUrl = "hello";

    @InjectMocks
    private PutSavingsIntoGoalService putSavingsIntoGoalService;

    @BeforeEach
    public void before() {
        putSavingsIntoGoalService = spy(new PutSavingsIntoGoalService(putSavingsUrl, restTemplate, utils));
    }

    @Test
    public void shouldCreateSavingsService() {
        final HashMap<String, String> urlVariables = new HashMap<>();
        urlVariables.put("savingsGoalUid", SAVINGS_GOAL_UUID);
        urlVariables.put("transferId", TRANSFER_UUID);

        when(utils.getId()).thenReturn(TRANSFER_UUID);
        doCallRealMethod().when(putSavingsIntoGoalService).putMoneyIntoGoal(SAVINGS_GOAL_UUID, BigDecimal.valueOf(20));

        putSavingsIntoGoalService.putMoneyIntoGoal(SAVINGS_GOAL_UUID, BigDecimal.valueOf(20));

        verify(restTemplate).exchange(eq(putSavingsUrl + SAVINGS_GOAL_UUID + "/add-money/" + TRANSFER_UUID),
                eq(PUT), any(HttpEntity.class), eq(String.class), eq(urlVariables));

    }
}
