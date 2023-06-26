package com.example.consumer;

import com.example.consumer.model.Bookshelf;
import com.example.consumer.model.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookServiceAdapter {

    private final RestTemplate restTemplate;

    @Autowired
    public BookServiceAdapter(RestTemplate restTemplate) {
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