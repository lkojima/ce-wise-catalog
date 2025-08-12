package br.com.codeelevate.ce_sage_catalog.handler.exception;

import br.com.codeelevate.ce_sage_catalog.exception.handler.NotFoundException;
import br.com.codeelevate.ce_sage_catalog.exception.handler.WiseExceptionHandler;
import br.com.codeelevate.ce_sage_catalog.model.exception.handler.HandlerExcpetionDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

class WiseExceptionHandlerTest {

    private final WiseExceptionHandler handler = new WiseExceptionHandler();

    @Test
    void handleResourceNotFound_returnsCorrectResponse() {
        NotFoundException ex = new NotFoundException("Resource not found");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/test/uri");

        ResponseEntity<HandlerExcpetionDTO> response = handler.handleResourceNotFound(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        HandlerExcpetionDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.NOT_FOUND.value(), body.getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), body.getError());
        assertEquals("Resource not found", body.getMessage());
        assertEquals("/test/uri", body.getPath());
        assertNotNull(body.getTimestamp());
        assertTrue(body.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1))); // timestamp recente
    }
}
