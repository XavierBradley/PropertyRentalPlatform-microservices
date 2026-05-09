package com.champsoft.propertyrentalplatform.owners.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
class OwnerControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should return conflict when creating duplicate owner")
    void shouldReturnConflictForDuplicateOwner() throws Exception {

        String payload = """
                {
                  "fullName": "John Smith",
                  "address": "Montreal"
                }
                """;

        mockMvc.perform(
                        post("/api/owners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isCreated());

        mockMvc.perform(
                        post("/api/owners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("should return not found for missing owner")
    void shouldReturnNotFoundForMissingOwner() throws Exception {

        UUID missingId = UUID.randomUUID();

        mockMvc.perform(get("/api/owners/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return not found when updating missing owner")
    void shouldReturnNotFoundWhenUpdatingMissingOwner() throws Exception {

        UUID missingId = UUID.randomUUID();

        String payload = """
                {
                  "fullName": "Jane Smith",
                  "address": "Laval"
                }
                """;

        mockMvc.perform(
                        put("/api/owners/{id}", missingId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return not found when deleting missing owner")
    void shouldReturnNotFoundWhenDeletingMissingOwner() throws Exception {

        UUID missingId = UUID.randomUUID();

        mockMvc.perform(delete("/api/owners/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return bad request for invalid UUID")
    void shouldReturnBadRequestForInvalidUuid() throws Exception {

        mockMvc.perform(get("/api/owners/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }
}