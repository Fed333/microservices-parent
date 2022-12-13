package com.epam.javacc.microservices.archaius.config.datasource;

import com.epam.javacc.microservices.archaius.config.datasource.routing.DatabaseConfigs;
import com.epam.javacc.microservices.archaius.config.datasource.routing.RoutingDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({DatabaseConfigs.class})
public class DataSourceRoutingConfiguration {

    private final DatabaseConfigs datasources;

    @Bean
    public DataSource dataSource() {
        RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setTargetDataSources(datasources.createTargetDataSources());
        return dataSource;
    }

}
