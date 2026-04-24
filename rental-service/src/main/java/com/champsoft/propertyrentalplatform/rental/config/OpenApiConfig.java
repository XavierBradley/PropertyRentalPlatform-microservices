package com.champsoft.propertyrentalplatform.rental.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rentalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Rental Service")
                        .version("1.0.0")
                        .description("Rental Service")
                        .contact(new Contact().name("L&X").email("n/a"))
                        .license(new License().name("Apache 2.0")));
    }
}
