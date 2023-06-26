package com.example.producer;

import com.example.producer.model.Book;
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
        if (bookshelf.isPresent()) {
            return bookshelf.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("/catalog")
    public List<Book> getCatalog(@RequestParam String customerId) {
        return booksRepository.getLibrary(customerId);
    }
}