package com.socialscore.provider.rest.configuration;

import com.socialscore.provider.rest.controller.PersonRegisterController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = PersonRegisterController.class)
@Configuration
public class SocialScoreProviderRestConfiguration {
}
