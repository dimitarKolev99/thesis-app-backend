package com.liebherr.hau.localapibackend.resources;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class is supposed to help access properties from the application.yml and
 * make access easier.
 */
@Component
public class ApplicationProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);

    public static ApplicationProperties staticProperties;

    private boolean swagger;
    private Environment environment;

    @PostConstruct
    private void init() {
        ApplicationProperties.staticProperties = this;
    }

    @Value("${ui.environment}")
    private void setEnvironment(String environment) {
        this.environment = Environment.valueOf(environment.toUpperCase());

        LOGGER.info("environment: {}", this.environment);
    }

    @Value("${ui.swagger}")
    private void setSwagger(String environment) {
        this.swagger = Boolean.valueOf(environment);

        LOGGER.info("swagger: {}", this.swagger);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public boolean enableSwagger() {
        return this.swagger;
    }

    public enum Environment {
        DEVELOPMENT, STABLE, PRODUCTION;

        public boolean isDevelopment() {
            return this == Environment.DEVELOPMENT;
        }

        public boolean isStable() {
            return this == Environment.STABLE;
        }

        public boolean isProduction() {
            return this == Environment.PRODUCTION;
        }

        public boolean isNotProduction() {
            return this != Environment.PRODUCTION;
        }
    }
}
