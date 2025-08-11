package br.com.codeelevate.ce_sage_catalog.controller;

import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.service.BookInsertService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/insert")
public class BookInsertController {

    private static final String HTTP_HEADER_FAPI_INTERACTION_ID = "x-fapi-interaction-id";
    @Autowired private BookInsertService bookInsertService;

    @PostMapping(value ="/books/{genre}")
    public ResponseEntity<String> getBookById(
            @PathVariable("genre") String genre) throws JsonProcessingException {
        bookInsertService.insertBooksByAuthor(genre);
        return ResponseEntity.created(URI.create("/books/"+genre)).body(genre);
    }
}
