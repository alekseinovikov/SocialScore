package com.socialscore.consumer.impl.dto;

import com.socialscore.consumer.api.dto.SocialScoreParams;
import lombok.Value;

@Value
public class SocialScoreParamsImpl implements SocialScoreParams {
    String firstName;
    String lastName;
    int age;
    double seed;
}
