package br.com.codeelevate.ce_sage_catalog.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultBooksClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ConsultBooksClient client;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(client, "restTemplate", restTemplate);
    }

    @Test
    void consultBooksByAuthor_shouldReturnResponseBody() throws JsonProcessingException {
        String genre = "fiction";
        String url = "https://openlibrary.org/subjects/" + genre.toLowerCase() + ".json";

        String mockJsonResponse = "{\"key\":\"value\"}";

        ResponseEntity<String> mockResponse = new ResponseEntity<>(mockJsonResponse, HttpStatus.OK);

        when(restTemplate.exchange("https://openlibrary.org/subjects/"+genre.toLowerCase()+".json", HttpMethod.GET, null, String.class)).thenReturn(new ResponseEntity<>("{\"key\":\"value\"}", HttpStatus.OK));
        String result = client.consultBooksByAuthor(genre);

        assertNotNull(result);
        assertEquals(mockJsonResponse, result);

        verify(restTemplate).exchange("https://openlibrary.org/subjects/"+genre.toLowerCase()+".json", HttpMethod.GET, null, String.class);
    }
}
