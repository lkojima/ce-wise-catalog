package br.com.codeelevate.ce_sage_catalog.service.impl;

import br.com.codeelevate.ce_sage_catalog.client.ConsultBooksClient;
import br.com.codeelevate.ce_sage_catalog.model.Book;
import br.com.codeelevate.ce_sage_catalog.model.dto.consult.BookConsultDTO;
import br.com.codeelevate.ce_sage_catalog.model.dto.consult.Counter;
import br.com.codeelevate.ce_sage_catalog.model.dto.consult.WorksDTO;
import br.com.codeelevate.ce_sage_catalog.repository.BookConsultRepository;
import br.com.codeelevate.ce_sage_catalog.service.BookInsertService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookInsertControllerImpl implements BookInsertService {

    @Autowired
    ConsultBooksClient consultBooksClient;
    @Autowired
    BookConsultRepository repository;
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void insertBooksByAuthor(String genre) throws JsonProcessingException {

        String listString = consultBooksClient.consultBooksByAuthor(genre);
        List<Book> bookList = transformToListBook(listString);

        for (Book book : bookList) {
            if(repository.findByTitle(book.getTitle()).get().isEmpty()){
                book.set_id(getNextSequence("subject_seq"));
                repository.save(book);
                System.out.println("Insert: " + book.toString());
            }
        }
    }

    private String getNextSequence(String subjectSeq) {
        Counter counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(subjectSeq)),
                new Update().inc("seq", 1),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                Counter.class
        );
        return String.valueOf(counter.getSeq());
    }

    private List<Book> transformToListBook(String listString) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        BookConsultDTO bookConsult = mapper.readValue(listString, BookConsultDTO.class);

        List<Book> listOfBooks = new ArrayList<>();
        for(WorksDTO works : bookConsult.getWorks()) {
            Book book = Book.builder()
                    .title(works.getTitle())
                    .genre(bookConsult.getName())
                    .author(works.getAuthors().get(0).getName())
                    .publishYear(works.getFirst_publish_year())
                    .build();

            listOfBooks.add(book);
        }
        return listOfBooks;
    }
}
