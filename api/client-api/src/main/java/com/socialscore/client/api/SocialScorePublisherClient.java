package com.socialscore.client.api;

import com.socialscore.client.api.dto.SocialScoreParams;

public interface SocialScorePublisherClient {

    void publish(final SocialScoreParams params);

}
