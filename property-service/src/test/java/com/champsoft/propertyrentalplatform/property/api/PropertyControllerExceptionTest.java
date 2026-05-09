package com.champsoft.propertyrentalplatform.property.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
class PropertyControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return conflict for duplicate address")
    void shouldReturnConflictForDuplicateAddress() throws Exception {

        String payload = """
                {
                  "tax": 2500.0,
                  "address": "123 Main Street"
                }
                """;

        mockMvc.perform(
                        post("/api/properties")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isOk());

        mockMvc.perform(
                        post("/api/properties")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Should return not found for missing property")
    void shouldReturnNotFoundForMissingProperty() throws Exception {

        UUID missingId = UUID.randomUUID();

        mockMvc.perform(get("/api/properties/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return not found when updating missing property")
    void shouldReturnNotFoundWhenUpdatingMissingProperty() throws Exception {

        UUID missingId = UUID.randomUUID();

        String payload = """
                {
                  "tax": 3000.0,
                  "address": "456 Park Avenue"
                }
                """;

        mockMvc.perform(
                        put("/api/properties/{id}", missingId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return not found when deleting missing property")
    void shouldReturnNotFoundWhenDeletingMissingProperty() throws Exception {

        UUID missingId = UUID.randomUUID();

        mockMvc.perform(delete("/api/properties/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return bad request for invalid UUID")
    void shouldReturnBadRequestForInvalidUuid() throws Exception {

        mockMvc.perform(get("/api/properties/{id}", "invalid-uuid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request for invalid tax")
    void shouldReturnBadRequestForInvalidTax() throws Exception {

        String payload = """
                {
                  "tax": -100.0,
                  "address": "123 Main Street"
                }
                """;

        mockMvc.perform(
                        post("/api/properties")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request for blank address")
    void shouldReturnBadRequestForBlankAddress() throws Exception {

        String payload = """
                {
                  "tax": 2500.0,
                  "address": ""
                }
                """;

        mockMvc.perform(
                        post("/api/properties")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isBadRequest());
    }
}