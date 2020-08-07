package com.socialscore.consumer.api;

import com.socialscore.consumer.api.dto.SocialScoreParams;

import java.util.function.Consumer;

public interface SocialScoreConsumerClient {
    void subscribe(final Consumer<SocialScoreParams> consumer);
}
