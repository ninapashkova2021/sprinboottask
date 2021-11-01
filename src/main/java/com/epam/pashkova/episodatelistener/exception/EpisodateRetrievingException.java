package com.epam.pashkova.episodatelistener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Couldn't retrieve info from Episodate")
public class EpisodateRetrievingException extends RuntimeException {
    public EpisodateRetrievingException(String message) {
        super(message);
    }
}
