package com.socialscore.score.consumer.impl.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.socialscore.score.consumer.api.dto.SocialScore;
import com.socialscore.score.consumer.impl.ScoreRedisConsumer;
import com.socialscore.score.consumer.impl.configuration.properties.RedisProperties;

@ComponentScan(basePackageClasses = ScoreRedisConsumer.class)
@Configuration
public class SocialScoreRedisConsumerConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public RedisProperties redisProperties(final org.springframework.boot.autoconfigure.data.redis.RedisProperties springRedisProperties) {
        return new RedisProperties(springRedisProperties.getHost(),
                                   springRedisProperties.getPort());
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(final RedisProperties redisProperties) {
        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                redisProperties.getHost(),
                redisProperties.getPort()
        );

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, SocialScore> redisTemplate(final JedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, SocialScore> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

}
