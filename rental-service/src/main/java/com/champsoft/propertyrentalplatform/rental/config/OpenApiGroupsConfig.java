package com.champsoft.propertyrentalplatform.rental.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi rentalsApi() {
        return GroupedOpenApi.builder().group("rentals").pathsToMatch("/api/rentals/**").build();
    }
}
