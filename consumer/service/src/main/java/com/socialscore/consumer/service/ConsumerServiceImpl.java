package com.socialscore.consumer.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.socialscore.consumer.service.calculator.SocialScoreCalculator;
import com.socialscore.consumer.service.calculator.dto.SocialRatingData;
import com.socialscore.populator.api.SocialScorePopulator;
import com.socialscore.server.api.SocialScoreConsumerClient;
import com.socialscore.server.api.dto.SocialScoreParams;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class ConsumerServiceImpl {

    private final SocialScoreConsumerClient socialScoreConsumerClient;
    private final SocialScoreCalculator socialScoreCalculator;
    private final List<SocialScorePopulator> populators;


    @PostConstruct
    public void init() {
        this.socialScoreConsumerClient.subscribe(this::acceptMessage);
    }


    private void acceptMessage(final SocialScoreParams params) {
        final double score = socialScoreCalculator.calculate(params);

        final SocialRatingData socialRatingData = new SocialRatingData(params.getFirstName(), params.getLastName(), score);
        populators.forEach(p -> p.populate(socialRatingData));
    }

}
