package com.demo.webflux_demo.service;

import com.demo.webflux_demo.dto.MultiplyRequestDto;
import com.demo.webflux_demo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class ReactiveMathService {
    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> input * input)
                   .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1, 10)
                   .delayElements(Duration.ofSeconds(1))
//                   .doOnNext(i -> SleepUtil.sleepSeconds(1))
                   .doOnNext(i -> log.debug("reactive processing: {}", i))
                   .map(i -> new Response(i * input));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono) {
        return dtoMono.map(dto -> dto.getFirst() * dto.getSecond())
                      .map(Response::new);
    }
}
