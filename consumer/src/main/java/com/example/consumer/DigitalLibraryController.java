package com.example.consumer;

import com.example.consumer.model.Bookshelf;
import com.example.consumer.model.Catalog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DigitalLibraryController {

    private BookServiceAdapter producerClient;

    public DigitalLibraryController(BookServiceAdapter producerClient) {
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