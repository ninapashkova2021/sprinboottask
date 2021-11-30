package com.epam.pashkova.episodatelistener.exception;

public class S3UploadException extends RuntimeException {
    public S3UploadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
