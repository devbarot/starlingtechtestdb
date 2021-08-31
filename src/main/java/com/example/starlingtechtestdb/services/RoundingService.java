package com.example.starlingtechtestdb.services;

import com.example.starlingtechtestdb.model.SavingsGoal;
import com.example.starlingtechtestdb.model.Transaction;
import com.example.starlingtechtestdb.tools.CalculateSavings;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class RoundingService {
    private final TransactionService transactionService;

    private final SavingsGoalService savingsGoalService;

    private final PutSavingsIntoGoalService putSavingsIntoGoalService;

    private final CalculateSavings calculateSavings;

    public RoundingService(TransactionService transactionService,
                           CalculateSavings calculateSavings,
                           PutSavingsIntoGoalService putSavingsIntoGoalService,
                           SavingsGoalService savingsGoalService) {
        this.transactionService = transactionService;
        this.calculateSavings = calculateSavings;
        this.putSavingsIntoGoalService = putSavingsIntoGoalService;
        this.savingsGoalService = savingsGoalService;
    }

    public String calculateSavings(String changesSince) {
        final List<Transaction> transactions = transactionService.retrieveTransactions(changesSince);
        final BigDecimal calculatedSaving = calculateSavings.calculateSavingAmount(transactions);
        final List<SavingsGoal> savingsGoalList = savingsGoalService.getAllSavingsGoals();

        final String savingsGoalUuid =
                Objects.requireNonNull(savingsGoalList.get(0).getSavingsGoalUid());

        final SavingsGoal goalToPutSavingsIn = savingsGoalService.getSingleSavingsGoal(savingsGoalUuid);
        putSavingsIntoGoalService.putMoneyIntoGoal(goalToPutSavingsIn.getSavingsGoalUid(), calculatedSaving);

        return savingsGoalUuid;
    }
}
