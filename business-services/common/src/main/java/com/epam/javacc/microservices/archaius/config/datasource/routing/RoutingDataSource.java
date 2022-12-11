package com.epam.javacc.microservices.archaius.config.datasource.routing;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private final DynamicStringProperty dynamicStringProperty = DynamicPropertyFactory.getInstance().getStringProperty("active.database", "tenant1");

    @Override
    protected Object determineCurrentLookupKey() {
        return dynamicStringProperty.getValue();
    }
}
