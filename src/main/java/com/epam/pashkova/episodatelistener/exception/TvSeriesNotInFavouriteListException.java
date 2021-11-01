package com.epam.pashkova.episodatelistener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "TV series not in subscriber's favourite list")
public class TvSeriesNotInFavouriteListException extends RuntimeException {
    public TvSeriesNotInFavouriteListException(String message) {
        super(message);
    }
}
