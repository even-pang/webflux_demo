package com.demo.webflux_demo.wtc;

import com.demo.webflux_demo.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class Lec1SimpleWebTestClientTest {
    @Autowired
    private WebTestClient client;

    @Test
    public void stepVerifierTest() {
        final Flux<Response> responseMono = client.get()
                                                  .uri("/reactive-math/square/{input}", 5)
                                                  .exchange()
                                                  .expectStatus()
                                                  .is2xxSuccessful()
                                                  .expectHeader()
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .returnResult(Response.class)
                                                  .getResponseBody();

        StepVerifier.create(responseMono)
                    .expectNextMatches(response -> response.getOutput() == 25)
                    .verifyComplete();
    }

    @Test
    public void fluentAssertionTest() {
        client.get()
              .uri("/reactive-math/square/{input}", 5)
              .exchange()
              .expectStatus()
              .is2xxSuccessful()
              .expectHeader()
              .contentType(MediaType.APPLICATION_JSON)
              .expectBody(Response.class)
              .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(25));
    }
}
