package org.anonymous.card.exceptions;

import org.anonymous.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class CardBadRequestException extends CommonException {
    public CardBadRequestException() {
        super("BadRequest.card", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }
}
