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
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class BookConsultServiceImpl implements BookConsultService {

    @Autowired
    private BookConsultRepository bookConsultRepository;

    @Override
    public RsponseBookConsultDTO consultBookById(String bookId) {
        Optional<Book> book = bookConsultRepository.findById(bookId);
        return RsponseBookConsultDTO.builder()
                .data(book.get())
                .build();
    }

    @Override
    public RsponseBookListConsultDTO consultBookByAuthor(String authorName) {
        Optional<List<Book>> book = bookConsultRepository.findByAuthor(authorName);
        System.out.println("bookConsultRepository.findById(bookId)");

        return RsponseBookListConsultDTO.builder()
                .data(book.get())
                .build();
    }

    @Override
    public RsponseBookListConsultDTO consultBookByGenre(String genre) {
        Optional<List<Book>> book = bookConsultRepository.findByGenre(genre);
        System.out.println("bookConsultRepository.findById(genre)");
        return RsponseBookListConsultDTO.builder()
                .data(book.get())
                .build();
    }

    @Override
    public RsponseBookListConsultDTO consultAllBooks(Integer page, Integer pageSize) {
        List<Book> book = bookConsultRepository.findAll();
        System.out.println("bookConsultRepository.findById(genre)");
        return RsponseBookListConsultDTO.builder()
                .data(book)
                .build();
    }
}
