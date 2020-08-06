package com.socialscore.client.impl.deserializer;

import org.apache.kafka.common.serialization.Serializer;

import com.socialscore.client.api.proto.SocialScoreParamsProto;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class SocialScoreParamsProtoDeserializer implements Serializer<SocialScoreParamsProto> {

    @Override
    public byte[] serialize(final String topic, final SocialScoreParamsProto data) {
        return data.toByteArray();
    }

}
