package br.com.codeelevate.ce_sage_catalog.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RsponseBookListConsultDTO {
    private List<br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookConsultDTO> consultBookList;
}
