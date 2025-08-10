package br.com.codeelevate.ce_sage_catalog.service.impl;

import br.com.codeelevate.ce_sage_catalog.exception.handler.NotFoundException;
import br.com.codeelevate.ce_sage_catalog.model.Book;
import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookListConsultDTO;
import br.com.codeelevate.ce_sage_catalog.repository.BookConsultRepository;
import br.com.codeelevate.ce_sage_catalog.service.BookConsultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class BookConsultServiceImpl implements BookConsultService {

    @Autowired private BookConsultRepository bookConsultRepository;
    @Autowired private RedisTemplate redisTemplate;

    private static final String UNIQUE_KEY = "books";

    @Autowired private ObjectMapper mapper;
    @Override public RsponseBookConsultDTO consultBookById(String bookId) throws JsonProcessingException {


        if(redisTemplate.hasKey(bookId)){
            JavaType type = mapper.getTypeFactory().constructType(Book.class);
            String bookString = (String) redisTemplate.opsForValue().get(bookId);
            Book bookDto = mapper.readValue(bookString, type);
            return RsponseBookConsultDTO.builder()
                    .data(bookDto)
                    .build();
        } else {
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

            return RsponseBookConsultDTO.builder()
                    .data(book.get())
                    .build();
        }


    }

    @Override
    public RsponseBookListConsultDTO consultBookByAuthor(String authorName) {
        Optional<List<Book>> book = bookConsultRepository.findByAuthor(authorName);
        System.out.println("bookConsultRepository.findById(bookId)");

        if(book.isEmpty()) {
            throw new NotFoundException("Not found books for author: " + authorName);
        }

        return RsponseBookListConsultDTO.builder()
                .data(book.get())
                .build();
    }

    @Override
    public RsponseBookListConsultDTO consultBookByGenre(String genre) {
        Optional<List<Book>> book = bookConsultRepository.findByGenre(genre);
        System.out.println("bookConsultRepository.findById(genre)");

        if(book.isEmpty()) {
            throw new NotFoundException("Not found books for genre: " + genre);
        }

        return RsponseBookListConsultDTO.builder()
                .data(book.get())
                .build();
    }

    @Override
    public RsponseBookListConsultDTO consultAllBooks(Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        List<Book> listOfBooks = bookConsultRepository.findAll(pageable).getContent();

        if(listOfBooks.isEmpty()) {
            throw new NotFoundException("Not found any books");
        }

        return RsponseBookListConsultDTO.builder()
                .data(listOfBooks)
                .build();
    }

    @Override
    public RsponseBookListConsultDTO consultRecentlyBooks() {

        Gson gson = new Gson();
        List<Object> listaJson = redisTemplate.opsForList().range(UNIQUE_KEY, 0, 9);

        List<Book> recentlyViewList = listaJson.stream()
                .map(obj -> gson.fromJson((String) obj, Book.class))
                .collect(Collectors.toList());

        System.out.println("livros : " + recentlyViewList);

        if(recentlyViewList.isEmpty()) {
            throw new NotFoundException("Not found books recently consulted");
        }

        return RsponseBookListConsultDTO.builder()
                .data(recentlyViewList.stream().distinct().collect(Collectors.toList()))
                .build();
    }
}
