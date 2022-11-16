package com.demo.webflux_demo.wc;

import com.demo.webflux_demo.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;

public class Lec7QueryParamsTest extends BaseTest {
    String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";
    @Autowired
    private WebClient webClient;

    @Test
    public void queryParamsTest() {
        final URI uri = UriComponentsBuilder.fromUriString(queryString).build(10, 20);

        final Flux<Integer> integerFlux = webClient.get()
                                                   .uri(uri)
                                                   .retrieve()
                                                   .bodyToFlux(Integer.class)
                                                   .doOnNext(logConsumer());

        StepVerifier.create(integerFlux)
                    .expectNextCount(2)
                    .verifyComplete();
    }
}
