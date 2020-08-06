package com.socialscore.provider.application.configuration;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.KafkaContainer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.client.impl.configuration.properties.KafkaProperties;
import com.socialscore.client.impl.configuration.properties.KafkaPropertiesImpl;
import com.socialscore.provider.application.SocialScoreProviderApplication;

@Import(SocialScoreProviderApplication.class)
@Configuration
public class SocialScoreProviderApplicationIntegrationTestConfiguration {

    @Value("${kafka.group-id}")
    private String kafkaGroupId;


    @Primary
    @Bean
    public KafkaProperties kafkaProperties(final KafkaContainer kafkaContainer) {
        final KafkaPropertiesImpl properties = new KafkaPropertiesImpl();

        properties.setBootstrapServers(List.of(kafkaContainer.getBootstrapServers()));

        return properties;
    }

    @Bean
    public KafkaContainer kafkaContainer() {
        final KafkaContainer kafkaContainer = new KafkaContainer("5.3.1");

        kafkaContainer.start();

        return kafkaContainer;
    }

    @Bean
    public KafkaConsumer<String, SocialScoreParamsProto> kafkaConsumer(final KafkaProperties kafkaProperties) {
        final Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", kafkaProperties.getBootstrapServers()));
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SocialScoreParamsProtoDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);

        return new KafkaConsumer<>(properties);
    }

    public static class SocialScoreParamsProtoDeserializer implements Deserializer<SocialScoreParamsProto> {

        @Override
        public SocialScoreParamsProto deserialize(final String topic, final byte[] data) {
            try {
                return SocialScoreParamsProto.parseFrom(data);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
