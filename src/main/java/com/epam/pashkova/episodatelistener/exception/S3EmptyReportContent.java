package com.epam.pashkova.episodatelistener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Nothing to upload to S3")
public class S3EmptyReportContent extends RuntimeException {
    public S3EmptyReportContent(String message) {
        super(message);
    }
}
