package com.champsoft.propertyrentalplatform.rental.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
class RentalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should complete rental lifecycle")
    void shouldCompleteRentalLifecycle() throws Exception {

        UUID propertyId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();

        LocalDate expiry = LocalDate.now().plusMonths(6);

        String createPayload = """
                {
                  "propertyId": "%s",
                  "ownerId": "%s",
                  "tenantId": "%s",
                  "rent": 1850.0,
                  "expiry": "%s"
                }
                """.formatted(
                propertyId,
                ownerId,
                tenantId,
                expiry
        );

        MvcResult createResult = mockMvc.perform(
                        post("/api/rentals")
                                .contentType(MediaType.APPLICATION_JSON)
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

        mockMvc.perform(get("/api/rentals/{id}", rentalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rentalId));

        mockMvc.perform(get("/api/rentals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        LocalDate renewedExpiry = LocalDate.now().plusYears(1);

        String renewPayload = """
                {
                  "newExpiry": "%s"
                }
                """.formatted(renewedExpiry);

        mockMvc.perform(
                        post("/api/rentals/{id}/renew", rentalId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(renewPayload)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expiry").value(renewedExpiry.toString()));

        mockMvc.perform(post("/api/rentals/{id}/cancel", rentalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));

        mockMvc.perform(delete("/api/rentals/{id}", rentalId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/rentals/{id}", rentalId))
                .andExpect(status().isNotFound());
    }
}