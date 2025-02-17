package org.anonymous.card.exceptions;

import org.anonymous.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class TrainLogNotFoundException extends CommonException {
    public TrainLogNotFoundException() {
        super("NotFound.TrainLog", HttpStatus.FOUND);

        setErrorCode(true);
    }
}
