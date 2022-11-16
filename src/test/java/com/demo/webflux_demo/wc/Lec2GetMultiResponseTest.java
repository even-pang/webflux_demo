package com.demo.webflux_demo.wc;

import com.demo.webflux_demo.BaseTest;
import com.demo.webflux_demo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class Lec2GetMultiResponseTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void fluxTest() {
        final Flux<Response> responseFlux = webClient.get()
                                                     .uri("/reactive-math/table/{input}", 5)
                                                     .retrieve()
                                                     .bodyToFlux(Response.class)
                                                     .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                    .expectNextCount(10)
                    .verifyComplete();
    }

    @Test
    public void fluxStreamTest() {
        final Flux<Response> responseFlux = webClient.get()
                                                     .uri("/reactive-math/table/{input}/stream", 5)
                                                     .retrieve()
                                                     .bodyToFlux(Response.class)
                                                     .doOnNext(response -> log.info("{}", response));

        StepVerifier.create(responseFlux)
                    .expectNextCount(10)
                    .verifyComplete();
    }
}
