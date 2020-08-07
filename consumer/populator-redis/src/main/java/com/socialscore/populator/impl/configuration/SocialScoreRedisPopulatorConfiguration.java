package com.socialscore.populator.impl.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.socialscore.populator.api.dto.SocialScore;
import com.socialscore.populator.impl.SocialScoreRedisPopulator;
import com.socialscore.populator.impl.configuration.properties.RedisProperties;

@ComponentScan(basePackageClasses = SocialScoreRedisPopulator.class)
@Configuration
public class SocialScoreRedisPopulatorConfiguration {

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
