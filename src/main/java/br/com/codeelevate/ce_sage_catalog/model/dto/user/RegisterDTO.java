package br.com.codeelevate.ce_sage_catalog.model.dto.user;


public record RegisterDTO(String login, String password, UserRole role) {
}
