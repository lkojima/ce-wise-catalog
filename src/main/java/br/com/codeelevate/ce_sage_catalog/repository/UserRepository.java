package br.com.codeelevate.ce_sage_catalog.repository;

import br.com.codeelevate.ce_sage_catalog.model.dto.user.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
    UserDetails findByLogin(String login);
}