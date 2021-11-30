package com.epam.pashkova.episodatelistener.exception;

public class S3BucketNotFoundException extends RuntimeException {
    public S3BucketNotFoundException(String message) {
        super(message);
    }
}
