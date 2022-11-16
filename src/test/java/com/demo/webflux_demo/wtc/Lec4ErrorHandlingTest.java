package com.demo.webflux_demo.wtc;

import com.demo.webflux_demo.controller.ReactiveMathValidationController;
import com.demo.webflux_demo.dto.Response;
import com.demo.webflux_demo.exception.InputValidationException;
import com.demo.webflux_demo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = {ReactiveMathValidationController.class})
public class Lec4ErrorHandlingTest {
    @Autowired
    private WebTestClient client;
    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void errorHandlingTest() {
        Mockito.when(reactiveMathService.findSquare(Mockito.anyInt()))
               .thenReturn(Mono.just(new Response(1)));

        client.get()
              .uri("/reactive-math/square/{number}/throw", 5)
              .exchange()
              .expectStatus().isBadRequest()
              .expectBody()
              .jsonPath("$.message").isEqualTo(InputValidationException.message())
              .jsonPath("$.errorCode").isEqualTo(100)
              .jsonPath("$.input").isEqualTo(5);
    }
}
