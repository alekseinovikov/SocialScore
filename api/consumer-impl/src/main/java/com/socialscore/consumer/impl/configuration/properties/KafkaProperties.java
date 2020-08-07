package com.socialscore.consumer.impl.configuration.properties;

import java.util.List;

public interface KafkaProperties {
    List<String> getBootstrapServers();
    String getGroupId();
    String getTopic();
}
