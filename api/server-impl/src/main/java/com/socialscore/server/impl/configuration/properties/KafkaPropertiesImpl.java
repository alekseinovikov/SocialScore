package com.socialscore.server.impl.configuration.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KafkaPropertiesImpl implements KafkaProperties {

    private List<String> bootstrapServers;
    private String groupId;
    private String topic;

}
