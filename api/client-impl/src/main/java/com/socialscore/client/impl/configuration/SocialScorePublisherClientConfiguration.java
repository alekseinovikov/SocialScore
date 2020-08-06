package com.socialscore.client.impl.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.client.impl.SocialScorePublisherClientImpl;
import com.socialscore.client.impl.configuration.properties.KafkaProperties;
import com.socialscore.client.impl.configuration.properties.KafkaPropertiesImpl;
import com.socialscore.client.impl.serializer.SocialScoreParamsProtoSerializer;

@ComponentScan(basePackageClasses = SocialScorePublisherClientImpl.class)
@Configuration
public class SocialScorePublisherClientConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public KafkaProperties kafkaProperties(final org.springframework.boot.autoconfigure.kafka.KafkaProperties kafkaPropertiesSpring) {
        final KafkaPropertiesImpl kafkaProperties = new KafkaPropertiesImpl();

        kafkaProperties.setBootstrapServers(kafkaPropertiesSpring.getBootstrapServers());

        return kafkaProperties;
    }

    @Bean
    public ProducerFactory<String, SocialScoreParamsProto> socialScoreProducer(final KafkaProperties kafkaProperties) {
        final Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SocialScoreParamsProtoSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, SocialScoreParamsProto> socialScoreParamsProtoKafkaTemplate(final ProducerFactory<String, SocialScoreParamsProto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
