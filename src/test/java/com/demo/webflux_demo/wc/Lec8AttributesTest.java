package com.demo.webflux_demo.wc;

import com.demo.webflux_demo.BaseTest;
import com.demo.webflux_demo.dto.MultiplyRequestDto;
import com.demo.webflux_demo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec8AttributesTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void attributeTest() {
        final Mono<Response> responseMono = webClient.post()
                                                     .uri("/reactive-math/multiply")
                                                     .bodyValue(buildRequestDto(5, 2))
//                                                     .attribute("auth", "oauth")
                                                     .retrieve()
                                                     .bodyToMono(Response.class)
                                                     .doOnNext(logConsumer());

        StepVerifier.create(responseMono)
                    .expectNextCount(1)
                    .verifyComplete();
    }

    private MultiplyRequestDto buildRequestDto(int a, int b) {
        return new MultiplyRequestDto(a, b);
    }
}
