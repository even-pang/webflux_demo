package com.demo.webflux_demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:8080")
                .filter(this::sessionToken)
//                .defaultHeaders(h -> h.setBasicAuth("username", "password"))
                .build();
    }

    /*private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction exchangeFunction) {
        log.debug("generating session token");
        final ClientRequest clientRequest = ClientRequest.from(request)
                                                         .headers(h -> h.setBearerAuth("awt-bearer-token"))
                                                         .build();

        return exchangeFunction.exchange(clientRequest);
    }*/

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction exchangeFunction) {
        final ClientRequest clientRequest = request.attribute("auth")
                                                   .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                                                   .orElse(request);

        return exchangeFunction.exchange(clientRequest);
    }

    private ClientRequest withBasicAuth(ClientRequest request) {
        return ClientRequest.from(request)
                            .headers(h -> h.setBasicAuth("username", "password"))
                            .build();
    }

    private ClientRequest withOAuth(ClientRequest request) {
        return ClientRequest.from(request)
                            .headers(h -> h.setBearerAuth("some-token"))
                            .build();
    }
}
