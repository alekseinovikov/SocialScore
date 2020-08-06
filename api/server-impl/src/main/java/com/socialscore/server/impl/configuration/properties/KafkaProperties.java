package com.socialscore.server.impl.configuration.properties;

import java.util.List;

public interface KafkaProperties {
    List<String> getBootstrapServers();
    String getGroupId();
    String getTopic();
}
