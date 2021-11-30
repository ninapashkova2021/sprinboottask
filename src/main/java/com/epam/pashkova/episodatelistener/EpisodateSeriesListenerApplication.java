package com.epam.pashkova.episodatelistener;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

@EnableAspectJAutoProxy
@SpringBootApplication
public class EpisodateSeriesListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpisodateSeriesListenerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public AWSCredentialsProvider awsCredentialsProvider(){
		return new DefaultAWSCredentialsProviderChain();
	}

	@Bean
	public AmazonS3 amazonS3(){
		AmazonS3 s3client = AmazonS3ClientBuilder
				.standard()
				.withCredentials(awsCredentialsProvider())
				.withRegion(Regions.US_EAST_2)
				.build();
		return s3client;
	}
}
