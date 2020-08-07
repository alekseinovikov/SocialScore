package com.socialscore.consumer.impl.deserializer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.socialscore.client.api.proto.SocialScoreParamsProto;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

@Slf4j

public class SocialScoreParamsProtoDeserializer implements Deserializer<SocialScoreParamsProto> {

    @Override
    public SocialScoreParamsProto deserialize(String topic, byte[] data) {
        try {
            return SocialScoreParamsProto.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            log.error("ERROR ON DESERIALIZATION: ", e);
        }

        return null;
    }

}
