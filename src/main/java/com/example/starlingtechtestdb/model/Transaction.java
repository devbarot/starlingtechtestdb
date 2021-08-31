package com.example.starlingtechtestdb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {

    private String feedItemUid;
    private String categoryUid;
    private CurrencyAndAmount amount;
    private CurrencyAndAmount sourceAmount;
    private String direction;
    private String updatedAt;
    private String transactionTime;
    private String settlementTime;
    private String retryAllocationUntilTime;
    private String source;
    private String sourceSubType;
    private String status;
    private String transactingApplicationUserUid;
    private String counterPartyType;
    private String counterPartyUid;
    private String counterPartyName;
    private String counterPartySubEntityUid;
    private String counterPartySubEntityName;
    private String counterPartySubEntityIdentifier;
    private String counterPartySubEntitySubIdentifier;
    private Number exchangeRate;
    private Number totalFees;
    private CurrencyAndAmount totalFeeAmount;
    private String reference;
    private String country;
    private String spendingCategory;
    private String userNote;
    private AssociatedFeedRoundup roundUp;
    private boolean hasAttachment;
    private boolean hasReceipt;
}