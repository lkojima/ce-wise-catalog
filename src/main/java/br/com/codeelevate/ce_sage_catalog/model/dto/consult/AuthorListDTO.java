package br.com.codeelevate.ce_sage_catalog.model.dto.consult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorListDTO {
    private String name;
    private String key;
}
