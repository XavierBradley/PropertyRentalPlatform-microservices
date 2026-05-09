package com.champsoft.propertyrentalplatform.tenant.api;

import com.champsoft.propertyrentalplatform.tenant.application.exception.DuplicateTenantException;
import com.champsoft.propertyrentalplatform.tenant.application.exception.TenantNotFoundException;
import com.champsoft.propertyrentalplatform.tenant.application.service.TenantCrudService;
import com.champsoft.propertyrentalplatform.tenant.application.service.TenantEligibilityService;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TenantController.class)
@Import(TenantExceptionHandler.class)
class TenantControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TenantCrudService service;

    @MockitoBean
    private TenantEligibilityService eligibilityService;

    @Test
    @DisplayName("Should return 404 when tenant not found")
    void shouldReturn404WhenTenantNotFound() throws Exception {

        UUID id = UUID.randomUUID();

        when(service.getById(id))
                .thenThrow(
                        new TenantNotFoundException(
                                "Tenant not found: " + id
                        )
                );

        mockMvc.perform(
                        get("/api/tenants/{id}", id)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Tenant not found: " + id));
    }

    @Test
    @DisplayName("Should return 409 for duplicate tenant")
    void shouldReturn409ForDuplicateTenant() throws Exception {

        when(service.create(
                any(),
                any(Integer.class),
                any(),
                any()
        )).thenThrow(
                new DuplicateTenantException(
                        "Tenant already exists"
                )
        );

        String body = """
                {
                  "name": "John Doe",
                  "score": 700,
                  "accountNumber": "123456789012",
                  "ABA": "123456789"
                }
                """;

        mockMvc.perform(
                        post("/api/tenants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value("Tenant already exists"));
    }

    @Test
    @DisplayName("Should return 409 for low credit score")
    void shouldReturn409ForLowCreditScore() throws Exception {

        when(service.create(
                any(),
                any(Integer.class),
                any(),
                any()
        )).thenThrow(
                new TooLowCreditScoreException(
                        "Tenant needs a credit score of at leat 400 to rent a property"
                )
        );

        String body = """
                {
                  "name": "John Doe",
                  "score": 350,
                  "accountNumber": "123456789012",
                  "ABA": "123456789"
                }
                """;

        mockMvc.perform(
                        post("/api/tenants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Should return 400 for invalid tenant name")
    void shouldReturn400ForInvalidTenantName() throws Exception {

        when(service.create(
                any(),
                any(Integer.class),
                any(),
                any()
        )).thenThrow(
                new InvalidTenantNameException(
                        "Name must contain only letters"
                )
        );

        String body = """
                {
                  "name": "John123",
                  "score": 700,
                  "accountNumber": "123456789012",
                  "ABA": "123456789"
                }
                """;

        mockMvc.perform(
                        post("/api/tenants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Name must contain only letters"));
    }

    @Test
    @DisplayName("Should return 400 for invalid account number")
    void shouldReturn400ForInvalidAccountNumber() throws Exception {

        when(service.create(
                any(),
                any(Integer.class),
                any(),
                any()
        )).thenThrow(
                new InvalidAccountNumberException(
                        "account number must have 12 digits"
                )
        );

        String body = """
                {
                  "name": "John Doe",
                  "score": 700,
                  "accountNumber": "123",
                  "ABA": "123456789"
                }
                """;

        mockMvc.perform(
                        post("/api/tenants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 for invalid ABA")
    void shouldReturn400ForInvalidAba() throws Exception {

        when(service.create(
                any(),
                any(Integer.class),
                any(),
                any()
        )).thenThrow(
                new InvalidABAException(
                        "ABA must be 9 digits"
                )
        );

        String body = """
                {
                  "name": "John Doe",
                  "score": 700,
                  "accountNumber": "123456789012",
                  "ABA": "123"
                }
                """;

        mockMvc.perform(
                        post("/api/tenants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 for invalid credit score")
    void shouldReturn400ForInvalidCreditScore() throws Exception {

        when(service.create(
                any(),
                any(Integer.class),
                any(),
                any()
        )).thenThrow(
                new InvalidCreditScoreException(
                        "Credit Score cannot be less than 300"
                )
        );

        String body = """
                {
                  "name": "John Doe",
                  "score": 200,
                  "accountNumber": "123456789012",
                  "ABA": "123456789"
                }
                """;

        mockMvc.perform(
                        post("/api/tenants")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isBadRequest());
    }
}