package org.anonymous.card.exceptions;

import org.anonymous.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class CardDataNotFoundException extends CommonException {

    public CardDataNotFoundException() {
        super("NotFound.CardData", HttpStatus.NOT_FOUND);

        setErrorCode(true);
    }
}
