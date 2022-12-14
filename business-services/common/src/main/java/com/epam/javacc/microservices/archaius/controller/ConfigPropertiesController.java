package com.epam.javacc.microservices.archaius.controller;


import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigPropertiesController {

    private DynamicStringProperty propertyOneWithDynamic = DynamicPropertyFactory.getInstance().getStringProperty("archaius.properties.one", "not found");

    private DynamicStringProperty propertyWithOtherConfig = DynamicPropertyFactory.getInstance().getStringProperty("archaius.other.config", "not other config found");

    @GetMapping("/property-for-dynamic-management")
    public String getPropertyValue() {
        return propertyOneWithDynamic.getName() + ": " + propertyOneWithDynamic.get();
    }

    @GetMapping("/property-from-other-dynamic-config")
    public String getPropertyValueFromOtherConfig() {
        return propertyWithOtherConfig.getName() + ": " + propertyWithOtherConfig.get();
    }

}

