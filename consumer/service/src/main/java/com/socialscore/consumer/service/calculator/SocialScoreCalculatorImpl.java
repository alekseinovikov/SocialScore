package com.socialscore.consumer.service.calculator;

import org.springframework.stereotype.Component;

import com.socialscore.consumer.api.dto.SocialScoreParams;

@Component
public class SocialScoreCalculatorImpl implements SocialScoreCalculator {

    @Override
    public double calculate(final SocialScoreParams params) {
        final double seed = params.getSeed();
        final int age = params.getAge();

        return seed * age;
    }

}
