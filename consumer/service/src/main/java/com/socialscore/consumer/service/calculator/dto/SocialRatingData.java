package com.socialscore.consumer.service.calculator.dto;

import com.socialscore.populator.api.dto.SocialScore;

import lombok.Value;

@Value
public class SocialRatingData implements SocialScore {
    String firstName;
    String lastName;
    double score;
}
