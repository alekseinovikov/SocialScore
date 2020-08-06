package com.socialscore.server.impl.dto;

import com.socialscore.server.api.dto.SocialScoreParams;
import lombok.Value;

@Value
public class SocialScoreParamsImpl implements SocialScoreParams {
    String firstName;
    String lastName;
    int age;
    double seed;
}
