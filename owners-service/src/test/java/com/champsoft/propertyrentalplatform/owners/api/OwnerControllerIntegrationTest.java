package com.champsoft.propertyrentalplatform.owners.api;

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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
class OwnerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should complete full owner lifecycle")
    void shouldCompleteOwnerLifecycle() throws Exception {

        String createPayload = """
                {
                  "fullName": "John Smith",
                  "address": "Montreal"
                }
                """;

        MvcResult createResult = mockMvc.perform(
                        post("/api/owners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createPayload)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.fullName").value("John Smith"))
                .andExpect(jsonPath("$.address").value("Montreal"))
                .andReturn();

        String response = createResult.getResponse().getContentAsString();

        JsonNode json = objectMapper.readTree(response);

        String id = json.get("id").asText();

        mockMvc.perform(get("/api/owners/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.fullName").value("John Smith"));

        mockMvc.perform(get("/api/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        String updatePayload = """
                {
                  "fullName": "Jane Smith",
                  "address": "Laval"
                }
                """;

        mockMvc.perform(
                        put("/api/owners/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatePayload)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Jane Smith"))
                .andExpect(jsonPath("$.address").value("Laval"));

        mockMvc.perform(post("/api/owners/{id}/deactivate", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("INACTIVE"));

        mockMvc.perform(post("/api/owners/{id}/activate", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        mockMvc.perform(delete("/api/owners/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/owners/{id}", id))
                .andExpect(status().isNotFound());
    }
}