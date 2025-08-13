package br.com.codeelevate.ce_sage_catalog.controller;

import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookListConsultDTO;
import br.com.codeelevate.ce_sage_catalog.service.BookConsultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books")
public class BookConsultController {

    @Autowired
    private BookConsultService bookConsultService;

    private static final String HTTP_HEADER_FAPI_INTERACTION_ID = "x-fapi-interaction-id";

    @GetMapping(value ="/{bookId}")
    public ResponseEntity<ResponseBookConsultDTO> getBookById(
            @PathVariable("bookId") String bookId,
            @RequestHeader HttpHeaders httpHeaders) throws JsonProcessingException {

        return ResponseEntity.ok()
                .header(HTTP_HEADER_FAPI_INTERACTION_ID, httpHeaders.getFirst(HTTP_HEADER_FAPI_INTERACTION_ID))
                .body(bookConsultService.consultBookById(bookId));
    }

    @GetMapping(value ="/author/{authorName}")
    public ResponseEntity<ResponseBookListConsultDTO> getBookByAuthorName(
            @PathVariable("authorName") String authorName,
            @RequestHeader HttpHeaders httpHeaders){

        return ResponseEntity.ok()
                .header(HTTP_HEADER_FAPI_INTERACTION_ID, httpHeaders.getFirst(HTTP_HEADER_FAPI_INTERACTION_ID))
                .body(bookConsultService.consultBookByAuthor(authorName));
    }

    @GetMapping(value ="/genre/{genre}")
    public ResponseEntity<ResponseBookListConsultDTO> getBookByGenre(
            @PathVariable("genre") String genre,
            @RequestHeader HttpHeaders httpHeaders){

        return ResponseEntity.ok()
                .header(HTTP_HEADER_FAPI_INTERACTION_ID, httpHeaders.getFirst(HTTP_HEADER_FAPI_INTERACTION_ID))
                .body(bookConsultService.consultBookByGenre(genre));
    }

    @GetMapping()
    public ResponseEntity<ResponseBookListConsultDTO> getAllBooks(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100000000") Integer pageSize,
            @RequestHeader HttpHeaders httpHeaders){

        return ResponseEntity.ok()
                .header(HTTP_HEADER_FAPI_INTERACTION_ID, httpHeaders.getFirst(HTTP_HEADER_FAPI_INTERACTION_ID))
                .body(bookConsultService.consultAllBooks(page, pageSize));
    }

    @GetMapping(value ="/recently")
    public ResponseEntity<ResponseBookListConsultDTO> getRecentlyBooks(
            @RequestHeader HttpHeaders httpHeaders){

        return ResponseEntity.ok()
                .header(HTTP_HEADER_FAPI_INTERACTION_ID, httpHeaders.getFirst(HTTP_HEADER_FAPI_INTERACTION_ID))
                .body(bookConsultService.consultRecentlyBooks());
    }

}
