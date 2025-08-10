package br.com.codeelevate.ce_sage_catalog.service;

import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookListConsultDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface BookConsultService {
    public RsponseBookConsultDTO consultBookById(String bookId) throws JsonProcessingException;

    public RsponseBookListConsultDTO consultBookByAuthor(String authorName);

    public RsponseBookListConsultDTO consultBookByGenre(String genre);

    public RsponseBookListConsultDTO consultAllBooks(Integer page, Integer pageSize);

    public RsponseBookListConsultDTO consultRecentlyBooks();
}
