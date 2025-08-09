package br.com.codeelevate.ce_sage_catalog.repository;

import br.com.codeelevate.ce_sage_catalog.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookConsultRepository extends MongoRepository<Book, String> {
}
