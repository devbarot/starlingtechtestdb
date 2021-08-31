package com.example.starlingtechtestdb.services;

import com.example.starlingtechtestdb.model.CurrencyAndAmount;
import com.example.starlingtechtestdb.model.SavingsGoal;
import com.example.starlingtechtestdb.model.Transaction;
import com.example.starlingtechtestdb.tools.CalculateSavings;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;

import static org.mockito.Mockito.*;

public class RoundingServiceTest {
    private final TransactionService transactionService = mock(TransactionService.class);
    private final PutSavingsIntoGoalService putSavingsIntoGoalService = mock(PutSavingsIntoGoalService.class);
    private final SavingsGoalService savingsGoalService = mock(SavingsGoalService.class);
    private final CalculateSavings calculateSavingsService = mock(CalculateSavings.class);

    @InjectMocks
    RoundingService roundingService = new RoundingService(transactionService, calculateSavingsService, putSavingsIntoGoalService,
            savingsGoalService);

    @Test
    public void shouldCalculateAmountAndReturnUuidOfGoal() {
        final String changesSince = "2020-01-01T12:34:56.000Z";
        final String SAVINGS_GOAL_UUID = "savingsGoalUuid";

        SavingsGoal newSavingsGoal = new SavingsGoal(SAVINGS_GOAL_UUID, "TestGoal", Currency.getInstance("GBP"),
                new CurrencyAndAmount(), new CurrencyAndAmount(),
                10, null);

        java.util.List<SavingsGoal> savingsGoalList = Collections.singletonList(newSavingsGoal);

        doReturn(savingsGoalList).when(savingsGoalService).getAllSavingsGoals();
        doReturn(newSavingsGoal).when(savingsGoalService).getSingleSavingsGoal(SAVINGS_GOAL_UUID);

        Transaction transaction = mock(Transaction.class);
        java.util.List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        when(transactionService.retrieveTransactions(changesSince)).thenReturn(transactionList);
        when(calculateSavingsService.calculateSavingAmount(transactionList)).thenReturn(BigDecimal.TEN);
        when(savingsGoalService.createGoal()).thenReturn(SAVINGS_GOAL_UUID);

        roundingService.calculateSavings(changesSince);

        verify(putSavingsIntoGoalService).putMoneyIntoGoal(SAVINGS_GOAL_UUID, BigDecimal.TEN);
    }
}
