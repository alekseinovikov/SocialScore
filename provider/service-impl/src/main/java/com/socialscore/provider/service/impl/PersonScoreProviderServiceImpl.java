package com.socialscore.provider.service.impl;

import org.springframework.stereotype.Component;

import com.socialscore.producer.api.SocialScoreProducer;
import com.socialscore.producer.api.dto.SocialScoreParams;
import com.socialscore.provider.service.api.PersonScoreProviderService;
import com.socialscore.provider.service.api.dto.PersonData;
import com.socialscore.provider.service.impl.params.CalculationParamsProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class PersonScoreProviderServiceImpl implements PersonScoreProviderService {

    private final CalculationParamsProvider paramsProvider;
    private final SocialScoreProducer publisher;


    @Override
    public void process(final PersonData data) {
        final double seed = paramsProvider.getSeed();
        final SocialScoreParams params = createParams(data, seed);

        publisher.publish(params);
    }

    private SocialScoreParams createParams(final PersonData data, final double seed) {
        return new SocialScoreParamsImpl(data.getFirstName(),
                                         data.getLastName(),
                                         data.getAge(),
                                         seed);
    }

}
