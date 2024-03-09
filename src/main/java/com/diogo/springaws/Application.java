package com.diogo.springaws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;

@SpringBootApplication(exclude = ContextInstanceDataAutoConfiguration.class)
public class Application 
{
	static final Logger LOG = LoggerFactory.getLogger(Application.class);

	private static ApplicationContext applicationContext;

	private final AmazonS3Client amazonS3Client;
	public Application(AmazonS3Client amazonS3Client) 
	{
		this.amazonS3Client = amazonS3Client;
	}

	public static void main(String[] args) 
	{
		SpringApplication.run(Application.class, args);
	}

	@EventListener(classes = ApplicationReadyEvent.class)
	public void onApplicationReadyEvent(ApplicationReadyEvent event) 
	{
		for (Bucket availableBuckets : amazonS3Client.listBuckets()) 
		{
			System.out.println(availableBuckets.getName());
		}
	}
}