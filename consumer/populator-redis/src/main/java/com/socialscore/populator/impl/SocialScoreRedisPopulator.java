package com.socialscore.populator.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.socialscore.populator.api.SocialScorePopulator;
import com.socialscore.populator.api.dto.SocialScore;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SocialScoreRedisPopulator implements SocialScorePopulator {

    private final RedisTemplate<String, SocialScore> redisTemplate;


    @Override
    public void populate(final SocialScore socialScore) {
        final String key = createKey(socialScore);

        redisTemplate.opsForList().rightPush(key, socialScore);
    }

    private String createKey(final SocialScore socialScore) {
        return String.format("%s-%s", socialScore.getFirstName(), socialScore.getLastName());
    }

}
