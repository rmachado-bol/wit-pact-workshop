package com.example.consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerAPI {

    private ProducerClient producerClient;

    public ConsumerAPI(ProducerClient producerClient) {
        this.producerClient = producerClient;
    }

    @GetMapping("/bookshelf")
    public Bookshelf getBookshelf(@RequestParam String customerId) {
        return producerClient.getBookshelf(customerId);
    }

    @GetMapping("/catalog")
    public Catalog getCatalog(@RequestParam String customerId) {
        return producerClient.getCatalog(customerId);
    }
}