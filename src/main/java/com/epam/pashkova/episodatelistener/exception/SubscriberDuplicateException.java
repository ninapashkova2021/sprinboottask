package com.epam.pashkova.episodatelistener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Subscriber was already created")
public class SubscriberDuplicateException extends RuntimeException {
    public SubscriberDuplicateException(String message) {
        super(message);
    }
}
