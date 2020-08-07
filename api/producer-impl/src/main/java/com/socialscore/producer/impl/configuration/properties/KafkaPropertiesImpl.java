package com.socialscore.producer.impl.configuration.properties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaPropertiesImpl implements KafkaProperties {

    private List<String> bootstrapServers;

}
