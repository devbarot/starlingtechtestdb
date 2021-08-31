package com.example.starlingtechtestdb.controller;

import com.example.starlingtechtestdb.services.RoundingService;
import com.example.starlingtechtestdb.services.SavingsGoalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SavingsController {

    private final RoundingService roundingService;
    private final SavingsGoalService savingsService;

    public SavingsController(RoundingService roundingService, SavingsGoalService savingsService) {
        this.roundingService = roundingService;
        this.savingsService = savingsService;
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.GET)
    public String calculateSavings(@RequestParam String changesSince) {
        return roundingService.calculateSavings(changesSince);
    }

    @RequestMapping(value = "/create-goal", method = RequestMethod.PUT)
    public String createSavingsGoal() {
        return savingsService.createGoal();
    }
}
