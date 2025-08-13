package br.com.codeelevate.ce_sage_catalog.service.impl;

import br.com.codeelevate.ce_sage_catalog.exception.handler.NotFoundException;
import br.com.codeelevate.ce_sage_catalog.model.Book;
import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookListConsultDTO;
import br.com.codeelevate.ce_sage_catalog.repository.BookConsultRepository;
import br.com.codeelevate.ce_sage_catalog.service.BookConsultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@Log
public class BookConsultServiceImpl implements BookConsultService {

    @Autowired private BookConsultRepository bookConsultRepository;
    @Autowired private RedisTemplate redisTemplate;

    private static final String UNIQUE_KEY = "books";

    @Autowired private ObjectMapper mapper;

    @Override public ResponseBookConsultDTO consultBookById(String bookId) throws JsonProcessingException {
        log.info("Consulting book by id: " + bookId);
        if(redisTemplate.hasKey(bookId)){
            log.info("Found book by id in redis: " + bookId);
            JavaType type = mapper.getTypeFactory().constructType(Book.class);
            String bookString = (String) redisTemplate.opsForValue().get(bookId);
            Book bookDto = mapper.readValue(bookString, type);
            return ResponseBookConsultDTO.builder()
                    .data(bookDto)
                    .build();
        } else {
            log.info("Found book by id in database: " + bookId);
            Optional<Book> book = bookConsultRepository.findById(bookId);
            if(book.isEmpty()) {
                throw new NotFoundException("book id " + bookId + " was not found in database");
            }

            Gson gson = new Gson();
            String jsonToRedis = gson.toJson(book.get());

            redisTemplate.opsForList().leftPush(UNIQUE_KEY, jsonToRedis);
            redisTemplate.expire(UNIQUE_KEY, 30, TimeUnit.SECONDS);

            redisTemplate.opsForValue().set(bookId, mapper.writeValueAsString(book.get()));
            redisTemplate.expire(bookId, 30, TimeUnit.SECONDS);

            return ResponseBookConsultDTO.builder()
                    .data(book.get())
                    .build();
        }


    }

    @Override
    public ResponseBookListConsultDTO consultBookByAuthor(String authorName) {
        log.info("Consulting book by author: " +  authorName);
        Optional<List<Book>> book = bookConsultRepository.findByAuthor(authorName);

        if(book.get().isEmpty()) {
            throw new NotFoundException("Not found books for author: " + authorName);
        }

        return ResponseBookListConsultDTO.builder()
                .data(book.get())
                .build();
    }

    @Override
    public ResponseBookListConsultDTO consultBookByGenre(String genre) {
        log.info("Consulting book by genre: " +  genre);
        Optional<List<Book>> book = bookConsultRepository.findByGenre(genre);
        System.out.println(book);

        if(book.get().isEmpty()) {
            throw new NotFoundException("Not found books for genre: " + genre);
        }

        return ResponseBookListConsultDTO.builder()
                .data(book.get())
                .build();
    }

    @Override
    public ResponseBookListConsultDTO consultAllBooks(Integer page, Integer pageSize) {
        log.info("Consulting all books");
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Book> listOfBooks = bookConsultRepository.findAll(pageable).getContent();

        if(listOfBooks.isEmpty()) {
            throw new NotFoundException("Not found any books");
        }

        return ResponseBookListConsultDTO.builder()
                .data(listOfBooks)
                .build();
    }

    @Override
    public ResponseBookListConsultDTO consultRecentlyBooks() {
        log.info("Consulting recently consulted books");
        Gson gson = new Gson();
        List<Object> listaJson = redisTemplate.opsForList().range(UNIQUE_KEY, 0, 9);

        List<Book> recentlyViewList = listaJson.stream()
                .map(obj -> gson.fromJson((String) obj, Book.class))
                .collect(Collectors.toList());

        System.out.println("livros : " + recentlyViewList);

        if(recentlyViewList.isEmpty()) {
            throw new NotFoundException("Not found books recently consulted");
        }

        return ResponseBookListConsultDTO.builder()
                .data(recentlyViewList.stream().distinct().collect(Collectors.toList()))
                .build();
    }
}
