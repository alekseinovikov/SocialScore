package com.socialscore.populator.impl;

import org.springframework.stereotype.Component;

import com.socialscore.populator.api.SocialScorePopulator;
import com.socialscore.populator.api.dto.SocialScore;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component
public class SocialScoreConsolePopulator implements SocialScorePopulator {

    @Override
    public void populate(final SocialScore socialScore) {
        final String message = format(socialScore);

        log.info(message);
    }

    private String format(final SocialScore socialScore) {
        return String.format("%s %s has %f score", socialScore.getFirstName(), socialScore.getLastName(), socialScore.getScore());
    }

}
