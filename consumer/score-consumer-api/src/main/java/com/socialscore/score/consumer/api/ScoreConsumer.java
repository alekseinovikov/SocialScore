package com.socialscore.score.consumer.api;

import com.socialscore.score.consumer.api.dto.SocialScore;

public interface ScoreConsumer {
    void consume(final SocialScore socialScore);
}
