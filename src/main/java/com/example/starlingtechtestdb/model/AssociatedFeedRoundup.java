package com.example.starlingtechtestdb.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssociatedFeedRoundup {
    private String goalCategoryUid;
    private CurrencyAndAmount amount;
}
