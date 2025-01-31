package org.anonymous.card.exceptions;

import org.anonymous.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class CardNotFoundException extends CommonException {
    public CardNotFoundException() {
        super("NotFound.card", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
