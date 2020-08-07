package com.socialscore.populator.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.socialscore.populator.api.dto.SocialScore;

@ExtendWith(MockitoExtension.class)
class SocialScoreRedisPopulatorTest {

    @Mock
    private RedisTemplate<String, SocialScore> redisTemplate;

    @Mock
    private ListOperations<String, SocialScore> listOperations;

    @InjectMocks
    private SocialScoreRedisPopulator populator;


    @BeforeEach
    public void setUp() {
        when(redisTemplate.opsForList()).thenReturn(listOperations);
    }

    @Test
    void populate_hasValue_putsWithKey() {
        //arrange
        final SocialScore socialScore = new SocialScore() {
            @Override
            public String getFirstName() {
                return "firstName";
            }

            @Override
            public String getLastName() {
                return "lastName";
            }

            @Override
            public double getScore() {
                return 12.3;
            }
        };

        final String expectedKey = String.format("%s-%s", "firstName", "lastName");

        //act
        populator.populate(socialScore);

        //assert
        verify(listOperations, Mockito.times(1)).rightPush(expectedKey, socialScore);
    }

}