package com.epam.pashkova.episodatelistener.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.pashkova.episodatelistener.exception.S3BucketNotFoundException;
import com.epam.pashkova.episodatelistener.exception.SubscriberDuplicateException;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EpisodateS3ServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private EpisodateS3Service episodateS3Service;

    @Test
    void verifyThatSS3BucketWasCreated() {
        // given
        String bucketName = "bucketName1";

        // when
        BDDMockito.given(amazonS3.doesBucketExistV2(bucketName)).willReturn(false);
        episodateS3Service.createBucket(bucketName);

        // then
        Mockito.verify(amazonS3).createBucket(bucketName);
    }

    @Test
    void verifyThatSS3BucketWasNotCreated() {
        // given
        String bucketName = "bucketName1";

        // when
        BDDMockito.given(amazonS3.doesBucketExistV2(bucketName)).willReturn(true);
        episodateS3Service.createBucket(bucketName);

        // then
        Mockito.verify(amazonS3, Mockito.never()).createBucket(bucketName);
    }

    @Test
    void verifyThatFileFromBucketWasObtained() {
        // given
        String bucketName = "bucketName1";
        String fileName = "fileName1";

        // when
        BDDMockito.given(amazonS3.doesBucketExistV2(bucketName)).willReturn(true);
        episodateS3Service.getFileFromBucket(bucketName, fileName);

        // then
        Mockito.verify(amazonS3).getObject(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class));
    }

    @Test
    void verifyThatFileFromBucketWasNotObtained() {
        // given
        String bucketName = "bucketName1";
        String fileName = "fileName1";

        // when
        BDDMockito.given(amazonS3.doesBucketExistV2(bucketName)).willReturn(false);
        ThrowableAssert.ThrowingCallable throwingCallable = () -> episodateS3Service.getFileFromBucket(bucketName, fileName);

        // then
        assertThatThrownBy(throwingCallable)
                .as("It should cause exception")
                .isInstanceOf(S3BucketNotFoundException.class)
                .hasMessage(String.format("Bucket %s is not found", bucketName));
        Mockito.verify(amazonS3, Mockito.never()).getObject(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class));
    }
}