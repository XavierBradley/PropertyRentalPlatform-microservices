package com.champsoft.propertyrentalplatform.rental.infrastructure.acl;

import com.champsoft.propertyrentalplatform.rental.application.exception.CrossContextValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(PropertyEligibilityRestAdapter.class)
@Import(PropertyEligibilityRestAdapterTest.TestRestTemplateConfig.class)
@TestPropertySource(properties = {
        "services.property.base-url=http://localhost:9991"
})
class PropertyEligibilityRestAdapterTest {

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private PropertyEligibilityRestAdapter adapter;

    @Test
    @DisplayName("should return true when property is eligible")
    void shouldReturnTrueWhenPropertyIsEligible() {

        UUID propertyId = UUID.randomUUID();

        mockServer.expect(
                        requestTo(
                                "http://localhost:9991/api/properties/"
                                        + propertyId
                                        + "/eligibility"
                        )
                )
                .andExpect(method(GET))
                .andRespond(
                        withSuccess(
                                "true",
                                MediaType.APPLICATION_JSON
                        )
                );

        boolean result = adapter.isEligible(propertyId);

        assertThat(result).isTrue();

        mockServer.verify();
    }

    @Test
    @DisplayName("should return false when property is not eligible")
    void shouldReturnFalseWhenPropertyIsNotEligible() {

        UUID propertyId = UUID.randomUUID();

        mockServer.expect(
                        requestTo(
                                "http://localhost:9991/api/properties/"
                                        + propertyId
                                        + "/eligibility"
                        )
                )
                .andExpect(method(GET))
                .andRespond(
                        withSuccess(
                                "false",
                                MediaType.APPLICATION_JSON
                        )
                );

        boolean result = adapter.isEligible(propertyId);

        assertThat(result).isFalse();

        mockServer.verify();
    }

    @Test
    @DisplayName("should throw exception when properties-service fails")
    void shouldThrowExceptionWhenPropertiesServiceFails() {

        UUID propertyId = UUID.randomUUID();

        mockServer.expect(
                        requestTo(
                                "http://localhost:9991/api/properties/"
                                        + propertyId
                                        + "/eligibility"
                        )
                )
                .andExpect(method(GET))
                .andRespond(withServerError());

        assertThrows(
                CrossContextValidationException.class,
                () -> adapter.isEligible(propertyId)
        );

        mockServer.verify();
    }

    @TestConfiguration
    static class TestRestTemplateConfig {

        @Bean
        RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }
    }
}