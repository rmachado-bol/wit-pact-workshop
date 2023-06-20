package com.example.producer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BooksRepository {

    private final Map<String, Bookshelf> BOOKSHELVES = new HashMap<>();
    private final List<Book> LIBRARY = new ArrayList<>();

    private void initLibrary() {
        LIBRARY.add(new Book("You Exist Too Much", "Zaina Arafat", "9781948226509"));
        LIBRARY.add(new Book("Tomorrow, and Tomorrow, and Tomorrow", "Gabrielle Zevin", "9780593321201"));
        LIBRARY.add(new Book("Beautiful World, Where Are You", "Sally Rooney", "9780374602604"));
        LIBRARY.add(new Book("The Midnight Library", "Matt Haig", "9788413621661"));
        LIBRARY.add(new Book("Blood Sugar", "Sascha Rothchild", "9780593331545"));
        LIBRARY.add(new Book("All the Light We Cannot See", "Anthony Doerr", "0008485194"));
        LIBRARY.add(new Book("Cloud Cuckoo Land", "Anthony Doerr", "9781982168438"));
        LIBRARY.add(new Book("Untamed", "Glennon Doyle Melton", "9781984801258"));
    }

    private void initBookshelves() {
        BOOKSHELVES.put("1", new Bookshelf(
                List.of(
                        new Book("You Exist Too Much", "Zaina Arafat", "9781948226509"),
                        new Book("Tomorrow, and Tomorrow, and Tomorrow", "Gabrielle Zevin", "9780593321201")
                )));

        BOOKSHELVES.put("2", new Bookshelf(
                List.of(
                        new Book("Beautiful World, Where Are You", "Sally Rooney", "9780374602604"),
                        new Book("The Midnight Library", "Matt Haig", "9788413621661"),
                        new Book("Blood Sugar", "Sascha Rothchild", "9780593331545")
                )));
    }

    public Optional<Bookshelf> getBookshelf(String customerId) {
        initBookshelves();
        return Optional.ofNullable(BOOKSHELVES.get(customerId));
    }


    public List<Book> getLibrary(String customerId) {
        initLibrary();
        return LIBRARY;
    }
}
