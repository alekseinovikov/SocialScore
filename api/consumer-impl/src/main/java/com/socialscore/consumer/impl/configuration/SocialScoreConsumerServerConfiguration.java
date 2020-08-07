package com.socialscore.consumer.impl.configuration;

import com.socialscore.client.api.proto.SocialScoreParamsProto;
import com.socialscore.consumer.impl.SocialScoreConsumerServerImpl;
import com.socialscore.consumer.impl.configuration.properties.KafkaProperties;
import com.socialscore.consumer.impl.configuration.properties.KafkaPropertiesImpl;
import com.socialscore.consumer.impl.deserializer.SocialScoreParamsProtoDeserializer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@ComponentScan(basePackageClasses = SocialScoreConsumerServerImpl.class)
@Configuration
public class SocialScoreConsumerServerConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public KafkaProperties kafkaServerProperties(final org.springframework.boot.autoconfigure.kafka.KafkaProperties kafkaPropertiesSpring,
                                                 @Value("${kafka.topic}") final String topic) {
        final KafkaPropertiesImpl kafkaProperties = new KafkaPropertiesImpl();

        kafkaProperties.setBootstrapServers(kafkaPropertiesSpring.getBootstrapServers());
        kafkaProperties.setGroupId(kafkaPropertiesSpring.getConsumer().getGroupId());
        kafkaProperties.setTopic(topic);

        return kafkaProperties;
    }

    @Bean
    public ConsumerFactory<String, SocialScoreParamsProto> consumerFactory(final KafkaProperties kafkaProperties) {
        final Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SocialScoreParamsProtoDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SocialScoreParamsProto> kafkaListenerContainerFactory(final ConsumerFactory<String, SocialScoreParamsProto> consumerFactory) {
        final ConcurrentKafkaListenerContainerFactory<String, SocialScoreParamsProto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }

}
