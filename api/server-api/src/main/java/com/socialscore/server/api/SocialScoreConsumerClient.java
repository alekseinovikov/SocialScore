package com.socialscore.server.api;

import com.socialscore.server.api.dto.SocialScoreParams;

import java.util.function.Consumer;

public interface SocialScoreConsumerClient {
    void subscribe(final Consumer<SocialScoreParams> consumer);
}
