package com.socialscore.client.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.socialscore.client.api.dto.SocialScoreParams;
import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.client.impl.properties.KafkaClientProperties;

@ExtendWith(MockitoExtension.class)
class SocialScorePublisherClientImplTest {

    @InjectMocks
    private SocialScorePublisherClientImpl publisher;

    @Mock
    private KafkaTemplate<String, SocialScoreParamsProto> kafkaTemplate;

    @Mock
    private KafkaClientProperties properties;


    @Test
    void publish_hasAllData_callsTemplate() {
        //arrange
        final String topic = "topic";
        when(properties.getTopic()).thenReturn(topic);

        final String firstName = "firstName";
        final String lastName = "lastName";
        final int age = 45;
        final double seed = 0.3;

        final SocialScoreParams params = getParams(firstName, lastName, age, seed);
        final SocialScoreParamsProto expectedProto = SocialScoreParamsProto.newBuilder()
                                                                           .setFirstName(firstName)
                                                                           .setLastName(lastName)
                                                                           .setAge(age)
                                                                           .setSeed(seed)
                                                                           .build();

        //act
        publisher.publish(params);

        //assert
        verify(kafkaTemplate, times(1)).send(topic, expectedProto);
    }

    @Test
    void publish_hasAllDataCase2_callsTemplate() {
        //arrange
        final String topic = "topic2";
        when(properties.getTopic()).thenReturn(topic);

        final String firstName = "firstName2";
        final String lastName = "lastName2";
        final int age = 452;
        final double seed = 0.2;

        final SocialScoreParams params = getParams(firstName, lastName, age, seed);
        final SocialScoreParamsProto expectedProto = SocialScoreParamsProto.newBuilder()
                                                                           .setFirstName(firstName)
                                                                           .setLastName(lastName)
                                                                           .setAge(age)
                                                                           .setSeed(seed)
                                                                           .build();

        //act
        publisher.publish(params);

        //assert
        verify(kafkaTemplate, times(1)).send(topic, expectedProto);
    }

    private SocialScoreParams getParams(final String firstName,
                                        final String lastName,
                                        final int age,
                                        final double seed) {
        return new SocialScoreParams() {
            @Override
            public String getFirstName() {
                return firstName;
            }

            @Override
            public String getLastName() {
                return lastName;
            }

            @Override
            public int getAge() {
                return age;
            }

            @Override
            public double getSeed() {
                return seed;
            }
        };
    }
}