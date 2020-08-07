package com.socialscore.populator.impl.configuration.properties;

import lombok.Value;

@Value
public class RedisProperties {
    String host;
    int port;
}
