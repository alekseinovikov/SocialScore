package com.socialscore.provider.service.impl;

import com.socialscore.producer.api.dto.SocialScoreParams;

import lombok.Value;

@Value
public class SocialScoreParamsImpl implements SocialScoreParams {
    String firstName;
    String lastName;
    int age;
    double seed;
}
