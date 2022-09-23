package com.example.societepersonnel.core.exception;

import com.example.societepersonnel.core.rest.Codes;
import lombok.Getter;

@Getter
public class EnterprisePersonalException extends RuntimeException {

    private Codes codes;

    public EnterprisePersonalException(Codes codes) {
        super(codes.getMessage());
        this.codes = codes;
    }


}
