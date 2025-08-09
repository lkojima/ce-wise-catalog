package br.com.codeelevate.ce_sage_catalog.controller;

import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookListConsultDTO;
import br.com.codeelevate.ce_sage_catalog.service.BookConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/consult")
public class BookConsultController {

    @Autowired
    private BookConsultService bookConsultService;

    private static final String HTTP_HEADER_FAPI_INTERACTION_ID = "x-fapi-interaction-id";

    @GetMapping(value ="/book/{bookId}")
    public ResponseEntity<RsponseBookConsultDTO> getBookById(
            @PathVariable("bookId") String bookId,
            @RequestHeader HttpHeaders httpHeaders){

        return ResponseEntity.ok()
                .header(HTTP_HEADER_FAPI_INTERACTION_ID, httpHeaders.getFirst(HTTP_HEADER_FAPI_INTERACTION_ID))
                .body(bookConsultService.consultBookById(bookId));
    }

    @GetMapping(value ="/book/author/{authorName}")
    public ResponseEntity<RsponseBookListConsultDTO> getBookByAuthorName(
            @PathVariable("authorName") String authorName,
            @RequestHeader HttpHeaders httpHeaders){

        return ResponseEntity.ok()
                .header(HTTP_HEADER_FAPI_INTERACTION_ID, httpHeaders.getFirst(HTTP_HEADER_FAPI_INTERACTION_ID))
                .body(bookConsultService.consultBookByAuthor(authorName));
    }

    @GetMapping(value ="/book/genre/{genre}")
    public ResponseEntity<RsponseBookListConsultDTO> getBookByGenre(
            @PathVariable("genre") String genre,
            @RequestHeader HttpHeaders httpHeaders){

        return ResponseEntity.ok()
                .header(HTTP_HEADER_FAPI_INTERACTION_ID, httpHeaders.getFirst(HTTP_HEADER_FAPI_INTERACTION_ID))
                .body(bookConsultService.consultBookByGenre(genre));
    }

}
