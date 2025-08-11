package br.com.codeelevate.ce_sage_catalog.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "books")
@Builder
public class Book {
    @Id
    private String _id;
    private String title;
    private String author;
    private String genre;
    private String publishYear;
}
