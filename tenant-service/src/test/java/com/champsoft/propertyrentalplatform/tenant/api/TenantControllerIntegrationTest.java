package com.champsoft.propertyrentalplatform.tenant.api;

import com.champsoft.propertyrentalplatform.tenant.application.port.out.TenantRepositoryPort;
import com.champsoft.propertyrentalplatform.tenant.domain.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TenantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TenantRepositoryPort repo;

    private Tenant tenant;

    @BeforeEach
    void setUp() {

        tenant = new Tenant(
                TenantId.of(UUID.randomUUID()),
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

        when(repo.existsByName("John Doe"))
                .thenReturn(false);

        when(repo.save(any(Tenant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.score").value(700))
                .andExpect(jsonPath("$.status").value("INACTIVE"));
    }

    @Test
    @DisplayName("Should get tenant by id")
    void shouldGetTenantById() throws Exception {

        when(repo.findById(tenant.id()))
                .thenReturn(Optional.of(tenant));

        mockMvc.perform(
                        get("/api/tenants/{id}", tenant.id().value())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tenant.id().value().toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.score").value(700));
    }

    @Test
    @DisplayName("Should list tenants")
    void shouldListTenants() throws Exception {

        Tenant second = new Tenant(
                TenantId.of(UUID.randomUUID()),
                "Jane Doe",
                new CreditScore(750),
                new BankDetails(
                        "999999999999",
                        "987654321"
                )
        );

        when(repo.findAll())
                .thenReturn(List.of(tenant, second));

        mockMvc.perform(get("/api/tenants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Should update tenant")
    void shouldUpdateTenant() throws Exception {

        when(repo.findById(tenant.id()))
                .thenReturn(Optional.of(tenant));

        when(repo.save(any(Tenant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String body = """
                {
                  "name": "Jane Doe",
                  "score": 750,
                  "accountNumber": "999999999999",
                  "ABA": "987654321"
                }
                """;

        mockMvc.perform(
                        put("/api/tenants/{id}", tenant.id().value())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.score").value(750));
    }

    @Test
    @DisplayName("Should activate tenant")
    void shouldActivateTenant() throws Exception {

        when(repo.findById(tenant.id()))
                .thenReturn(Optional.of(tenant));

        when(repo.save(any(Tenant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(
                        post("/api/tenants/{id}/activate", tenant.id().value())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("Should delete tenant")
    void shouldDeleteTenant() throws Exception {

        when(repo.findById(tenant.id()))
                .thenReturn(Optional.of(tenant));

        mockMvc.perform(
                        delete("/api/tenants/{id}", tenant.id().value())
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return tenant eligibility")
    void shouldReturnTenantEligibility() throws Exception {

        tenant.activate();

        when(repo.findById(tenant.id()))
                .thenReturn(Optional.of(tenant));

        mockMvc.perform(
                        get("/api/tenants/{id}/eligibility", tenant.id().value())
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}