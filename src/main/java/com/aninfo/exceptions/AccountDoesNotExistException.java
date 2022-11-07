package com.aninfo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AccountDoesNotExistException extends RuntimeException {

    private final String MSG = "Account with cbu: %s does not exists";

    private Long cbu;

    public AccountDoesNotExistException(final Long cbu) {
        super();
        this.cbu = cbu;
    }

    @Override
    public String getMessage() {
        return format(MSG, cbu);
    }

}
