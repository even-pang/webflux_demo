package com.demo.webflux_demo.controller;

import com.demo.webflux_demo.dto.Response;
import com.demo.webflux_demo.exception.InputValidationException;
import com.demo.webflux_demo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reactive-math")
public class ReactiveMathValidationController {
    private final ReactiveMathService mathService;

    @GetMapping("/square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }

        return mathService.findSquare(input);
    }

    @GetMapping("/square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono
                .just(input)
                .handle((integer, sink) -> {
                    if (integer >= 10 && integer <= 20) sink.next(integer);
                    else sink.error(new InputValidationException(integer));
                })
                .cast(Integer.class)
                .flatMap(mathService::findSquare);
    }
}
