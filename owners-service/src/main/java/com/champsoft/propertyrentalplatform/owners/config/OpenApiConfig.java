package com.champsoft.propertyrentalplatform.owners.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ownersOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Owners Service")
                        .version("1.0.0")
                        .description("Owners Service")
                        .contact(new Contact().name("L&X").email("n/a"))
                        .license(new License().name("Apache 2.0")));
    }
}
