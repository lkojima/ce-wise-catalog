package br.com.codeelevate.ce_sage_catalog.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private Double price;
}
