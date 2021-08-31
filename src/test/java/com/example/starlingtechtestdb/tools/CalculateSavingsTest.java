package com.example.starlingtechtestdb.tools;

import com.example.starlingtechtestdb.model.CurrencyAndAmount;
import com.example.starlingtechtestdb.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculateSavingsTest {

    private final CalculateSavings calculateSavings = new CalculateSavings();

    @Test
    public void shouldReturnRoundupAmount() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);
        Transaction transaction3 = mock(Transaction.class);
        Transaction transaction4 = mock(Transaction.class);
        Transaction transaction5 = mock(Transaction.class);
        Transaction transaction6 = mock(Transaction.class);

        when(transaction1.getAmount()).thenReturn(setCurrencyAndAmount(Currency.getInstance("GBP"),
                BigDecimal.valueOf(3.60)));
        when(transaction2.getAmount()).thenReturn(setCurrencyAndAmount(Currency.getInstance("GBP"),
                BigDecimal.valueOf(4.30)));
        when(transaction3.getAmount()).thenReturn(setCurrencyAndAmount(Currency.getInstance("GBP"),
                BigDecimal.valueOf(0.90)));

        //Should total to 2.20 once transaction4 below runs
        when(transaction4.getAmount()).thenReturn(setCurrencyAndAmount(Currency.getInstance("GBP"),
                BigDecimal.valueOf(9)));

        //Should change to 6.20 at transaction5
        when(transaction5.getAmount()).thenReturn(setCurrencyAndAmount(Currency.getInstance("GBP"),
                BigDecimal.valueOf(56)));

        //Should stay 6.20 at transaction6
        when(transaction6.getAmount()).thenReturn(setCurrencyAndAmount(Currency.getInstance("GBP"),
                BigDecimal.valueOf(22000)));

        java.util.List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        transactionList.add(transaction4);
        transactionList.add(transaction5);

        //Using double to accurately read value to right amount of decimal places.
        Double roundupValue = calculateSavings.calculateSavingAmount(transactionList).doubleValue();

        assertEquals(6.20, roundupValue);
    }

    private CurrencyAndAmount setCurrencyAndAmount(Currency currency, BigDecimal amount) {
        return new CurrencyAndAmount(currency, amount);
    }

}
