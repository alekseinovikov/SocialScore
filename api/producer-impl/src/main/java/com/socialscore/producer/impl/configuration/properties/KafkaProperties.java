package com.socialscore.producer.impl.configuration.properties;

import java.util.List;

public interface KafkaProperties {
    List<String> getBootstrapServers();
}
