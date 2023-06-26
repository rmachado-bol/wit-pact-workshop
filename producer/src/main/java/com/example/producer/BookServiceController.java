package com.example.producer;

import com.example.producer.model.AvailableLibrary;
import com.example.producer.model.Bookshelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookServiceController {

    private final BooksRepository booksRepository;

    @Autowired
    public BookServiceController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GetMapping("/bookshelf")
    public Bookshelf getBookshelf(@RequestParam String customerId) {
        Optional<Bookshelf> bookshelf = booksRepository.getBookshelf(customerId);
        return bookshelf.orElseGet(() -> new Bookshelf(List.of()));
    }

    @GetMapping("/catalog")
    public AvailableLibrary getCatalog(@RequestParam String customerId) {
        return new AvailableLibrary (
                booksRepository.getLibrary(customerId)
        );
    }
}