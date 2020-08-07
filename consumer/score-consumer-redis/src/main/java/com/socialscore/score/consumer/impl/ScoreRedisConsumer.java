package com.socialscore.score.consumer.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.socialscore.score.consumer.api.ScoreConsumer;
import com.socialscore.score.consumer.api.dto.SocialScore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class ScoreRedisConsumer implements ScoreConsumer {

    private final RedisTemplate<String, SocialScore> redisTemplate;


    @Override
    public void consume(final SocialScore socialScore) {
        final String key = createKey(socialScore);

        redisTemplate.opsForList().rightPush(key, socialScore);
    }

    private String createKey(final SocialScore socialScore) {
        return String.format("%s-%s", socialScore.getFirstName(), socialScore.getLastName());
    }

}
