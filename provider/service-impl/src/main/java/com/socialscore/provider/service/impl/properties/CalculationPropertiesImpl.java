package com.socialscore.provider.service.impl.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(value = "calculation.properties", ignoreUnknownFields = false)
@Component
public class CalculationPropertiesImpl implements CalculationProperties {

    private String fileName;
    private int refreshRateSeconds;

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public int refreshRateSeconds() {
        return this.refreshRateSeconds;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setRefreshRateSeconds(final int refreshRateSeconds) {
        this.refreshRateSeconds = refreshRateSeconds;
    }

}
