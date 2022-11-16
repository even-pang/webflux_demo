package com.demo.webflux_demo.controller;

import com.demo.webflux_demo.dto.Response;
import com.demo.webflux_demo.service.MathService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/math")
@RequiredArgsConstructor
public class MathController {
    private final MathService mathService;

    @GetMapping("/square/{input}")
    public Response findSquare(@PathVariable int input) {
        return mathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public List<Response> multiplicationTable(@PathVariable int input) {
        return mathService.multiplicationTable(input);
    }
}
