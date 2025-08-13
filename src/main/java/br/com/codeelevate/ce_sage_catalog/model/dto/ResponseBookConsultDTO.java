package br.com.codeelevate.ce_sage_catalog.model.dto;

import br.com.codeelevate.ce_sage_catalog.model.Book;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookConsultDTO {
    private Book data;

}
