package com.socialscore.producer.api;

import com.socialscore.producer.api.dto.SocialScoreParams;

public interface SocialScoreProducer {

    void publish(final SocialScoreParams params);

}
