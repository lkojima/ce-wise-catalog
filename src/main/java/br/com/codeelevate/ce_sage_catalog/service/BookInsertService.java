package br.com.codeelevate.ce_sage_catalog.service;

import br.com.codeelevate.ce_sage_catalog.model.dto.ResponseBookListConsultDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface BookInsertService {
    public JsonNode insertBooksByGenre(String authorName) throws JsonProcessingException;

}
