package com.example.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
public class BookServiceConsumerPactTest {

    @Pact(consumer = "DigitalLibrary", provider = "BookService")
    RequestResponsePact getBookshelfForCustomer(PactDslWithProvider builder) {
        return builder.given("bookshelf for customerId 1 exists")
                .uponReceiving("get bookshelf for customerId 1")
                .method("GET")
                .path("/bookshelf")
                .query("customerId=1")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json;"))
                .body(new PactDslJsonBody()
                        .eachLike("books")
                            .stringType("title", "You Exist Too Much")
                            .stringType("author", "Zaina Arafat")
                            .stringType("isbn", "9781948226509")
//                            .minArrayLike("books", 3)
//                                .stringType("title", "You Exist Too Much")
//                                .stringType("author", "Zaina Arafat")
//                                .stringType("isbn", "9781948226509")
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getBookshelfForCustomer", pactVersion = PactSpecVersion.V3)
    public void getBookshelf_whenBookshelfForCustomerWithId1Exists(MockServer mockServer) {
        Book book = new Book(
                "You Exist Too Much",
                "Zaina Arafat",
                "9781948226509"
        );

        Bookshelf expected = new Bookshelf(
                List.of(
                        book
//                        ,book
//                        ,book
                ));

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();

        Bookshelf actual = new ProducerClient(restTemplate).getBookshelf("1");

        assertEquals(expected, actual);
    }


}
