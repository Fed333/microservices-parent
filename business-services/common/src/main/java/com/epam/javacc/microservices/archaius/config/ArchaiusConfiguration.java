package com.epam.javacc.microservices.archaius.config;

import com.netflix.config.*;
import com.netflix.config.sources.URLConfigurationSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URL;

@Slf4j
@Configuration
public class ArchaiusConfiguration {

    @Bean
    public AbstractConfiguration addApplicationPropertiesSource() throws IOException {
        URL configPropertyURL = (new ClassPathResource("other-config.properties")).getURL();
        URL routingDatasourceURL = (new ClassPathResource("routing-datasource.properties")).getURL();
        PolledConfigurationSource source = new URLConfigurationSource(configPropertyURL, routingDatasourceURL);

        FixedDelayPollingScheduler scheduler = new FixedDelayPollingScheduler(10000, 10000, false);

        ConfigurationManager.getConfigInstance().addConfigurationListener(configurationEvent -> {
            log.info("configurationChanged invoked!");
            log.info("configurationEvent: {}", configurationEvent);
            log.info("property: {}, value: {}", configurationEvent.getPropertyName(), configurationEvent.getPropertyValue());
            log.info("end of configurationChanged.");
        });

        return new DynamicConfiguration(source, scheduler);
    }

}
