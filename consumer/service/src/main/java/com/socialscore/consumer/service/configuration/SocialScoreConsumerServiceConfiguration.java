package com.socialscore.consumer.service.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.socialscore.consumer.service.ConsumerServiceImpl;

@ComponentScan(basePackageClasses = ConsumerServiceImpl.class)
@Configuration
public class SocialScoreConsumerServiceConfiguration {
}
