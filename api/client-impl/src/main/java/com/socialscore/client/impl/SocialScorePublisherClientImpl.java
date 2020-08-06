package com.socialscore.client.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.socialscore.client.api.SocialScorePublisherClient;
import com.socialscore.client.api.dto.SocialScoreParams;
import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.client.impl.properties.KafkaClientProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SocialScorePublisherClientImpl implements SocialScorePublisherClient {

    private final KafkaTemplate<String, SocialScoreParamsProto> kafkaTemplate;
    private final KafkaClientProperties properties;


    @Override
    public void publish(final SocialScoreParams params) {
        final SocialScoreParamsProto proto = convert(params);

        kafkaTemplate.send(properties.getTopic(), proto);
    }

    private SocialScoreParamsProto convert(final SocialScoreParams params) {
        return SocialScoreParamsProto.newBuilder()
                                     .setFirstName(params.getFirstName())
                                     .setLastName(params.getLastName())
                                     .setAge(params.getAge())
                                     .setSeed(params.getSeed())
                                     .build();
    }

}
