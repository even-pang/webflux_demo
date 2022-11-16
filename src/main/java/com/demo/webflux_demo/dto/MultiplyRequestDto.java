package com.demo.webflux_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiplyRequestDto {
    private Integer first;
    private Integer second;
}
