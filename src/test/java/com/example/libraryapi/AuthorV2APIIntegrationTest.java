package com.example.libraryapi;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Sql(statements = """
        DELETE FROM book;
        ALTER TABLE book ALTER COLUMN id RESTART WITH 1;
        DELETE FROM author;
        ALTER TABLE author ALTER COLUMN id RESTART WITH 1;
        """)
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

    @Nested
    @DisplayName("GET /api/v2/authors")
    class GetAuthorsTests {
        @Sql(statements = """
                INSERT INTO author (id, name) VALUES
                (1, 'George Orwell'),
                (2, 'Aldous Huxley'),
                (3, 'J.R.R. Tolkien');
                    """)
        @Test
        @DisplayName("should return a list of authors")
        void shouldReturnAllAuthors() throws Exception {
            mockMvc.perform(get("/api/v2/authors")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.version").value(2))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data.length()").value(3))
                    .andExpect(jsonPath("$.data[*].name", hasItems("George Orwell", "Aldous Huxley", "J.R.R. Tolkien")));
        }

        @Test
        void shouldReturnEmptyListWhenNoAuthors() throws Exception {
            mockMvc.perform(get("/api/v2/authors")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.version").value(2))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isEmpty());
        }
    }
}
