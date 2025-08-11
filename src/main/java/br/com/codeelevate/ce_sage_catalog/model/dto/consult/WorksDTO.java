package br.com.codeelevate.ce_sage_catalog.model.dto.consult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorksDTO {
    private String title;
    private List<AuthorListDTO> authors;
    private String first_publish_year;
}
