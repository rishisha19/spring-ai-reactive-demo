package com.springai.demo.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponse {
    private String response;
    private String model;
    private LocalDateTime timestamp;
    private Integer tokensUsed;
}
