package com.example.libraryapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookV1APIIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateBookWhenAllParams() throws Exception {
        String bookJson = """
            {
                "title": "The Great Gatsby",
                "author": "F. Scott Fitzgerald",
                "isbn": "978-0743273565",
                "publishedYear": 1925
            }
        """;

        var result = mockMvc.perform(
            post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        )
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", matchesPattern("/api/v1/books/\\d+")))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.title").value("The Great Gatsby"))
        .andExpect(jsonPath("$.author").value("F. Scott Fitzgerald"))
        .andExpect(jsonPath("$.isbn").value("978-0743273565"))
        .andExpect(jsonPath("$.publishedYear").value(1925))
        .andReturn();

        String body = result.getResponse().getContentAsString();
        Number numberId = JsonPath.read(body, "$.id");
        long id = numberId.longValue();

        mockMvc.perform(get("/api/v1/books/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(is(id), Long.class))
            .andExpect(jsonPath("$.title").value("The Great Gatsby"))
            .andExpect(jsonPath("$.author").value("F. Scott Fitzgerald"))
            .andExpect(jsonPath("$.isbn").value("978-0743273565"))
            .andExpect(jsonPath("$.publishedYear").value(1925));
    }
}
