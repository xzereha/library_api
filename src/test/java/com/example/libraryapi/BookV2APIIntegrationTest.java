package com.example.libraryapi;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;
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
public class BookV2APIIntegrationTest {

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

        @Sql(statements = """
                INSERT INTO book (title, author) VALUES
                ('1984', 'George Orwell'),
                ('Brave New World', 'Aldous Huxley');
                 """, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Test
        void returnsAllBooksWhenDataExists() throws Exception {
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

        @Sql(statements = """
                INSERT INTO book (id, title, author, isbn, published_year, available) VALUES
                (1, 'The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 1925, false);
                 """, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Test
        void returnsBookWhenFound() throws Exception {
            Long id = 1L;
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

    @Sql(statements = """
            DELETE FROM book;
            ALTER TABLE book ALTER COLUMN id RESTART WITH 1;
            INSERT INTO book (title, author, isbn, published_year) VALUES
            ('1984', 'George Orwell', '978-0451524935', 1949),
            ('Animal Farm', 'George Orwell', '978-0451526342', 1945),
            ('Moby Dick', 'Herman Melville', '978-1503280786', 1851),
            ('The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 1925),
            ('Great Expectations', 'Charles Dickens', '978-0141439563', 1861),
            ('Brave New World', 'Aldous Huxley', '978-0060850524', 1932);
            """, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Nested
    class FilteredGet {
        @Test
        void returnsBooksByAuthor() throws Exception {
            mockMvc.perform(get("/api/v2/books?author=George Orwell"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[*].title", hasItems("1984", "Animal Farm")))
                .andExpect(jsonPath("$.data[*].author", everyItem(is("George Orwell"))));
        }

        @Test
        void returnsBooksByTitle() throws Exception {
            mockMvc.perform(get("/api/v2/books?title=Great"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[*].title", hasItems("The Great Gatsby", "Great Expectations")));
        }

        @Test
        void returnsBooksByIsbn() throws Exception {
            mockMvc.perform(get("/api/v2/books?isbn=978-0141439563"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].title").value("Great Expectations"))
                .andExpect(jsonPath("$.data[0].isbn").value("978-0141439563"));
        }

        @Test
        void returnsEmptyListWhenNoBooksMatch() throws Exception {
            mockMvc.perform(get("/api/v2/books?author=Nonexistent Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());

        }

        @Test
        void whitespaceQueryReturnsAllBooks() throws Exception {
            mockMvc.perform(get("/api/v2/books?author="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(6))
                .andExpect(
                            jsonPath("$.data[*].title",
                                    hasItems("The Great Gatsby",
                                            "Brave New World",
                                            "Moby Dick",
                                            "1984",
                                            "Animal Farm",
                                            "Great Expectations")));
        }

        @Test
        void multipleQueryParametersReturnsBooksMatchingAllCriteria() throws Exception {
            mockMvc.perform(get("/api/v2/books?author=George&title=1984"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(2))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].title").value("1984"))
                .andExpect(jsonPath("$.data[0].author").value("George Orwell"))
                .andExpect(jsonPath("$.data[0].isbn").value("978-0451524935"));
        }
    }
}
