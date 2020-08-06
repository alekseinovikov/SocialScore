package com.socialscore.provider.service.impl.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.socialscore.provider.service.impl.PersonScoreProviderServiceImpl;

@ComponentScan(basePackageClasses = PersonScoreProviderServiceImpl.class)
@Configuration
public class SocialScoreProviderServiceConfiguration {
}
