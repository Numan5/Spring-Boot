package com.example.bookstore;



import com.example.bookstore.dto.BookResponse;

import com.example.bookstore.service.BookCatalogService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = BookstoreSpringDataApplication.class,
        webEnvironment = WebEnvironment.MOCK
)
@AutoConfigureMockMvc
class BookCatalogRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookCatalogService bookCatalogService;

    @Test
    void findByIsbn() throws Exception {
        var bookCatalog = new BookResponse(
                1L,"34534534","yaşarNuri",
                "başlık", 200,2019,65.75,
                "Test Cover"
        );

        Mockito.when(bookCatalogService.findBookByIsbn("34534534")).thenReturn(bookCatalog);

        mockMvc.perform(get("/books/34534534")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn",containsString("34534534")))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.author",is("yaşarNuri")))
                .andExpect(jsonPath("$.pages",is(200)))
                .andExpect(jsonPath("$.year",is(2019)))
                .andExpect(jsonPath("$.cover",is("kapak")))
                .andExpect(jsonPath("$.price",is(65.75)));


    }
    
}