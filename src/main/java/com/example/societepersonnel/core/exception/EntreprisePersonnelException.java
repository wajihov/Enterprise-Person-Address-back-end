package com.example.societepersonnel.core.exception;

import com.example.societepersonnel.core.rest.Codes;
import lombok.Getter;

@Getter
public class EntreprisePersonnelException extends RuntimeException {

    private Codes codes;

    public EntreprisePersonnelException(Codes codes) {
        super(codes.getMessage());
        this.codes = codes;
    }


}
