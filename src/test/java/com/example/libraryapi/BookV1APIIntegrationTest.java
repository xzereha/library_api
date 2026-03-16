package com.example.libraryapi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(statements = """
        DELETE FROM book;
        ALTER TABLE book ALTER COLUMN id RESTART WITH 1
            """, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BookV1APIIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class PostBooks {
        @Test
        void withAllFields() throws Exception {
            String bookJson = """
                    {
                        "title": "The Great Gatsby",
                        "author": "F. Scott Fitzgerald",
                        "isbn": "978-0743273565",
                        "publishedYear": 1925
                    }
                    """;

            mockMvc.perform(post("/api/v1/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("/api/v1/books/\\d+")))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.author").value("F. Scott Fitzgerald"))
                .andExpect(jsonPath("$.isbn").value("978-0743273565"))
                .andExpect(jsonPath("$.publishedYear").value(1925));
        }

        @Test
        void withOptionalFieldsMissing() throws Exception {
            String bookJson = """
                    {
                        "title": "Moby Dick",
                        "author": "Herman Melville"
                    }
                    """;

            mockMvc.perform(post("/api/v1/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Moby Dick"))
                .andExpect(jsonPath("$.author").value("Herman Melville"))
                .andExpect(jsonPath("$.isbn").value(nullValue()))
                .andExpect(jsonPath("$.publishedYear").value(nullValue()));
        }

        @Test
        void failsWhenRequiredFieldsMissing() throws Exception {
            String bookJson = """
                    {
                        "isbn": "978-0743273565",
                        "publishedYear": 1925
                    }
                    """;

            mockMvc.perform(post("/api/v1/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bookJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value(containsString("missing or invalid")))
                .andExpect(jsonPath("$.path").value("/api/v1/books"));
        }

        @Test
        void failsWhenJsonIsInvalid() throws Exception {
            String invalidJson = "{ title: not valid json }";

            mockMvc.perform(post("/api/v1/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value(containsString("parse")))
                .andExpect(jsonPath("$.path").value("/api/v1/books"));
        }
    }

    @Nested
    class GetBooks {
        @Test
        void returnsEmptyListWhenNoBooksExist() throws Exception {
            mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
        }

        @Sql(statements = """
                INSERT INTO book (title, author) VALUES
                ('1984', 'George Orwell'),
                ('Brave New World', 'Aldous Huxley');
                """, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Test
        void returnsAllBooksWhenDataExists() throws Exception {
            mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[*].title", hasItems("1984", "Brave New World")));
        }
    }

    @Nested
    class GetBookById {
        @Sql(statements = """
                INSERT INTO book (id, title, author, isbn, published_year) VALUES
                (1, 'The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 1925);
                """, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Test
        void returnsBookWhenFound() throws Exception {
            mockMvc.perform(get("/api/v1/books/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(is(1L), Long.class))
                .andExpect(jsonPath("$.title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.author").value("F. Scott Fitzgerald"))
                .andExpect(jsonPath("$.isbn").value("978-0743273565"))
                .andExpect(jsonPath("$.publishedYear").value(1925));
        }

        @Test
        void returnsNotFoundWhenIdDoesNotExist() throws Exception {
            mockMvc.perform(get("/api/v1/books/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(containsString("not found")))
                .andExpect(jsonPath("$.path").value("/api/v1/books/999999"));
        }

        @Test
        void returnsBadRequestWhenIdIsNotNumeric() throws Exception {
            mockMvc.perform(get("/api/v1/books/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.path").value("/api/v1/books/abc"));
        }
    }
}
