package com.socialscore.score.consumer.impl;

import org.springframework.stereotype.Component;

import com.socialscore.score.consumer.api.ScoreConsumer;
import com.socialscore.score.consumer.api.dto.SocialScore;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component
public class ScoreConsoleConsumer implements ScoreConsumer {

    @Override
    public void consume(final SocialScore socialScore) {
        final String message = format(socialScore);

        log.info(message);
    }

    private String format(final SocialScore socialScore) {
        return String.format("%s %s has %f score", socialScore.getFirstName(), socialScore.getLastName(), socialScore.getScore());
    }

}
