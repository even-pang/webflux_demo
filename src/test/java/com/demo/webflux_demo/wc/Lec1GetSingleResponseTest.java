package com.demo.webflux_demo.wc;

import com.demo.webflux_demo.BaseTest;
import com.demo.webflux_demo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec1GetSingleResponseTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
        final Response response = webClient.get()
                                           .uri("/reactive-math/square/{input}", 5)
                                           .retrieve()
                                           .bodyToMono(Response.class)
                                           .block();

        System.out.println(response);
    }

    @Test
    public void stepVerifierTest() {
        final Mono<Response> responseMono = webClient.get()
                                                     .uri("/reactive-math/square/{input}", 5)
                                                     .retrieve()
                                                     .bodyToMono(Response.class);

        StepVerifier.create(responseMono)
                    .expectNextMatches(response -> response.getOutput() == 25)
                    .verifyComplete();
    }
}
