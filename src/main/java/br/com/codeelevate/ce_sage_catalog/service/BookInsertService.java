package br.com.codeelevate.ce_sage_catalog.service;

import br.com.codeelevate.ce_sage_catalog.model.dto.RsponseBookConsultDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface BookInsertService {
    public void insertBooksByAuthor(String authorName) throws JsonProcessingException;

}
