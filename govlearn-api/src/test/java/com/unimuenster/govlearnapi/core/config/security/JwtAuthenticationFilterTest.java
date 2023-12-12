package com.unimuenster.govlearnapi.core.config.security;

import com.unimuenster.govlearnapi.AbstractIntegrationTest;
import com.unimuenster.govlearnapi.initializer.InitializerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private InitializerService initializer;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void missingJWT() {

        String url = "http://localhost:" + port + "/api/v1/users";

        ResponseEntity response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void validJWT()  {

        String url = "http://localhost:" + port + "/api/v1/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(initializer.getUser1Token().token());
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void invalidJWT()  {

        String url = "http://localhost:" + port + "/api/v1/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("anythiung.at.all");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void publicEndpoint() {

        String url = "http://localhost:" + port + "/api/v1/health";

        ResponseEntity response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}