package com.demo.webflux_demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Consumer;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseTest {
    protected <T> Consumer<T> logConsumer() {
        return i -> log.debug("{}", i);
    }
}
