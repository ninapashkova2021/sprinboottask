package com.epam.pashkova.episodatelistener.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.epam.pashkova.episodatelistener.exception.S3BucketNotFoundException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class EpisodateS3Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(EpisodateS3Service.class);

    @Autowired
    private AmazonS3 amazonS3;

    public Bucket createBucket(String bucketName) {
        if (amazonS3.doesBucketExistV2(bucketName)) {
            return amazonS3.listBuckets().stream().filter(bucket -> bucket.getName().equals(bucketName)).findFirst().orElse(null);
        }
        return amazonS3.createBucket(bucketName);
    }

    public StreamingResponseBody getFileFromBucket(String bucketName, String fileName) {
        if (amazonS3.doesBucketExistV2(bucketName)) {
            S3Object requiredFileObject = amazonS3.getObject(bucketName, fileName);

            return outputStream -> {
                int numberOfBytesToWrite;
                byte[] data = new byte[1024];
                InputStream inputStreamContent = requiredFileObject.getObjectContent();
                while ((numberOfBytesToWrite = inputStreamContent.read(data, 0, data.length)) != -1) {
                    LOGGER.debug("Writing bytes into output stream");
                    outputStream.write(data, 0, numberOfBytesToWrite);
                }

                inputStreamContent.close();
            };
        }
        throw new S3BucketNotFoundException(String.format("Bucket %s is not found", bucketName));
    }

    public PutObjectResult putFileToBucket(String bucketName, String fileName, String content) {
        Bucket bucket = createBucket(bucketName);
        LOGGER.debug("Bucket with name {}/{} will be used", bucket.getName(), bucket.getCreationDate());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length());
        return amazonS3.putObject(new PutObjectRequest(bucketName, fileName, IOUtils.toInputStream(content, StandardCharsets.UTF_8), metadata));
    }
}
