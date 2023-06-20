package com.example.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProducerClient {

    private final RestTemplate restTemplate;

    @Autowired
    public ProducerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Bookshelf getBookshelf(String customerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate.exchange("/bookshelf?customerId={id}",
                HttpMethod.GET,
                entity,
                Bookshelf.class,
                customerId
        ).getBody();
    }

    public Catalog getCatalog(String customerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restTemplate.exchange("/catalog?customerId={id}",
                HttpMethod.GET,
                entity,
                Catalog.class,
                customerId
        ).getBody();
    }
}