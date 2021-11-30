package com.epam.pashkova.episodatelistener.exception;

public class JsonGenerationException extends RuntimeException {
    public JsonGenerationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
