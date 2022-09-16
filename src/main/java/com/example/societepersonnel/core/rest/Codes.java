package com.example.societepersonnel.core.rest;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Codes {

    ERR_ADRESS_NOT_FOUND("Adresse Not Found", HttpStatus.NOT_FOUND),
    ERR_ADRESS_NOT_VAlID("Adresse Not Valid : Il faut ajouter l'adresse ", HttpStatus.NOT_ACCEPTABLE),
    ERR_ADRESS_NOT_EXIST("Adresse Not Exist", HttpStatus.NOT_EXTENDED),

    ERR_ENTREPRESE_NOT_FOUND("Entreprise Not Found", HttpStatus.NOT_FOUND),
    ERR_ENTREPRESE_NOT_VAlID("Entreprise Not Valid", HttpStatus.NOT_ACCEPTABLE),
    ERR_ENTREPRESE_NOT_EXIST("Entreprise Not Exist", HttpStatus.NOT_EXTENDED),

    ERR_PERSONNEL_NOT_FOUND("Personnel Not Found", HttpStatus.NOT_FOUND),
    ERR_PERSONNEL_NOT_VAlID("Personnel Not Valid", HttpStatus.NOT_ACCEPTABLE),
    ERR_PERSONNEL_NOT_EXIST("Personnel Not Exist", HttpStatus.NOT_EXTENDED);

    private String message;
    private HttpStatus httpStatus;

    Codes(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
