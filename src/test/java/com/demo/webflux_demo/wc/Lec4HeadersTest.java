package com.demo.webflux_demo.wc;

import com.demo.webflux_demo.BaseTest;
import com.demo.webflux_demo.dto.MultiplyRequestDto;
import com.demo.webflux_demo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class Lec4HeadersTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest() {
        final Mono<Response> responseMono = webClient.post()
                                                     .uri("/reactive-math/multiply")
                                                     .bodyValue(buildRequestDto(5, 2))
                                                     .headers(h -> h.set("key", "value"))
//                                                     .headers(h -> h.setBasicAuth("username", "password"))
                                                     .retrieve()
                                                     .bodyToMono(Response.class)
                                                     .doOnNext(logConsumer());

        StepVerifier.create(responseMono)
                    .expectNextCount(1)
                    .verifyComplete();
        StepVerifier.create(responseMono)
                    .expectNextCount(1)
                    .verifyComplete();
    }

    private MultiplyRequestDto buildRequestDto(int a, int b) {
        return new MultiplyRequestDto(a, b);
    }
}
