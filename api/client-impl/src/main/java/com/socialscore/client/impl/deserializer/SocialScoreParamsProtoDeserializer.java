package com.socialscore.client.impl.deserializer;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.socialscore.client.api.proto.SocialScoreParamsProto;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class SocialScoreParamsProtoDeserializer implements Deserializer<SocialScoreParamsProto> {

    @Override
    public SocialScoreParamsProto deserialize(final String topic, final byte[] data) {
        try {
            return SocialScoreParamsProto.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            log.error("Can't deserialize SocialScoreParamsProto: ", e);
        }

        return null;
    }

}
