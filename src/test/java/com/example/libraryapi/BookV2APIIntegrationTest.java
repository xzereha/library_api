package com.example.libraryapi;

import com.example.libraryapi.repository.BookRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookV2APIIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

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

            mockMvc.perform(post("/api/v2/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bookJson))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", matchesPattern("/api/v2/books/\\d+")))
                    .andExpect(jsonPath("$.version").value(2))
                    .andExpect(jsonPath("$.data.id").exists())
                    .andExpect(jsonPath("$.data.title").value("The Great Gatsby"))
                    .andExpect(jsonPath("$.data.author").value("F. Scott Fitzgerald"))
                    .andExpect(jsonPath("$.data.isbn").value("978-0743273565"))
                    .andExpect(jsonPath("$.data.publishedYear").value(1925))
                    .andExpect(jsonPath("$.data.available").value(false));
        }

        @Test
        void withOptionalFieldsMissing() throws Exception {
            String bookJson = """
                    {
                        "title": "Moby Dick",
                        "author": "Herman Melville"
                    }
                    """;

            mockMvc.perform(post("/api/v2/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bookJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.version").value(2))
                    .andExpect(jsonPath("$.data.id").exists())
                    .andExpect(jsonPath("$.data.title").value("Moby Dick"))
                    .andExpect(jsonPath("$.data.author").value("Herman Melville"))
                    .andExpect(jsonPath("$.data.isbn").value(nullValue()))
                    .andExpect(jsonPath("$.data.publishedYear").value(nullValue()))
                    .andExpect(jsonPath("$.data.available").value(false));
        }

        @Test
        void failsWhenRequiredFieldsMissing() throws Exception {
            String bookJson = """
                    {
                        "isbn": "978-0743273565",
                        "publishedYear": 1925
                    }
                    """;

            mockMvc.perform(post("/api/v2/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bookJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Bad Request"))
                    .andExpect(jsonPath("$.message").value(containsString("missing or invalid")))
                    .andExpect(jsonPath("$.path").value("/api/v2/books"));
        }

        @Test
        void failsWhenJsonIsInvalid() throws Exception {
            String invalidJson = "{ title: not valid json }";

            mockMvc.perform(post("/api/v2/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Bad Request"))
                    .andExpect(jsonPath("$.message").value(containsString("parse")))
                    .andExpect(jsonPath("$.path").value("/api/v2/books"));
        }
    }

    @Nested
    class GetBooks {

        @Test
        void returnsEmptyListWhenNoBooksExist() throws Exception {
            mockMvc.perform(get("/api/v2/books"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.version").value(2))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        void returnsAllBooksWhenDataExists() throws Exception {
            String book1 = """
                    { "title": "1984", "author": "George Orwell" }
                    """;
            String book2 = """
                    { "title": "Brave New World", "author": "Aldous Huxley" }
                    """;

            mockMvc.perform(post("/api/v2/books").contentType(MediaType.APPLICATION_JSON).content(book1))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/api/v2/books").contentType(MediaType.APPLICATION_JSON).content(book2))
                    .andExpect(status().isCreated());

            mockMvc.perform(get("/api/v2/books"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.version").value(2))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data.length()").value(2))
                    .andExpect(jsonPath("$.data[*].title", hasItems("1984", "Brave New World")))
                    .andExpect(jsonPath("$.data[*].available", everyItem(is(false))));
        }
    }

    @Nested
    class GetBookById {

        @Test
        void returnsBookWhenFound() throws Exception {
            String bookJson = """
                    {
                        "title": "The Great Gatsby",
                        "author": "F. Scott Fitzgerald",
                        "isbn": "978-0743273565",
                        "publishedYear": 1925
                    }
                    """;

            var result = mockMvc.perform(post("/api/v2/books")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bookJson))
                    .andExpect(status().isCreated())
                    .andReturn();

            long id = ((Number) JsonPath.read(result.getResponse().getContentAsString(), "$.data.id")).longValue();

            mockMvc.perform(get("/api/v2/books/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.version").value(2))
                    .andExpect(jsonPath("$.data.id").value(is(id), Long.class))
                    .andExpect(jsonPath("$.data.title").value("The Great Gatsby"))
                    .andExpect(jsonPath("$.data.author").value("F. Scott Fitzgerald"))
                    .andExpect(jsonPath("$.data.isbn").value("978-0743273565"))
                    .andExpect(jsonPath("$.data.publishedYear").value(1925))
                    .andExpect(jsonPath("$.data.available").value(false));
        }

        @Test
        void returnsNotFoundWhenIdDoesNotExist() throws Exception {
            mockMvc.perform(get("/api/v2/books/999999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.error").value("Not Found"))
                    .andExpect(jsonPath("$.message").value(containsString("not found")))
                    .andExpect(jsonPath("$.path").value("/api/v2/books/999999"));
        }

        @Test
        void returnsBadRequestWhenIdIsNotNumeric() throws Exception {
            mockMvc.perform(get("/api/v2/books/abc"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.path").value("/api/v2/books/abc"));
        }
    }
}
