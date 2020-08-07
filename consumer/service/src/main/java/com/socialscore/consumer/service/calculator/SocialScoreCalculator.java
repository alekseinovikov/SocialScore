package com.socialscore.consumer.service.calculator;

import com.socialscore.server.api.dto.SocialScoreParams;

public interface SocialScoreCalculator {
    double calculate(final SocialScoreParams socialScoreParams);
}
