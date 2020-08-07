package com.socialscore.server.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.server.api.SocialScoreConsumerClient;
import com.socialscore.server.api.dto.SocialScoreParams;
import com.socialscore.server.impl.configuration.properties.KafkaProperties;
import com.socialscore.server.impl.dto.SocialScoreParamsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Component
public class SocialScoreConsumerServerImpl implements SocialScoreConsumerClient {

    private final List<Consumer<SocialScoreParams>> consumers = new ArrayList<>();
    private final KafkaProperties kafkaServerProperties;


    @Override
    public void subscribe(final Consumer<SocialScoreParams> consumer) {
        consumers.add(consumer);
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
    public void receiveMessage(final SocialScoreParamsProto message) {
        final SocialScoreParams params = convert(message);

        consumers.forEach(consumer -> consumer.accept(params));
    }

    private SocialScoreParams convert(final SocialScoreParamsProto proto) {
        return new SocialScoreParamsImpl(
                proto.getFirstName(),
                proto.getLastName(),
                proto.getAge(),
                proto.getSeed()
        );
    }

}
