package com.socialscore.populator.impl.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.socialscore.populator.impl.SocialScoreConsolePopulator;

@ComponentScan(basePackageClasses = SocialScoreConsolePopulator.class)
@Configuration
public class SocialScoreConsolePopulatorConfiguration {
}
