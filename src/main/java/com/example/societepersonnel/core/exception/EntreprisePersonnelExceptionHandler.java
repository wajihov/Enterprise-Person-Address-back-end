package com.example.societepersonnel.core.exception;

import com.example.societepersonnel.core.rest.ServerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class EntreprisePersonnelExceptionHandler {


    @ExceptionHandler(EntreprisePersonnelException.class)
    public ResponseEntity<ServerResponse> exception(EntreprisePersonnelException ex) {
        ServerResponse serverResponse = ServerResponse
                .builder()
                .message(ex.getCodes().getMessage())
                .timeStamp(LocalDateTime.now())
                .message(ex.getCodes().getMessage())
                .build();
        return new ResponseEntity<>(serverResponse, ex.getCodes().getHttpStatus());
    }

}
