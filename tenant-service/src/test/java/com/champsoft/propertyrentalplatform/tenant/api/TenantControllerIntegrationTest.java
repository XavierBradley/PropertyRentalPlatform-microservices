package com.champsoft.propertyrentalplatform.tenant.api;

import com.champsoft.propertyrentalplatform.tenant.application.service.TenantCrudService;
import com.champsoft.propertyrentalplatform.tenant.application.service.TenantEligibilityService;
import com.champsoft.propertyrentalplatform.tenant.domain.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TenantController.class)
class TenantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TenantCrudService service;

    @MockBean
    private TenantEligibilityService eligibilityService;

    private Tenant sampleTenant(UUID id) {
        return new Tenant(
                TenantId.of(id),
                "John Doe",
                new CreditScore(700),
                new BankDetails(
                        "123456789012",
                        "123456789"
                )
        );
    }

    @Test
    @DisplayName("Should create tenant")
    void shouldCreateTenant() throws Exception {

        UUID id = UUID.randomUUID();
        Tenant tenant = sampleTenant(id);

        when(service.create(any(), anyInt(), any(), any()))
                .thenReturn(tenant);

        String body = """
                {
                  "name": "John Doe",
                  "score": 700,
                  "accountNumber": "123456789012",
                  "ABA": "123456789"
                }
                """;

        mockMvc.perform(post("/api/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.score").value(700));
    }

    @Test
    @DisplayName("Should get tenant by id")
    void shouldGetTenantById() throws Exception {

        UUID id = UUID.randomUUID();
        Tenant tenant = sampleTenant(id);

        when(service.getById(id)).thenReturn(tenant);

        mockMvc.perform(get("/api/tenants/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @DisplayName("Should list tenants")
    void shouldListTenants() throws Exception {

        UUID id = UUID.randomUUID();
        Tenant tenant = sampleTenant(id);

        when(service.list()).thenReturn(List.of(tenant));

        mockMvc.perform(get("/api/tenants"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should update tenant")
    void shouldUpdateTenant() throws Exception {

        UUID id = UUID.randomUUID();
        Tenant tenant = new Tenant(
                TenantId.of(id),
                "Jane Doe",
                new CreditScore(750),
                new BankDetails("999999999999", "987654321")
        );

        when(service.update(any(), any(), anyInt(), any(), any()))
                .thenReturn(tenant);

        String body = """
                {
                  "name": "Jane Doe",
                  "score": 750,
                  "accountNumber": "999999999999",
                  "ABA": "987654321"
                }
                """;

        mockMvc.perform(put("/api/tenants/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.score").value(750));
    }

    @Test
    @DisplayName("Should activate tenant")
    void shouldActivateTenant() throws Exception {

        UUID id = UUID.randomUUID();

        Tenant tenant = sampleTenant(id);
        tenant.activate();

        when(service.activate(id)).thenReturn(tenant);

        mockMvc.perform(post("/api/tenants/{id}/activate", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("Should delete tenant")
    void shouldDeleteTenant() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/tenants/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return tenant eligibility")
    void shouldReturnTenantEligibility() throws Exception {

        UUID id = UUID.randomUUID();

        when(eligibilityService.isEligible(id)).thenReturn(true);

        mockMvc.perform(get("/api/tenants/{id}/eligibility", id))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}