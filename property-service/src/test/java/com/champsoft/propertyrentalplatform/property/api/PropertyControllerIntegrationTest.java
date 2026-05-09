package com.champsoft.propertyrentalplatform.property.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
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
class PropertyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should complete full property lifecycle")
    void shouldCompletePropertyLifecycle() throws Exception {

        String createPayload = """
                {
                  "tax": 2500.0,
                  "address": "123 Main Street"
                }
                """;

        MvcResult createResult = mockMvc.perform(
                        post("/api/properties")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createPayload)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.tax").value(2500.0))
                .andExpect(jsonPath("$.address").value("123 Main Street"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"))
                .andReturn();

        String response =
                createResult.getResponse().getContentAsString();

        JsonNode json =
                objectMapper.readTree(response);

        String id = json.get("id").asText();

        mockMvc.perform(get("/api/properties/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(get("/api/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        String updatePayload = """
                {
                  "tax": 3000.0,
                  "address": "456 Park Avenue"
                }
                """;

        mockMvc.perform(
                        put("/api/properties/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatePayload)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tax").value(3000.0))
                .andExpect(jsonPath("$.address").value("456 Park Avenue"));

        mockMvc.perform(post("/api/properties/{id}/activate", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("AVAILABLE"));

        mockMvc.perform(get("/api/properties/{id}/eligibility", id))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(delete("/api/properties/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/properties/{id}", id))
                .andExpect(status().isNotFound());
    }
}