package br.com.codeelevate.ce_sage_catalog.model.dto;

import br.com.codeelevate.ce_sage_catalog.model.Book;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RsponseBookListConsultDTO {
    private List<Book> data;
}
