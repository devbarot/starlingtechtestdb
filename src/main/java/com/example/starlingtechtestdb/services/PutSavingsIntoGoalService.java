package com.example.starlingtechtestdb.services;

import com.example.starlingtechtestdb.model.AddMoney;
import com.example.starlingtechtestdb.model.CurrencyAndAmount;
import com.example.starlingtechtestdb.tools.CoreUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;

import static org.springframework.http.HttpMethod.PUT;

@Service
public class PutSavingsIntoGoalService {

    private final RestTemplate template;
    private final CoreUtils utils;
    private final String url;

    public PutSavingsIntoGoalService(@Value("${put-savings-url}") String url,
                                     RestTemplate template,
                                     CoreUtils utils) {
        this.url = url;
        this.template = template;
        this.utils = utils;
    }

    public void putMoneyIntoGoal(String savingsGoalUId, BigDecimal roundedAmount) {
        final HashMap<String, String> urlVariables = new HashMap<>();
        final String generatedUUID = utils.getId();

        urlVariables.put("savingsGoalUid", savingsGoalUId);
        urlVariables.put("transferId", generatedUUID);

        HttpEntity<AddMoney> putMoneyIntoGoal = setCurrencyAndFormat(roundedAmount);

        template.exchange(url + savingsGoalUId + "/add-money/" + generatedUUID,
                PUT, putMoneyIntoGoal, String.class, urlVariables);
    }

    private HttpEntity<AddMoney> setCurrencyAndFormat(BigDecimal roundedAmount) {

        final HttpHeaders httpHeaders = utils.configureHeaders();
        CurrencyAndAmount amount = new CurrencyAndAmount();

        amount.setCurrency(Currency.getInstance("GBP"));
        amount.setMinorUnits(roundedAmount);

        AddMoney addMoney = new AddMoney();
        addMoney.setAmount(amount);

        return new HttpEntity<>(addMoney, httpHeaders);
    }
}
