package com.example.societepersonnel.core.rest;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ServerResponse {

    private LocalDateTime timeStamp;
    private String message;

    public ServerResponse(LocalDateTime localDateTime, String message) {
        this.timeStamp = localDateTime;
        this.message = message;
    }
}
