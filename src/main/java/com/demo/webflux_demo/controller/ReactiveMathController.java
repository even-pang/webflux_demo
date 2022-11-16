package com.demo.webflux_demo.controller;

import com.demo.webflux_demo.dto.MultiplyRequestDto;
import com.demo.webflux_demo.dto.Response;
import com.demo.webflux_demo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reactive-math")
public class ReactiveMathController {
    private final ReactiveMathService mathService;

    @GetMapping("/square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return mathService.findSquare(input)
                          .defaultIfEmpty(new Response(-1));
    }

    @GetMapping("/table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }

    @GetMapping(value = "/table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }

    @PostMapping("/multiply")
    public Mono<Response> multiply(@RequestBody final Mono<MultiplyRequestDto> dto,
                                   @RequestHeader Map<String, String> headers) {
        log.debug("{}", headers);
        return mathService.multiply(dto);
    }
}
