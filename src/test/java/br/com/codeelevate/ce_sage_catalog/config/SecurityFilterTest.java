package br.com.codeelevate.ce_sage_catalog.config;

import br.com.codeelevate.ce_sage_catalog.filter.SecurityFilter;
import br.com.codeelevate.ce_sage_catalog.repository.UserRepository;
import br.com.codeelevate.ce_sage_catalog.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityFilterTest {

    @InjectMocks
    private SecurityFilter securityFilter;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_withValidToken_shouldSetAuthentication() throws ServletException, IOException {
        String token = "valid-token";
        String login = "user-login";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenService.validateToken(token)).thenReturn(login);
        when(userRepository.findByLogin(login)).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(java.util.Collections.emptyList());

        securityFilter.doFilterInternal(request, response, filterChain);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(userDetails, authentication.getPrincipal());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_withoutToken_shouldNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }
}