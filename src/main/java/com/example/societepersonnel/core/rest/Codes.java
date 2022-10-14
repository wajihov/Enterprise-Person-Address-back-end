package com.example.societepersonnel.core.rest;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Codes {

    ERR_ADDRESS_NOT_FOUND("ADDRESS NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_ADDRESSES_NOT_FOUND("ADDRESSES NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_ADDRESS_NOT_VAlID("ADDRESS NOT VALID", HttpStatus.NOT_ACCEPTABLE),
    ERR_ADDRESS_NOT_EXIST("ADDRESS NOT EXIST", HttpStatus.NOT_EXTENDED),

    ERR_ENTERPRISE_NOT_FOUND("ENTERPRISE NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_ENTERPRISES_NOT_FOUND("ENTERPRISES NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_ENTERPRISE_NOT_VAlID("ENTERPRISE NOT VALID", HttpStatus.NOT_ACCEPTABLE),
    ERR_ENTERPRISE_NOT_EXIST("ENTERPRISE NOT EXIST", HttpStatus.NOT_EXTENDED),

    ERR_PERSON_NOT_FOUND("PERSON NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_PERSONS_NOT_FOUND("PERSONS NOT FOUND", HttpStatus.NOT_FOUND),
    ERR_PERSON_NOT_VAlID("PERSON NOT VALID", HttpStatus.NOT_ACCEPTABLE),
    ERR_PERSON_NOT_EXIST("PERSON NOT EXIST", HttpStatus.NOT_EXTENDED);

    private String message;
    private HttpStatus httpStatus;

    Codes(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
