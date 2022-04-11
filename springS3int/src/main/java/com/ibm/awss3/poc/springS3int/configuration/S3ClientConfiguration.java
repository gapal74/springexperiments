package com.ibm.awss3.poc.springS3int.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3ClientConfiguration {
	
    @Value("${amazon.aws.accesskey}")
    private String accessKeyId;

    @Value("${amazon.aws.secretkey}")
    private String accessKeySecret;

    @Value("${amazon.aws.s3.region}")
    private String s3RegionName;
    
    @Bean
    public AmazonS3 getAmazonS3Client() {
    	
    	final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret); 	
        // Get Amazon S3 client and return the S3 client object
    	System.out.println("Here In Bean:");
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(s3RegionName)
                .build();
    }   

}
