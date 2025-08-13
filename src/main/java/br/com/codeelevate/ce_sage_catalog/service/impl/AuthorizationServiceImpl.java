package br.com.codeelevate.ce_sage_catalog.service.impl;

import br.com.codeelevate.ce_sage_catalog.exception.handler.NotFoundException;
import br.com.codeelevate.ce_sage_catalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthorizationServiceImpl implements UserDetailsService {

    @Autowired private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = repository.findByLogin(username);
        if(Objects.isNull(userDetails)){
            throw new NotFoundException("Not found any user with this credentials");
        }
        return userDetails;
    }
}