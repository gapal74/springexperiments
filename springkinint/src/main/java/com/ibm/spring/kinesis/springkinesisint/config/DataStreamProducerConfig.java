/**
 * 
 */
package com.ibm.spring.kinesis.springkinesisint.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisClient;


/**
 * @author 02029H744
 *
 */
@Component
public class DataStreamProducerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DataStreamProducerConfig.class);

    @Value("${aws.stream_name}")
    private String streamName;
    
	@Value("${cloud.aws.region.static}")
	private String amazonAWSRegion;

	@Value("${cloud.aws.credentials.access-key}")
	private String amazonAWSAccessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String amazonAWSSecretKey;
	
	/*
	@Bean
    public KinesisProducer getKinesisProducer() {
		
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);

        KinesisProducerConfiguration config = new KinesisProducerConfiguration();
        config.setRegion(amazonAWSRegion);
        config.setCredentialsProvider(new AWSStaticCredentialsProvider(awsCreds));
        config.setMaxConnections(1);
        config.setRequestTimeout(6000);
        config.setRecordMaxBufferedTime(5000);

        return new KinesisProducer(config);
    }
	*/
	@Bean
	public KinesisClient getConsumerConfig() {
		
        AwsCredentials awsCreds = AwsBasicCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey);
        AwsCredentialsProvider awsCredential = StaticCredentialsProvider.create(awsCreds);
        Region region = Region.of(amazonAWSRegion);		
		
	    KinesisClient kinesisClient = KinesisClient
	            .builder()
	            .credentialsProvider(awsCredential)
	            .region(region).build();
	    return kinesisClient;
	}
	
}
