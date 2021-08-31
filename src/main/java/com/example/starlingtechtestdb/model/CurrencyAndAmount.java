package com.example.starlingtechtestdb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyAndAmount {
    private Currency currency;
    private BigDecimal minorUnits;
}
