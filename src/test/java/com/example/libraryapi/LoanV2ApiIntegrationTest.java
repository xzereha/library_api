package com.example.libraryapi;

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

/** Integration tests for the LoanV2 API. */
@Sql(
        statements =
                """
                DELETE FROM loan;
                ALTER TABLE loan ALTER COLUMN id RESTART WITH 1;
                DELETE FROM book;
                ALTER TABLE book ALTER COLUMN id RESTART WITH 1;
                DELETE FROM author;
                ALTER TABLE author ALTER COLUMN id RESTART WITH 1;

                INSERT INTO author (id, name) VALUES (1, 'J.K. Rowling');
                INSERT INTO book (id, title, author_id) VALUES
                (1, 'Harry Potter and the Sorcerer''s Stone', 1);
                """,
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(
        properties = "spring.cache.type=none",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LoanV2ApiIntegrationTest {
    @Autowired private MockMvc mockMvc;

    @Nested
    @DisplayName("POST /api/v2/loans")
    class CreateLoan {

        @Test
        @DisplayName("should return 400 Bad Request when book ID is missing")
        void shouldReturnBadRequestWhenBookIdIsMissing() throws Exception {
            String requestBody =
                    """
                    {
                        }
                    """;

            mockMvc.perform(
                            post("/api/v2/loans")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody))
                    .andExpect(status().isBadRequest());
        }

        @Sql(
                statements =
                        """
                        DELETE FROM loan;
                        ALTER TABLE loan ALTER COLUMN id RESTART WITH 1;
                        INSERT INTO loan (id, book_id) VALUES (1, 1);
                        """,
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Test
        @DisplayName("should return 400 Bad Request when book is already loaned out")
        void shouldReturnBadRequestWhenBookIsNotAvailable() throws Exception {
            String requestBody =
                    """
                    {
                        "bookId": 1
                        }
                    """;

            mockMvc.perform(
                            post("/api/v2/loans")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should return 400 bad request when book is attempted to be loaned out twice")
        void shouldReturnBadRequestWhenBookIsLoanedOutTwice() throws Exception {
            String requestBody =
                    """
                    {
                        "bookId": 1
                        }
                    """;

            mockMvc.perform(
                            post("/api/v2/loans")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody))
                    .andExpect(status().isCreated());

            mockMvc.perform(
                            post("/api/v2/loans")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("should return 404 Not Found when book is missing")
        void shouldReturnNotFoundWhenBookIsMissing() throws Exception {
            String requestBody =
                    """
                    {
                        "bookId": 999
                        }
                    """;

            mockMvc.perform(
                            post("/api/v2/loans")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("should create a new loan and return 201 Created when book is available")
        void shouldCreateLoanWhenBookIsAvailable() throws Exception {
            String requestBody =
                    """
                    {
                        "bookId": 1
                        }
                    """;
            mockMvc.perform(
                            post("/api/v2/loans")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestBody))
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    @DisplayName("GET /api/v2/loans")
    class GetAllLoans {

        @Test
        @DisplayName("should retrieve all loans and return 200 OK")
        void shouldRetrieveAllLoans() throws Exception {
            mockMvc.perform(get("/api/v2/loans").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("should return an empty list of loans when there are no loans")
        void shouldReturnEmptyListWhenNoLoans() throws Exception {
            mockMvc.perform(get("/api/v2/loans").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Sql(
                statements =
                        """
                        INSERT INTO loan (id, book_id) VALUES (1, 1);
                        """,
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Test
        @DisplayName("should return a list of loans when there are existing loans")
        void shouldReturnListOfLoans() throws Exception {
            mockMvc.perform(get("/api/v2/loans").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data[0].id").value(1))
                    .andExpect(jsonPath("$.data[0].bookId").value(1));
        }
    }
}
