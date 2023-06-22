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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                            .stringType("title")
                            .stringType("author")
                            .stringType("isbn")
                )
                .toPact();
    }

    @Pact(consumer = "DigitalLibrary", provider = "BookService")
    RequestResponsePact getBookshelfForCustomerWithNoBooks(PactDslWithProvider builder) {
        return builder.given("customerId 5 has no books")
                .uponReceiving("get bookshelf for customerId 5")
                .method("GET")
                .path("/bookshelf")
                .query("customerId=5")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json;"))
                .body(new PactDslJsonBody().array("books"))
                .toPact();
    }

    @Pact(consumer = "DigitalLibrary", provider = "BookService")
    RequestResponsePact getCatalogForCustomer(PactDslWithProvider builder) {
        return builder.given("catalog exists")
                .uponReceiving("get catalog for customerId 1")
                .method("GET")
                .path("/catalog")
                .query("customerId=1")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json;"))
                .body(new PactDslJsonBody()
                            .minArrayLike("availableBooks", 5)
                                .stringType("title", "You Exist Too Much")
                                .stringType("author", "Zaina Arafat")
                                .stringType("isbn", "9781948226509")
// If we need to check a list of required variants that can have different structures
//                                .arrayContaining("availableBooks")
//                                    .object()
//                                    .closeObject()
//                                .closeArray()
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getBookshelfForCustomer", pactVersion = PactSpecVersion.V3)
    public void getBookshelf_whenBookshelfForCustomerWithId1Exists(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();

        Bookshelf actual = new ProducerClient(restTemplate).getBookshelf("1");

        assertNotNull(actual.getBooks());
        assertTrue(actual.getBooks().size() > 0);
    }

    @Test
    @PactTestFor(pactMethod = "getBookshelfForCustomerWithNoBooks", pactVersion = PactSpecVersion.V3)
    public void getBookshelf_whenCustomerWithId5HasNoBooks(MockServer mockServer) {
        Bookshelf expected = new Bookshelf(List.of());

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();

        Bookshelf actual = new ProducerClient(restTemplate).getBookshelf("5");

        assertEquals(expected, actual);
    }

    @Test
    @PactTestFor(pactMethod = "getCatalogForCustomer", pactVersion = PactSpecVersion.V3)
    public void getCatalog_whenCatalogExists(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();

        Catalog actual = new ProducerClient(restTemplate).getCatalog("1");

        assertNotNull(actual.getAvailableBooks());
        assertTrue(actual.getAvailableBooks().size() > 4);
    }


}
