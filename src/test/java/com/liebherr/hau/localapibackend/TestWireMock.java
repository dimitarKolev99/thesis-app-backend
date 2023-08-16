package com.liebherr.hau.localapibackend;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;



import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = LocalapibackendApplication.class)
class TestWireMock {

    private WebTestClient httpBinClient;

    @Value("${appliance.url}")
    private String httpBinUrl;

    @BeforeEach
    void configureClient() {
        httpBinClient = WebTestClient.bindToServer().baseUrl(httpBinUrl).build();
    }

    @Test
    void returnStringExistsWhenSuccessful() {

//        httpBinClient.get()
//                .uri("/api/v1/appliance/d8b25784-c16f-449a-9006-6972e8a9111b")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .value(responseBody -> {
//                    assertEquals("exists!", responseBody);
//                });

    }

    @Test
    void returnStringAlreadyRegisteredWhenSuccessful() {

//        httpBinClient.get()
//                .uri("/api/v1/appliance/d7b25784-c16f-449a-9006-6972e8a9111b")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .value(responseBody -> {
//                    assertEquals("already registered", responseBody);
//                });

    }

    @Test
    void return404AnyOtherUUIDAndWhenSuccessful() {

//        httpBinClient.get()
//                .uri("/api/v1/appliance/d8b25784-c16f-439a-9006-6972e8a9111b")
//                .exchange()
//                .expectStatus().is4xxClientError();
    }

}
