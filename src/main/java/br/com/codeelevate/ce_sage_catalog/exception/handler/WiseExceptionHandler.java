package br.com.codeelevate.ce_sage_catalog.exception.handler;

import br.com.codeelevate.ce_sage_catalog.model.exception.handler.HandlerExcpetionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class WiseExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HandlerExcpetionDTO> handleResourceNotFound(NotFoundException ex, HttpServletRequest request) {
        HandlerExcpetionDTO errorResponse = new HandlerExcpetionDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
