package com.example.libraryapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

/**
 * Full-stack integration tests for the V2 API. These tests cover the entire application stack, from
 * the API layer down to the database.
 */
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        statements =
                """
                DELETE FROM loan;
                ALTER TABLE loan ALTER COLUMN id RESTART WITH 1;
                DELETE FROM book;
                ALTER TABLE book ALTER COLUMN id RESTART WITH 1;
                DELETE FROM author;
                ALTER TABLE author ALTER COLUMN id RESTART WITH 1;
                """)
@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FullstackV2Test {
    @Autowired private TestRestTemplate restTemplate;

    @Test
    void testCreateFullStack() {

        String authorRequest =
                """
                {
                    "name": "George Orwell"
                }
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> authorEntity = new HttpEntity<>(authorRequest, headers);
        ResponseEntity<String> authorResponse =
                restTemplate.postForEntity("/api/v2/authors", authorEntity, String.class);
        assertEquals(HttpStatus.CREATED, authorResponse.getStatusCode());
        var authorIdRaw = JsonPath.read(authorResponse.getBody(), "$.data.id");
        long authorId = ((Number) authorIdRaw).longValue();
        String bookRequest =
                """
                {
                    "title": "1984",
                    "authorId": %d
                }
                """
                        .formatted(authorId);
        HttpEntity<String> bookEntity = new HttpEntity<>(bookRequest, headers);
        var bookResponse = restTemplate.postForEntity("/api/v2/books", bookEntity, String.class);
        assertEquals(HttpStatus.CREATED, bookResponse.getStatusCode());
        var bookIdRaw = JsonPath.read(bookResponse.getBody(), "$.data.id");
        long bookId = ((Number) bookIdRaw).longValue();
        var loanRequest =
                """
                {
                    "bookId": %d
                }
                """
                        .formatted(bookId);
        HttpEntity<String> loanEntity = new HttpEntity<>(loanRequest, headers);
        var loanResponse = restTemplate.postForEntity("/api/v2/loans", loanEntity, String.class);
        assertEquals(HttpStatus.CREATED, loanResponse.getStatusCode());
    }

    @Test
    void testInvalidMediaType() {
        String request =
                """
                {
                    "name": "George Orwell"
                }
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response =
                restTemplate.postForEntity("/api/v2/authors", entity, String.class);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }
}
