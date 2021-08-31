package com.example.starlingtechtestdb.response;

import com.example.starlingtechtestdb.model.SavingsGoal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingsGoalResponse {
    private List<SavingsGoal> savingsGoalList;
}
