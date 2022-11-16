package com.demo.webflux_demo.service;

import com.demo.webflux_demo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class MathService {
    public Response findSquare(int input) {
        return new Response(input * input);
    }

    public List<Response> multiplicationTable(int input) {
        return IntStream
                .rangeClosed(1, 10)
                .peek(i -> SleepUtil.sleepSeconds(1))
                .peek(i -> log.debug("processing: {}", i))
                .mapToObj(i -> new Response(i * input))
                .toList();
    }
}
