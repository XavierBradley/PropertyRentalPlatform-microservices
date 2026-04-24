package com.champsoft.propertyrentalplatform.tenant.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi tenantsApi() {
        return GroupedOpenApi.builder().group("tenants").pathsToMatch("/api/tenants/**").build();
    }
}
