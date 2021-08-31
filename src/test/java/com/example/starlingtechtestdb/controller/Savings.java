package com.example.starlingtechtestdb.controller;

import com.example.starlingtechtestdb.services.RoundingService;
import com.example.starlingtechtestdb.services.SavingsGoalService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Savings {

    private static final String ROUNDING_SERVICE_UUID = "roundingServiceUuid";
    private static final String SAVINGS_GOAL_UUID = "savingsGoalUuid";
    private static final String changesSince = "2020-01-01T12:34:56.000Z";
    private final RoundingService roundingService = mock(RoundingService.class);
    private final SavingsGoalService savingsGoalService = mock(SavingsGoalService.class);

    @InjectMocks
    private final SavingsController savingsController = new SavingsController(roundingService, savingsGoalService);

    @Test
    public void shouldCallRoundingService() {
        when(roundingService.calculateSavings(changesSince)).thenReturn(ROUNDING_SERVICE_UUID);
        String roundedGeneratedUuid = savingsController.calculateSavings(changesSince);

        assertEquals(ROUNDING_SERVICE_UUID, roundedGeneratedUuid);
    }

    @Test
    public void shouldCallCreateSavingsGoalService() {
        when(savingsGoalService.createGoal()).thenReturn(SAVINGS_GOAL_UUID);
        String createdGoalUuid = savingsController.createSavingsGoal();

        assertEquals(SAVINGS_GOAL_UUID, createdGoalUuid);
    }

}
