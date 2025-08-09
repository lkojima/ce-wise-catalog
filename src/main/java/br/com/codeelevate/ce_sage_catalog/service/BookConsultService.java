package br.com.codeelevate.ce_sage_catalog.service;

import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookListConsultDTO;

public interface BookConsultService {
    public RsponseBookConsultDTO consultBookById(String bookId);

    public RsponseBookListConsultDTO consultBookByAuthor(String authorName);

    public RsponseBookListConsultDTO consultBookByGenre(String genre);

    public RsponseBookListConsultDTO consultAllBooks(Integer page, Integer pageSize);
}
