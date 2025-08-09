package br.com.codeelevate.ce_sage_catalog.service.impl;

import br.com.codeelevate.ce_sage_catalog.model.Book;
import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookListConsultDTO;
import br.com.codeelevate.ce_sage_catalog.repository.BookConsultRepository;
import br.com.codeelevate.ce_sage_catalog.service.BookConsultService;
import com.mongodb.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookConsultServiceImpl implements BookConsultService {

    @Autowired
    private BookConsultRepository bookConsultRepository;

    @Override
    public RsponseBookConsultDTO consultBookById(String bookId) {
        System.out.println("BookConsultServiceImpl consultBookById");
        //bookConsultRepository.findAllById(bookId);
        return null;
    }

    @Override
    public RsponseBookListConsultDTO consultBookByAuthor(String authorName) {
        return null;
    }

    @Override
    public RsponseBookListConsultDTO consultBookByGenre(String genre) {
        return null;
    }

    @Override
    public RsponseBookListConsultDTO consultAllBooks(Integer page, Integer pageSize) {
        System.out.println(bookConsultRepository.findAll());

        return null;
    }
}
