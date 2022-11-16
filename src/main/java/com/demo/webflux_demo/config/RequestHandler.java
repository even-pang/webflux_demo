package com.demo.webflux_demo.config;

import com.demo.webflux_demo.dto.MultiplyRequestDto;
import com.demo.webflux_demo.dto.Response;
import com.demo.webflux_demo.exception.InputValidationException;
import com.demo.webflux_demo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {
    private final ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        final int input = Integer.parseInt(serverRequest.pathVariable("input"));
        final Mono<Response> responseMono = mathService.findSquare(input);

        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        final int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }
        final Mono<Response> responseMono = mathService.findSquare(input);

        return ServerResponse.ok().body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        final int input = Integer.parseInt(serverRequest.pathVariable("input"));
        final Flux<Response> responseFlux = mathService.multiplicationTable(input);

        return ServerResponse.ok().body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        final int input = Integer.parseInt(serverRequest.pathVariable("input"));
        final Flux<Response> responseFlux = mathService.multiplicationTable(input);

        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_EVENT_STREAM)
                             .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        final Mono<MultiplyRequestDto> requestDtoMono = serverRequest.bodyToMono(MultiplyRequestDto.class);
        final Mono<Response> responseMono = mathService.multiply(requestDtoMono);

        return ServerResponse.ok().body(responseMono, Response.class);
    }
}
