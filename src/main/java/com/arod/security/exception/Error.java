package com.arod.security.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private String message;
    private int statusCode;
    private String status;
    private String url;
}
