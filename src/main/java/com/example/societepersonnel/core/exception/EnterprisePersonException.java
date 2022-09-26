package com.example.societepersonnel.core.exception;

import com.example.societepersonnel.core.rest.Codes;
import lombok.Getter;

@Getter
public class EnterprisePersonException extends RuntimeException {

    private Codes codes;

    public EnterprisePersonException(Codes codes) {
        super(codes.getMessage());
        this.codes = codes;
    }


}
