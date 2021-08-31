package com.example.starlingtechtestdb.services;

import com.example.starlingtechtestdb.model.CurrencyAndAmount;
import com.example.starlingtechtestdb.model.SavingsGoal;
import com.example.starlingtechtestdb.response.SavingsGoalResponse;
import com.example.starlingtechtestdb.tools.CoreUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.util.Currency.getInstance;
import static org.springframework.http.HttpMethod.PUT;

@Service
public class SavingsGoalService {

    private final String createGoalUrl;
    private final String getGoalsUrl;
    private final RestTemplate template;
    private final CoreUtils utils;

    public SavingsGoalService(@Value("${create-savingsGoal-url}") String createGoalUrl,
                              @Value("${get-savingsGoal-url}") String getGoalsUrl,
                              RestTemplate template,
                              CoreUtils utils) {
        this.createGoalUrl = createGoalUrl;
        this.getGoalsUrl = getGoalsUrl;
        this.template = template;
        this.utils = utils;
    }

    public String createGoal() {
        final HashMap<String, String> urlVariables = new HashMap<>();
        final String generatedUUID = utils.getId();
        urlVariables.put("goalId", generatedUUID);

        final HttpEntity<SavingsGoal> savingsGoal = parametersToCreateNewGoal();

        template.exchange(createGoalUrl,
                PUT, savingsGoal, Void.class, urlVariables);

        return generatedUUID;
    }

    public List<SavingsGoal> getAllSavingsGoals() {
        final HttpEntity<SavingsGoal> getEntity = getHttpEntity();
        ResponseEntity<SavingsGoalResponse> responseEntity =
                Objects.requireNonNull(template.exchange(getGoalsUrl,
                        HttpMethod.GET,
                        getEntity,
                        new ParameterizedTypeReference<SavingsGoalResponse>() {
                        }));

        return Objects.requireNonNull(responseEntity.getBody()).getSavingsGoalList();
    }

    public SavingsGoal getSingleSavingsGoal(String savingsGoalUuid) {
        final HttpEntity<SavingsGoal> getEntity = getHttpEntity();
        ResponseEntity<SavingsGoal> responseEntity =
                Objects.requireNonNull(template.exchange(getGoalsUrl + savingsGoalUuid,
                        HttpMethod.GET,
                        getEntity,
                        new ParameterizedTypeReference<SavingsGoal>() {
                        }));

        return responseEntity.getBody();
    }

    private HttpEntity<SavingsGoal> parametersToCreateNewGoal() {
        final HttpHeaders httpHeaders = getHttpHeaders();
        CurrencyAndAmount savingsGoalTarget = new CurrencyAndAmount();

        savingsGoalTarget.setCurrency(getInstance("GBP"));
        savingsGoalTarget.setMinorUnits(BigDecimal.valueOf(3000));

        SavingsGoal savingsGoal = new SavingsGoal();
        savingsGoal.setName("Holiday");
        savingsGoal.setCurrency(getInstance("GBP"));
        savingsGoal.setTarget(savingsGoalTarget);
        savingsGoal.setBase64EncodedPhoto(null);

        return new HttpEntity<>(savingsGoal, httpHeaders);
    }

    private HttpEntity<SavingsGoal> getHttpEntity() {
        final HttpHeaders httpHeaders = getHttpHeaders();
        return new HttpEntity<>(httpHeaders);
    }

    private HttpHeaders getHttpHeaders() {
        return utils.configureHeaders();
    }
}
