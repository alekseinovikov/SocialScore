package com.socialscore.provider.service.impl.params;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.socialscore.provider.service.impl.properties.CalculationProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component
public class CalculationParamsProviderImpl implements CalculationParamsProvider {

    private final CalculationProperties properties;
    private LoadingCache<String, Properties> propertiesCache;

    private static final String SEED_PROPERTY_NAME = "seed";

    public CalculationParamsProviderImpl(final CalculationProperties properties) {
        this.properties = properties;

    }

    @PostConstruct
    public void init() {
        this.propertiesCache = Caffeine.newBuilder()
                                       .maximumSize(1)
                                       .refreshAfterWrite(this.properties.refreshRateSeconds(), TimeUnit.SECONDS)
                                       .build(this::loadProperties);
    }

    @Override
    public double getSeed() {
        final Properties properties = propertiesCache.get(this.properties.getFileName());
        Assert.notNull(properties, "Cache returned null properties!");

        final double seed = Double.parseDouble(properties.getProperty(SEED_PROPERTY_NAME));
        validateSeed(seed);

        return seed;
    }

    private Properties loadProperties(final String file) throws IOException {
        final Properties properties = new Properties();
        try (final InputStream propertiesFileStream = getClass().getClassLoader().getResourceAsStream(file)) {
            properties.load(propertiesFileStream);
        } catch (IOException e) {
            log.error("ERROR ON READING CALCULATION PROPERTIES FILE: ", e);
            throw e;
        }

        return properties;
    }

    private void validateSeed(final double seed) {
        if (seed < 0 || seed > 1) {
            throw new IllegalStateException("Seed must be between 0 and 1 including");
        }
    }

}
