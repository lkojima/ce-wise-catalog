package br.com.codeelevate.ce_sage_catalog.service;


import br.com.codeelevate.ce_sage_catalog.exception.handler.NotFoundException;
import br.com.codeelevate.ce_sage_catalog.model.Book;
import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookListConsultDTO;
import br.com.codeelevate.ce_sage_catalog.repository.BookConsultRepository;
import br.com.codeelevate.ce_sage_catalog.service.impl.BookConsultServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookConsultServiceImplTest {

    @InjectMocks
    private BookConsultServiceImpl service;

    @Mock
    private BookConsultRepository repository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @Mock
    private ListOperations<String, Object> listOperations;

    private ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "mapper", mapper);
        ReflectionTestUtils.setField(service, "redisTemplate", redisTemplate);
    }

    @Test
    void consultBookByIdReturnFromRedis() throws Exception {
        String bookId = "160";
        String bookJson = "{\n" +
                "  \"_id\" : \"160\",\n" +
                "  \"title\" : \"Alice's Adventures in Wonderland\",\n" +
                "  \"author\" : \"Lewis Carroll\",\n" +
                "  \"genre\" : \"fantasy\",\n" +
                "  \"publishYear\" : \"1865\"\n" +
                "}";

        when(redisTemplate.hasKey(bookId)).thenReturn(true);
        when(valueOperations.get(bookId)).thenReturn(bookJson);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        Book sampleBook = new Book();
        sampleBook.set_id(bookId);

        ResponseBookConsultDTO response = service.consultBookById(bookId);

        assertNotNull(response);
        assertEquals(bookId, response.getData().get_id());

        verify(redisTemplate).hasKey(bookId);
        verify(valueOperations).get(bookId);
        verifyNoMoreInteractions(repository, listOperations);
    }

    @Test
    void consultBookByIdReturnFromMongo() throws Exception {
        String bookId = "160";
        Book book = Book.builder()
                .title("Test Book")
                ._id(bookId)
                .build();

        when(repository.findById(bookId)).thenReturn(Optional.of(book));
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        ResponseBookConsultDTO result = service.consultBookById(bookId);

        assertNotNull(result);
        assertEquals(bookId, result.getData().get_id());
        assertEquals("Test Book", result.getData().getTitle());
    }

    @Test
    void consultBookByIdNotFound() throws Exception {
        String bookId = "99";

        when(redisTemplate.hasKey(bookId)).thenReturn(false);
        when(repository.findById(bookId)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.consultBookById(bookId);
        });

        assertEquals("book id 99 was not found in database", thrown.getMessage());

        verify(repository).findById(bookId);
    }

    @Test
    void consultBookByAuthor(){
        String authorName = "Author1";

        List<Book> books = List.of(
                Book.builder()._id("1").author("Author1").build(),
                Book.builder()._id("2").author("Author2").build()
        );

        when(repository.findByAuthor(authorName)).thenReturn(Optional.of(books));

        ResponseBookListConsultDTO response = service.consultBookByAuthor(authorName);

        assertNotNull(response);
        assertEquals(2, response.getData().size());
        assertEquals(authorName, response.getData().get(0).getAuthor());

        verify(repository).findByAuthor(authorName);
    }

    @Test
    void consultBookByAuthorNotFound() throws Exception {
        String bookAuthor = "Author1";

        when(repository.findByAuthor(bookAuthor)).thenReturn(Optional.of(Collections.emptyList()));

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.consultBookByAuthor(bookAuthor);
        });

        assertEquals("Not found books for author: Author1", thrown.getMessage());

        verify(repository).findByAuthor(bookAuthor);
    }

    @Test
    void consultBookByGenre(){
        String genre = "Genre1";

        List<Book> books = List.of(
                Book.builder()._id("1").author("Genre1").build(),
                Book.builder()._id("2").author("Genre2").build()
        );

        when(repository.findByGenre(genre)).thenReturn(Optional.of(books));

        ResponseBookListConsultDTO response = service.consultBookByGenre(genre);

        assertNotNull(response);
        assertEquals(2, response.getData().size());
        assertEquals(genre, response.getData().get(0).getAuthor());

        verify(repository).findByGenre(genre);
    }

    @Test
    void consultBookByGenreNotFound() throws Exception {
        String genre = "Genre1";

        when(repository.findByGenre(genre)).thenReturn(Optional.of(Collections.emptyList()));

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.consultBookByGenre(genre);
        });

        assertEquals("Not found books for genre: Genre1", thrown.getMessage());

        verify(repository).findByGenre(genre);
    }

    @Test
    void consultAllBook(){
        int page = 0;
        int pageSize = 2;

        List<Book> books = List.of(
                Book.builder()._id("1").author("Author1").genre("Genre1").build(),
                Book.builder()._id("2").author("Author2").genre("Genre2").build()
        );

        Page<Book> bookPage = new PageImpl<>(books);

        when(repository.findAll(PageRequest.of(page, pageSize))).thenReturn(bookPage);

        ResponseBookListConsultDTO response = service.consultAllBooks(page, pageSize);

        assertNotNull(response);
        assertEquals(2, response.getData().size());
        assertEquals("1", response.getData().get(0).get_id());

        verify(repository).findAll(PageRequest.of(page, pageSize));
    }

    @Test
    void consultBookByNotNountAnyBook() throws Exception {
        int page = 0;
        int pageSize = 2;

        Page<Book> emptyPage = new PageImpl<>(List.of());

        when(repository.findAll(PageRequest.of(page, pageSize))).thenReturn(emptyPage);

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.consultAllBooks(page, pageSize);
        });

        assertEquals("Not found any books", thrown.getMessage());

        verify(repository).findAll(PageRequest.of(page, pageSize));
    }

    @Test
    void consultRecentlyBooksFromMongo() {
        List<Object> redisList = List.of(
                "{\"_id\":\"1\",\"title\":\"Book 1\"}",
                "{\"_id\":\"2\",\"title\":\"Book 2\"}",
                "{\"_id\":\"1\",\"title\":\"Book 1\"}"  // Duplicado para testar distinct()
        );

        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.range("books", 0, 9)).thenReturn(redisList);

        ResponseBookListConsultDTO response = service.consultRecentlyBooks();

        assertNotNull(response);
        assertEquals(2, response.getData().size());  // Distinct elimina duplicado
        assertEquals("1", response.getData().get(0).get_id());
        assertEquals("2", response.getData().get(1).get_id());

        verify(redisTemplate).opsForList();
        verify(listOperations).range("books", 0, 9);
    }

    @Test
    void consultRecentlyBooksNotFound() {
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.range("books", 0, 9)).thenReturn(List.of());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            service.consultRecentlyBooks();
        });

        assertEquals("Not found books recently consulted", thrown.getMessage());

        verify(redisTemplate).opsForList();
        verify(listOperations).range("books", 0, 9);
    }

}