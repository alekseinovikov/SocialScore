package com.socialscore.score.consumer.impl.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.socialscore.score.consumer.impl.ScoreConsoleConsumer;

@ComponentScan(basePackageClasses = ScoreConsoleConsumer.class)
@Configuration
public class SocialScoreConsoleConsumerConfiguration {
}
