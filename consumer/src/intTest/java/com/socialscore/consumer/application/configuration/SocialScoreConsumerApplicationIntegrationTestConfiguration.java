package com.socialscore.consumer.application.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;

import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.consumer.application.SocialScoreConsumerApplication;
import com.socialscore.consumer.impl.configuration.properties.KafkaProperties;
import com.socialscore.consumer.impl.configuration.properties.KafkaPropertiesImpl;
import com.socialscore.score.consumer.impl.configuration.properties.RedisProperties;

@Configuration
@Import(SocialScoreConsumerApplication.class)
public class SocialScoreConsumerApplicationIntegrationTestConfiguration {

    @Bean("redisContainer")
    public GenericContainer redisContainer() {
        final GenericContainer redisContainer = new GenericContainer("redis:5.0.3-alpine")
                .withExposedPorts(6379);

        redisContainer.start();

        return redisContainer;
    }

    @Primary
    @Bean
    public RedisProperties redisProperties(@Qualifier("redisContainer") final GenericContainer redisContainer) {
        return new RedisProperties(redisContainer.getHost(),
                                   redisContainer.getMappedPort(6379));
    }

    @Bean
    public KafkaContainer kafkaContainer() {
        final KafkaContainer kafkaContainer = new KafkaContainer("5.3.1");

        kafkaContainer.start();

        return kafkaContainer;
    }

    @Primary
    @Bean
    public KafkaProperties kafkaServerProperties(final KafkaContainer kafkaContainer) {
        final KafkaPropertiesImpl properties = new KafkaPropertiesImpl();

        properties.setBootstrapServers(List.of(kafkaContainer.getBootstrapServers()));

        return properties;
    }

    @Bean
    public ProducerFactory<String, SocialScoreParamsProto> protoProducerFactory(final KafkaProperties kafkaProperties) {
        final Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SocialScoreParamsProtoSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, SocialScoreParamsProto> kafkaTemplate(final ProducerFactory<String, SocialScoreParamsProto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    public static class SocialScoreParamsProtoSerializer implements Serializer<SocialScoreParamsProto> {
        @Override
        public byte[] serialize(final String topic, final SocialScoreParamsProto data) {
            return data.toByteArray();
        }
    }

}
