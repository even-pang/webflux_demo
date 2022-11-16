package com.demo.webflux_demo.wc;

import com.demo.webflux_demo.BaseTest;
import com.demo.webflux_demo.dto.InputFailedValidationResponse;
import com.demo.webflux_demo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class Lec6ExchangeTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest() {
        final Mono<Object> responseMono = webClient.get()
                                                   .uri("/reactive-math/square/{number}/throw", 5)
                                                   .exchangeToMono(this::exchange)
                                                   .doOnNext(r -> log.info("{}", r))
                                                   .doOnError(err -> log.warn("{}", err.getMessage()));

        StepVerifier.create(responseMono)
                    .expectNextCount(1)
                    .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        if (clientResponse.rawStatusCode() == 400) {
            return clientResponse.bodyToMono(InputFailedValidationResponse.class);
        } else {
            return clientResponse.bodyToMono(Response.class);
        }
    }
}
