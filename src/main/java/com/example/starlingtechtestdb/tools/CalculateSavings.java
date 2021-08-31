package com.example.starlingtechtestdb.tools;

import com.example.starlingtechtestdb.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CalculateSavings {
    public BigDecimal calculateSavingAmount(List<Transaction> transactions) {
        return transactions.stream().filter(transaction -> transaction.getAmount().getMinorUnits()
                .compareTo(BigDecimal.ZERO) > 0).map(this::calculateRoundupAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateRoundupAmount(Transaction transaction) {
        BigDecimal amountToRound = transaction.getAmount().getMinorUnits();

        int roundedInteger;
        BigDecimal finalCalculatedAmount;
        double calculatedAmount = amountToRound.doubleValue();

        //Check if number is integer or not
        //if it is, round to nearest 10th
        //else round decimal to nearest integer
        if(calculatedAmount % 1 == 0) {
            roundedInteger = ((amountToRound.intValue() + 5)/10) * 10;
            finalCalculatedAmount = BigDecimal.valueOf(roundedInteger).subtract(amountToRound);
        } else {
            double roundedDouble = Math.ceil(calculatedAmount);
            finalCalculatedAmount = BigDecimal.valueOf(roundedDouble).subtract(amountToRound);
        }

        return finalCalculatedAmount;
    }
}
