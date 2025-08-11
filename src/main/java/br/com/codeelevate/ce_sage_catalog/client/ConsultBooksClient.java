package br.com.codeelevate.ce_sage_catalog.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConsultBooksClient {

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(
            value = {HttpClientErrorException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public String consultBooksByAuthor(String genre) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.exchange("https://openlibrary.org/subjects/"+genre.toLowerCase()+".json", HttpMethod.GET, null, String.class);

        return response.getBody();
    }


}
