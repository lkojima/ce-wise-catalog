package br.com.codeelevate.ce_sage_catalog.controller;

import br.com.codeelevate.ce_sage_catalog.service.BookInsertService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookInsertControllerTest {

    @Mock
    private BookInsertService bookInsertService;

    @InjectMocks
    private BookInsertController controller;

    @Test
    void testGetBookById() throws JsonProcessingException {
        String genre = "Fiction";

        ResponseEntity<String> response = controller.getBookById(genre);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(URI.create("/books/" + genre), response.getHeaders().getLocation());
        assertEquals(genre, response.getBody());

        verify(bookInsertService).insertBooksByAuthor(genre);
    }
}

