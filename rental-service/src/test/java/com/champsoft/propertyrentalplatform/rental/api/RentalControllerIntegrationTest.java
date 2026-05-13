package com.champsoft.propertyrentalplatform.rental.api;

import com.champsoft.propertyrentalplatform.rental.application.port.out.OwnerEligibilityPort;
import com.champsoft.propertyrentalplatform.rental.application.port.out.PropertyEligibilityPort;
import com.champsoft.propertyrentalplatform.rental.application.port.out.TenantEligibilityPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
class RentalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private OwnerEligibilityPort ownerEligibilityPort;

    @MockBean
    private PropertyEligibilityPort propertyEligibilityPort;

    @MockBean
    private TenantEligibilityPort tenantEligibilityPort;

    @BeforeEach
    void setup() {
        when(ownerEligibilityPort.isEligible(any(UUID.class))).thenReturn(true);
        when(propertyEligibilityPort.isEligible(any(UUID.class))).thenReturn(true);
        when(tenantEligibilityPort.isEligible(any(UUID.class))).thenReturn(true);
    }

    @Test
    @DisplayName("should complete rental lifecycle")
    void shouldCompleteRentalLifecycle() throws Exception {

        // ---------------- TEST DATA ----------------
        UUID propertyId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();

        LocalDate expiry = LocalDate.now().plusMonths(6);
        LocalDate renewedExpiry = LocalDate.now().plusYears(1);

        String createPayload = """
                {
                  "propertyId": "%s",
                  "ownerId": "%s",
                  "tenantId": "%s",
                  "rent": 1850.0,
                  "expiry": "%s"
                }
                """.formatted(propertyId, ownerId, tenantId, expiry);

        // ---------------- CREATE RENTAL ----------------
        MvcResult createResult = mockMvc.perform(
                        post("/api/rentals")
                                .contentType(APPLICATION_JSON)
                                .content(createPayload)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.propertyId").value(propertyId.toString()))
                .andExpect(jsonPath("$.ownerId").value(ownerId.toString()))
                .andExpect(jsonPath("$.tenantId").value(tenantId.toString()))
                .andExpect(jsonPath("$.rent").value(1850.0))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andReturn();

        String response = createResult.getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(response);
        String rentalId = json.get("id").asText();

        // ---------------- GET BY ID ----------------
        mockMvc.perform(get("/api/rentals/{id}", rentalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rentalId));

        // ---------------- GET ALL (FIXED HAL ASSERTION) ----------------
        mockMvc.perform(get("/api/rentals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.rentalResponseList").exists())
                .andExpect(jsonPath("$._embedded.rentalResponseList[?(@.id=='" + rentalId + "')]").exists());

        // ---------------- RENEW RENTAL ----------------
        String renewPayload = """
                {
                  "newExpiry": "%s"
                }
                """.formatted(renewedExpiry);

        mockMvc.perform(
                        post("/api/rentals/{id}/renew", rentalId)
                                .contentType(APPLICATION_JSON)
                                .content(renewPayload)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expiry").value(renewedExpiry.toString()));

        // ---------------- CANCEL RENTAL ----------------
        mockMvc.perform(post("/api/rentals/{id}/cancel", rentalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));

        // ---------------- DELETE RENTAL ----------------
        mockMvc.perform(delete("/api/rentals/{id}", rentalId))
                .andExpect(status().isNoContent());

        // ---------------- VERIFY DELETED ----------------
        mockMvc.perform(get("/api/rentals/{id}", rentalId))
                .andExpect(status().isNotFound());
    }
}