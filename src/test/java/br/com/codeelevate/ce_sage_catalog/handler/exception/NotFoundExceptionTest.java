package br.com.codeelevate.ce_sage_catalog.handler.exception;

import static org.junit.jupiter.api.Assertions.*;

import br.com.codeelevate.ce_sage_catalog.exception.handler.NotFoundException;
import org.junit.jupiter.api.Test;

public class NotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Resource not found";
        NotFoundException exception = new NotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }
}
