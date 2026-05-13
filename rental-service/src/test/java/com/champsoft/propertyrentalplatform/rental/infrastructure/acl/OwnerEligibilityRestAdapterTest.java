package com.champsoft.propertyrentalplatform.rental.infrastructure.acl;

import com.champsoft.propertyrentalplatform.rental.application.exception.CrossContextValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestClientTest(OwnerEligibilityRestAdapter.class)
@Import(OwnerEligibilityRestAdapterTest.TestRestTemplateConfig.class)
@TestPropertySource(properties = {
        "services.owners.base-url=http://localhost:9992"
})
class OwnerEligibilityRestAdapterTest {

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private OwnerEligibilityRestAdapter adapter;

    @Test
    @DisplayName("should return true when owner is eligible")
    void shouldReturnTrueWhenOwnerIsEligible() {

        UUID ownerId = UUID.randomUUID();

        mockServer.expect(
                        requestTo(
                                "http://localhost:9992/api/owners/"
                                        + ownerId
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

        boolean result = adapter.isEligible(ownerId);

        assertThat(result).isTrue();

        mockServer.verify();
    }

    @Test
    @DisplayName("should return false when owner is not eligible")
    void shouldReturnFalseWhenOwnerIsNotEligible() {

        UUID ownerId = UUID.randomUUID();

        mockServer.expect(
                        requestTo(
                                "http://localhost:9992/api/owners/"
                                        + ownerId
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

        boolean result = adapter.isEligible(ownerId);

        assertThat(result).isFalse();

        mockServer.verify();
    }

    @Test
    @DisplayName("should throw exception when owners-service fails")
    void shouldThrowExceptionWhenOwnersServiceFails() {

        UUID ownerId = UUID.randomUUID();

        mockServer.expect(
                        requestTo(
                                "http://localhost:9992/api/owners/"
                                        + ownerId
                                        + "/eligibility"
                        )
                )
                .andExpect(method(GET))
                .andRespond(withServerError());

        assertThrows(
                CrossContextValidationException.class,
                () -> adapter.isEligible(ownerId)
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
