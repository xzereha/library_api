package com.example.libraryapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Synchronization tests for the V2 API. These tests ensure that the V2 API endpoints are properly
 * synchronized with the underlying data model and that concurrent requests are handled correctly.
 */
@SpringBootTest(
        properties = "spring.cache.type=none",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SyncV2Test {
    @Autowired private MockMvc mockMvc;

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
    @Test
    void testConcurrentLoanCreation() throws Exception {
        final int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(numberOfThreads);
        ResultActions[] resultActions = new ResultActions[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            final int index = i;
            executorService.submit(
                    () -> {
                        try {
                            start.await(); // Wait for the signal to start
                            // Perform a POST request to create a loan
                            resultActions[index] =
                                    mockMvc.perform(
                                            post("/api/v2/loans")
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(
                                                            """
                                                            {
                                                                "bookId": 1
                                                            }
                                                            """));
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            done.countDown(); // Signal that this thread is done
                        }
                    });
        }

        // Signal all threads to start
        start.countDown();
        // Wait for all threads to finish
        done.await();

        // Check results
        int successCount = 0;
        int badRequestCount = 0;
        for (ResultActions action : resultActions) {
            if (action != null) {
                try {
                    action.andExpect(status().isCreated());
                    successCount++;
                } catch (AssertionError e) {
                    action.andExpect(status().isBadRequest());
                    badRequestCount++;
                }
            }
            assertNotNull(action, "Expected a ResultActions object, but got null");
        }
        assertEquals(
                1,
                successCount,
                "Expected exactly one successful loan creation, but got " + successCount);
        assertEquals(
                numberOfThreads - 1,
                badRequestCount,
                "Expected exactly one bad request response, but got " + badRequestCount);

        executorService.shutdown();
    }
}
