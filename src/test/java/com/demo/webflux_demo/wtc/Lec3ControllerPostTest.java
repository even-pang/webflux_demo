package com.demo.webflux_demo.wtc;

import com.demo.webflux_demo.controller.ReactiveMathController;
import com.demo.webflux_demo.dto.MultiplyRequestDto;
import com.demo.webflux_demo.dto.Response;
import com.demo.webflux_demo.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = {ReactiveMathController.class})
public class Lec3ControllerPostTest {
    @Autowired
    private WebTestClient client;
    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void postTest() {
        Mockito.when(reactiveMathService.multiply(Mockito.any()))
               .thenReturn(Mono.just(new Response(1)));

        client.post()
              .uri("/reactive-math/multiply")
              .accept(MediaType.APPLICATION_JSON)
              .headers(h -> h.setBasicAuth("username", "password"))
              .headers(h -> h.set("somekey", "somevalue"))
              .bodyValue(new MultiplyRequestDto())
              .exchange()
              .expectStatus().is2xxSuccessful();
    }
}
