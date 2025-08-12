package br.com.codeelevate.ce_sage_catalog.service;

import br.com.codeelevate.ce_sage_catalog.repository.UserRepository;
import br.com.codeelevate.ce_sage_catalog.service.impl.AuthorizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorizationServiceImplTest {

    @InjectMocks
    private AuthorizationServiceImpl authorizationServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_existingUser_returnsUserDetails() {
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);
        when(userRepository.findByLogin(username)).thenReturn(userDetails);

        UserDetails result = authorizationServiceImpl.loadUserByUsername(username);

        assertNotNull(result);
        assertEquals(userDetails, result);
    }

    @Test
    void loadUserByUsername_userNotFound_throwsException() {
        String username = "unknownUser";
        when(userRepository.findByLogin(username)).thenReturn(null);

        UserDetails result = authorizationServiceImpl.loadUserByUsername(username);
        assertNull(result);
    }
}
