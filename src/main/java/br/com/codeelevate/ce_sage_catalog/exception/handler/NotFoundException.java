package br.com.codeelevate.ce_sage_catalog.exception.handler;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String error) {
            super(error);
    }
}

