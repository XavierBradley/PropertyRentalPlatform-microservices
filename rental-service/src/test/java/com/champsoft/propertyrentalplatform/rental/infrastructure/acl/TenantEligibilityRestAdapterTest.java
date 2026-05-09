package com.champsoft.propertyrentalplatform.rental.infrastructure.acl;

import com.champsoft.propertyrentalplatform.rental.application.exception.CrossContextValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
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

@RestClientTest(TenantEligibilityRestAdapter.class)
@Import(TenantEligibilityRestAdapterTest.TestRestTemplateConfig.class)
@TestPropertySource(properties = {
        "services.tenants.base-url=http://localhost:9993"
})
class TenantEligibilityRestAdapterTest {

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private TenantEligibilityRestAdapter adapter;

    @Test
    @DisplayName("should return true when tenant is eligible")
    void shouldReturnTrueWhenTenantIsEligible() {

        UUID tenantId = UUID.randomUUID();

        mockServer.expect(
                        requestTo(
                                "http://localhost:9993/api/tenants/"
                                        + tenantId
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

        boolean result = adapter.isEligible(tenantId);

        assertThat(result).isTrue();

        mockServer.verify();
    }

    @Test
    @DisplayName("should return false when tenant is not eligible")
    void shouldReturnFalseWhenTenantIsNotEligible() {

        UUID tenantId = UUID.randomUUID();

        mockServer.expect(
                        requestTo(
                                "http://localhost:9993/api/tenants/"
                                        + tenantId
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

        boolean result = adapter.isEligible(tenantId);

        assertThat(result).isFalse();

        mockServer.verify();
    }

    @Test
    @DisplayName("should throw exception when tenants-service fails")
    void shouldThrowExceptionWhenTenantsServiceFails() {

        UUID tenantId = UUID.randomUUID();

        mockServer.expect(
                        requestTo(
                                "http://localhost:9993/api/tenants/"
                                        + tenantId
                                        + "/eligibility"
                        )
                )
                .andExpect(method(GET))
                .andRespond(withServerError());

        assertThrows(
                CrossContextValidationException.class,
                () -> adapter.isEligible(tenantId)
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