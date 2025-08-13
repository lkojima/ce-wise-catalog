package br.com.codeelevate.ce_sage_catalog.service;

import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookListConsultDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface BookConsultService {
    public ResponseBookConsultDTO consultBookById(String bookId) throws JsonProcessingException;

    public ResponseBookListConsultDTO consultBookByAuthor(String authorName);

    public ResponseBookListConsultDTO consultBookByGenre(String genre);

    public ResponseBookListConsultDTO consultAllBooks(Integer page, Integer pageSize);

    public ResponseBookListConsultDTO consultRecentlyBooks();
}
