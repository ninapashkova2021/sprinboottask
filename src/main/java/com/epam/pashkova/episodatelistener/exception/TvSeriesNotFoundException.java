package com.epam.pashkova.episodatelistener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Unable to find TV series")
public class TvSeriesNotFoundException extends RuntimeException {
    public TvSeriesNotFoundException(String message) {
        super(message);
    }
}
