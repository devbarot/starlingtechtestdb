package com.example.starlingtechtestdb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Currency;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingsGoal {
    private String savingsGoalUid;
    private String name;
    private Currency currency;
    private CurrencyAndAmount target;
    private CurrencyAndAmount totalSaved;
    private int savedPercentage;
    @Nullable
    private String base64EncodedPhoto;
}
