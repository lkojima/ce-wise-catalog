package br.com.codeelevate.ce_sage_catalog.service;

import br.com.codeelevate.ce_sage_catalog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService authorizationService;

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

        UserDetails result = authorizationService.loadUserByUsername(username);

        assertNotNull(result);
        assertEquals(userDetails, result);
    }

    @Test
    void loadUserByUsername_userNotFound_throwsException() {
        String username = "unknownUser";
        when(userRepository.findByLogin(username)).thenReturn(null);

        UserDetails result = authorizationService.loadUserByUsername(username);
        assertNull(result);
    }
}
