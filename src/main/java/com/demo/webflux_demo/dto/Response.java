package com.demo.webflux_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private Date date = new Date();
    private int output;

    public Response(final int output) {
        this.output = output;
    }
}
