package com.socialscore.client.impl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@ConfigurationProperties("kafka")
@Component
public class KafkaClientPropertiesImpl implements KafkaClientProperties {

    private String topic;

}
