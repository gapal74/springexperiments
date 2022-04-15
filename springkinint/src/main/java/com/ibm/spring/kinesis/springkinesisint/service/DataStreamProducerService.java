package com.ibm.spring.kinesis.springkinesisint.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.ibm.spring.kinesis.springkinesisint.model.ClientRequest;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.awssdk.services.kinesis.model.PutRecordResponse;

@Service
public class DataStreamProducerService {

	private static final Logger logger = LoggerFactory.getLogger(DataStreamProducerService.class.getName());
	
	@Autowired
	KinesisClient kinesisClient;

	@Value("${aws.stream_name}")
	private String dataStreamName;

	public String putMessage(ClientRequest clientRequest) {
		
		String putStatus = "";
		String partitionKey = "partitionkey1";	
		// Create the data to be sent to Kinesis Data Stream in bytes
		byte[] clientRequestData = SerializationUtils.serialize(clientRequest);
		logger.info("size :"+clientRequestData.length);
		//SdkBytes streamRequestData = SdkBytes.fromByteBuffer(ByteBuffer.wrap(clientRequestData));
		SdkBytes streamRequestData = SdkBytes.fromByteArray(clientRequestData);	
		// Create the request for putRecord method
		PutRecordRequest putRecordRequest = PutRecordRequest.builder().streamName(dataStreamName)
				.partitionKey(partitionKey).data(streamRequestData).build();
		// Call the method to write the record to Kinesis Data Stream
		PutRecordResponse putRecordsResult = kinesisClient.putRecord(putRecordRequest);
		logger.info("Put Result : " + putRecordsResult.toString());	
		kinesisClient.close();
		// setting response value 
		putStatus = putRecordsResult.sequenceNumber();
		
		return putStatus;
	}

}
