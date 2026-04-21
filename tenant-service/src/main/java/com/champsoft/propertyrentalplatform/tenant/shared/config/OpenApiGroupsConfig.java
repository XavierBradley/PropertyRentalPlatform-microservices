package com.champsoft.propertyrentalplatform.shared.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi propertiesApi() {
        return GroupedOpenApi.builder().group("properties").pathsToMatch("/api/properties/**").build();
    }

    @Bean
    GroupedOpenApi ownersApi() {
        return GroupedOpenApi.builder().group("owners").pathsToMatch("/api/owners/**").build();
    }

    @Bean
    GroupedOpenApi tenantsApi() {
        return GroupedOpenApi.builder().group("tenants").pathsToMatch("/api/tenants/**").build();
    }

    @Bean
    GroupedOpenApi rentalsApi() {
        return GroupedOpenApi.builder().group("rentals").pathsToMatch("/api/rentals/**").build();
    }
}
