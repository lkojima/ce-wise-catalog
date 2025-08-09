package br.com.codeelevate.ce_sage_catalog.service.impl;

import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookListConsultDTO;
import br.com.codeelevate.ce_sage_catalog.service.BookConsultService;
import org.springframework.stereotype.Service;

@Service
public class BookConsultServiceImpl implements BookConsultService {

    @Override
    public RsponseBookConsultDTO consultBookById(String bookId) {

    }

    @Override
    public RsponseBookListConsultDTO consultBookByAuthor(String authorName) {

    }

    @Override
    public RsponseBookListConsultDTO consultBookByGenre(String genre) {

    }

    @Override
    public RsponseBookListConsultDTO consultAllBooks(Integer page, Integer pageSize) {

    }
}
