package com.personal.demo.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private boolean error;
    private String respCode;
    private String message;
    private long timestamp;
}