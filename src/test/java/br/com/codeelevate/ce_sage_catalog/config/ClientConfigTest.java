package br.com.codeelevate.ce_sage_catalog.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

class ClientConfigTest {

    @Test
    void restTemplate_shouldReturnRestTemplateInstance() {
        RestTemplateBuilder builder = mock(RestTemplateBuilder.class);
        RestTemplate restTemplateMock = mock(RestTemplate.class);

        when(builder.build()).thenReturn(restTemplateMock);

        ClientConfig config = new ClientConfig();
        RestTemplate restTemplate = config.restTemplate(builder);

        assertNotNull(restTemplate);
        assertEquals(restTemplateMock, restTemplate);

        verify(builder).build();
    }
}
