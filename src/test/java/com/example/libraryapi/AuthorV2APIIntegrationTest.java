package com.example.libraryapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorV2APIIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("POST /api/v2/authors")
    class CreateAuthorTests {
        @Test
        @DisplayName("should create a new author and return 201 Created")
        void shouldCreateAuthor() throws Exception {
            String requestBody = """
                    {
                        "name": "J.R.R. Tolkien"
                    }
                    """;

            mockMvc.perform(post("/api/v2/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.version").value(2))
                    .andExpect(jsonPath("$.data.id").exists())
                    .andExpect(jsonPath("$.data.name").value("J.R.R. Tolkien"));
        }

        @Test
        @DisplayName("should return 400 Bad Request when name is missing")
        void shouldReturnBadRequestWhenNameIsMissing() throws Exception {
            String requestBody = """
                    {
                    }
                    """;

            mockMvc.perform(post("/api/v2/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isBadRequest());
        }
    }
}
