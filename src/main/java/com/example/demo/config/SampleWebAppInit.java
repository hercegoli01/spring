package com.example.demo.config;

import com.vaadin.flow.spring.VaadinMVCWebAppInitializer;

import java.util.Collection;
import java.util.Collections;

public class SampleWebAppInit extends VaadinMVCWebAppInitializer {
    @Override
    protected Collection<Class<?>> getConfigurationClasses() {
        return Collections.singletonList(
                SampleConfiguration.class);
    }
}
