package br.com.codeelevate.ce_sage_catalog.repository;

import br.com.codeelevate.ce_sage_catalog.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookConsultRepository extends MongoRepository<Book, String> {
    Optional<List<Book>>findByAuthor(String authorName);

    Optional<List<Book>> findByGenre(String genre);
}
