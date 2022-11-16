package com.demo.webflux_demo.config;

import com.demo.webflux_demo.dto.InputFailedValidationResponse;
import com.demo.webflux_demo.exception.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions
                .route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

//    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions
                .route()
                .GET("square/{input}",
                        RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")
                        ), requestHandler::squareHandler)
                .GET("square/{input}", request -> ServerResponse.badRequest().bodyValue("only 10-20 allowed"))
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandle())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandle() {
        return (err, request) -> {
            InputValidationException exception = (InputValidationException) err;
            var input = exception.getInput();
            var errorCode = InputValidationException.errorCode();
            var message = InputValidationException.message();
            final InputFailedValidationResponse response = new InputFailedValidationResponse(errorCode, input, message);

            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
