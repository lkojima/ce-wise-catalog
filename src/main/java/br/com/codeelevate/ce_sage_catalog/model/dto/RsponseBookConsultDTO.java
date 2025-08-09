package br.com.codeelevate.ce_sage_catalog.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RsponseBookConsultDTO {
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private Double price;

}
