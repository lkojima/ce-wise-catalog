package br.com.codeelevate.ce_sage_catalog.model.dto.consult;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Counter {
    @Id
    private String id;
    private long seq;
}
