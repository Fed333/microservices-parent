package com.epam.javacc.microservices.archaius.config;

import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.URLConfigurationSource;
import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URL;

@Configuration
public class ArchaiusConfiguration {

    @Bean
    public AbstractConfiguration addApplicationPropertiesSource() throws IOException {
        URL configPropertyURL = (new ClassPathResource("other-config.properties")).getURL();
        URL routingDatasourceURL = (new ClassPathResource("routing-datasource.properties")).getURL();
        PolledConfigurationSource source = new URLConfigurationSource(configPropertyURL, routingDatasourceURL);

        return new DynamicConfiguration(source, new FixedDelayPollingScheduler(10000, 10000, false));
    }

}
