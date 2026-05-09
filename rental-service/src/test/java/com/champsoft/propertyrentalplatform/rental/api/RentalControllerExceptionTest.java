package com.champsoft.propertyrentalplatform.rental.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
class RentalControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should return unprocessable entity when property is not eligible")
    void shouldReturnUnprocessableEntityWhenPropertyNotEligible() throws Exception {

        UUID propertyId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();

        String payload = """
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
                LocalDate.now().plusMonths(6)
        );

        mockMvc.perform(
                        post("/api/rentals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("should return not found for missing rental")
    void shouldReturnNotFoundForMissingRental() throws Exception {

        UUID missingId = UUID.randomUUID();

        mockMvc.perform(get("/api/rentals/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return not found when renewing missing rental")
    void shouldReturnNotFoundWhenRenewingMissingRental() throws Exception {

        UUID missingId = UUID.randomUUID();

        String payload = """
                {
                  "newExpiry": "%s"
                }
                """.formatted(LocalDate.now().plusMonths(6));

        mockMvc.perform(
                        post("/api/rentals/{id}/renew", missingId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return not found when cancelling missing rental")
    void shouldReturnNotFoundWhenCancellingMissingRental() throws Exception {

        UUID missingId = UUID.randomUUID();

        mockMvc.perform(post("/api/rentals/{id}/cancel", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return not found when deleting missing rental")
    void shouldReturnNotFoundWhenDeletingMissingRental() throws Exception {

        UUID missingId = UUID.randomUUID();

        mockMvc.perform(delete("/api/rentals/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return bad request for invalid UUID")
    void shouldReturnBadRequestForInvalidUuid() throws Exception {

        mockMvc.perform(get("/api/rentals/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return bad request for past expiry date")
    void shouldReturnBadRequestForPastExpiryDate() throws Exception {

        UUID propertyId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();

        String payload = """
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
                LocalDate.now().minusDays(1)
        );

        mockMvc.perform(
                        post("/api/rentals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return bad request for invalid rent")
    void shouldReturnBadRequestForInvalidRent() throws Exception {

        UUID propertyId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();

        String payload = """
                {
                  "propertyId": "%s",
                  "ownerId": "%s",
                  "tenantId": "%s",
                  "rent": -100.0,
                  "expiry": "%s"
                }
                """.formatted(
                propertyId,
                ownerId,
                tenantId,
                LocalDate.now().plusMonths(6)
        );

        mockMvc.perform(
                        post("/api/rentals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isBadRequest());
    }
}