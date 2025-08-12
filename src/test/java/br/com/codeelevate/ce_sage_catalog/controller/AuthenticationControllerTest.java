package br.com.codeelevate.ce_sage_catalog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.codeelevate.ce_sage_catalog.controller.AuthenticationController;
import br.com.codeelevate.ce_sage_catalog.model.dto.user.*;
import br.com.codeelevate.ce_sage_catalog.repository.UserRepository;
import br.com.codeelevate.ce_sage_catalog.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository repository;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationController controller;

    @Test
    void testLoginSuccess() {
        // Arrange
        AuthenticationDTO dto = new AuthenticationDTO("user", "pass");
        UserEntity userEntity = new UserEntity("user", "encodedPass", UserRole.USER);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(userEntity);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenService.generateToken(userEntity)).thenReturn("fake-jwt");

        // Act
        ResponseEntity<LoginResponseDTO> response = controller.login(dto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("fake-jwt", response.getBody().token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(userEntity);
    }

    @Test
    void testRegisterUserAlreadyExists() {
        RegisterDTO dto = new RegisterDTO("user", "pass", UserRole.USER);
        when(repository.findByLogin("user")).thenReturn(new UserEntity());

        ResponseEntity<?> response = controller.register(dto);

        assertEquals(400, response.getStatusCodeValue());
        verify(repository).findByLogin("user");
        verify(repository, never()).save(any());
    }

    @Test
    void testRegisterNewUserSuccess() {
        RegisterDTO dto = new RegisterDTO("newUser", "pass", UserRole.USER);
        when(repository.findByLogin("newUser")).thenReturn(null);

        ResponseEntity<?> response = controller.register(dto);

        assertEquals(200, response.getStatusCodeValue());
        verify(repository).findByLogin("newUser");
        verify(repository).save(any(UserEntity.class));
    }
}
