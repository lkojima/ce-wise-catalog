package br.com.codeelevate.ce_sage_catalog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookListConsultDTO;
import br.com.codeelevate.ce_sage_catalog.service.BookConsultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class BookConsultControllerTest {

    @Mock
    private BookConsultService bookConsultService;

    @InjectMocks
    private BookConsultController controller;

    private static final String HEADER_NAME = "x-fapi-interaction-id";
    private static final String HEADER_VALUE = "12345";

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_NAME, HEADER_VALUE);
        return headers;
    }

    @Test
    void testGetBookById() throws JsonProcessingException {
        ResponseBookConsultDTO mockResponse = new ResponseBookConsultDTO();
        when(bookConsultService.consultBookById("1")).thenReturn(mockResponse);

        ResponseEntity<ResponseBookConsultDTO> response =
                controller.getBookById("1", buildHeaders());

        assertEquals(HEADER_VALUE, response.getHeaders().getFirst(HEADER_NAME));
        assertEquals(mockResponse, response.getBody());
        verify(bookConsultService).consultBookById("1");
    }

    @Test
    void testGetBookByAuthorName() {
        ResponseBookListConsultDTO mockResponse = new ResponseBookListConsultDTO();
        when(bookConsultService.consultBookByAuthor("Author")).thenReturn(mockResponse);

        ResponseEntity<ResponseBookListConsultDTO> response =
                controller.getBookByAuthorName("Author", buildHeaders());

        assertEquals(HEADER_VALUE, response.getHeaders().getFirst(HEADER_NAME));
        assertEquals(mockResponse, response.getBody());
        verify(bookConsultService).consultBookByAuthor("Author");
    }

    @Test
    void testGetBookByGenre() {
        ResponseBookListConsultDTO mockResponse = new ResponseBookListConsultDTO();
        when(bookConsultService.consultBookByGenre("Fiction")).thenReturn(mockResponse);

        ResponseEntity<ResponseBookListConsultDTO> response =
                controller.getBookByGenre("Fiction", buildHeaders());

        assertEquals(HEADER_VALUE, response.getHeaders().getFirst(HEADER_NAME));
        assertEquals(mockResponse, response.getBody());
        verify(bookConsultService).consultBookByGenre("Fiction");
    }

    @Test
    void testGetAllBooks() {
        ResponseBookListConsultDTO mockResponse = new ResponseBookListConsultDTO();
        when(bookConsultService.consultAllBooks(0, 10)).thenReturn(mockResponse);

        ResponseEntity<ResponseBookListConsultDTO> response =
                controller.getAllBooks(0, 10, buildHeaders());

        assertEquals(HEADER_VALUE, response.getHeaders().getFirst(HEADER_NAME));
        assertEquals(mockResponse, response.getBody());
        verify(bookConsultService).consultAllBooks(0, 10);
    }

    @Test
    void testGetRecentlyBooks() {
        ResponseBookListConsultDTO mockResponse = new ResponseBookListConsultDTO();
        when(bookConsultService.consultRecentlyBooks()).thenReturn(mockResponse);

        ResponseEntity<ResponseBookListConsultDTO> response =
                controller.getRecentlyBooks(buildHeaders());

        assertEquals(HEADER_VALUE, response.getHeaders().getFirst(HEADER_NAME));
        assertEquals(mockResponse, response.getBody());
        verify(bookConsultService).consultRecentlyBooks();
    }
}
