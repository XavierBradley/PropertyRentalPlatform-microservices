package com.champsoft.propertyrentalplatform.property.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi propertiesApi() {
        return GroupedOpenApi.builder().group("properties").pathsToMatch("/api/properties/**").build();
    }
}
