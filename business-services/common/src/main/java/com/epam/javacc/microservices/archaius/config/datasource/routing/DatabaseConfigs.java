package com.epam.javacc.microservices.archaius.config.datasource.routing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@Setter
@ConfigurationProperties(prefix = "spring")
@RequiredArgsConstructor
public class DatabaseConfigs {

    private Map<String, DatabaseConfig> datasources = new HashMap<>();

    public Map<Object, Object> createTargetDataSources() {
        return datasources.entrySet().stream()
                .map(e->new Object[]{e.getKey(), e.getValue().createDataSource()})
                .collect(Collectors.toMap(o->o[0],o->o[1]));
    }

}
