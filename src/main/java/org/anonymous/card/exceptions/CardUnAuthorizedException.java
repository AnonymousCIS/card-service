package org.anonymous.card.exceptions;

import org.anonymous.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class CardUnAuthorizedException extends CommonException {
    public CardUnAuthorizedException() {
        super("Unauthorized.card", HttpStatus.UNAUTHORIZED);
        setErrorCode(true);
    }
}
