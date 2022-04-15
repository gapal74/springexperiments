package com.ibm.spring.kinesis.springkinesisint.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ibm.spring.kinesis.springkinesisint.model.ClientRequest;

import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.GetRecordsRequest;
import software.amazon.awssdk.services.kinesis.model.GetRecordsResponse;
import software.amazon.awssdk.services.kinesis.model.GetShardIteratorRequest;
import software.amazon.awssdk.services.kinesis.model.GetShardIteratorResponse;
import software.amazon.awssdk.services.kinesis.model.ListShardsRequest;
import software.amazon.awssdk.services.kinesis.model.ListShardsResponse;
import software.amazon.awssdk.services.kinesis.model.Shard;
import software.amazon.awssdk.services.kinesis.model.ShardIteratorType;

@Service
public class DataStreamConsumerService {

	private static final Logger logger = LoggerFactory.getLogger(DataStreamProducerService.class.getName());

	@Autowired
	KinesisClient kinesisClient;

	@Value("${aws.stream_name}")
	private String dataStreamName;

	public List<ClientRequest> getMessage() {

		ListShardsRequest request = ListShardsRequest.builder().streamName(dataStreamName).build();
		ListShardsResponse response = kinesisClient.listShards(request);
		List<Shard> shradList = response.shards();
		List<ClientRequest> clientRequestList = new ArrayList<ClientRequest>();
		for (Iterator<Shard> iterator = shradList.iterator(); iterator.hasNext();) {
			
			Shard shard = (Shard) iterator.next();
			// Prepare the shard iterator request with the stream name
			// and identifier of the shard to which the record was written
			GetShardIteratorRequest getShardIteratorRequest = GetShardIteratorRequest.builder()
					.streamName(dataStreamName).shardId(shard.shardId())
					.shardIteratorType(ShardIteratorType.TRIM_HORIZON.name()).build();
			GetShardIteratorResponse getShardIteratorResponse = kinesisClient.getShardIterator(getShardIteratorRequest);
			// Get the shard iterator from the Shard Iterator Response
			String shardIterator = getShardIteratorResponse.shardIterator();
			// Prepare the get records request with the shardIterator
			GetRecordsRequest getRecordsRequest = GetRecordsRequest.builder().shardIterator(shardIterator).limit(5).build();
			// Read the records from the shard
			GetRecordsResponse getRecordsResponse = kinesisClient.getRecords(getRecordsRequest);
			List<software.amazon.awssdk.services.kinesis.model.Record> records = getRecordsResponse.records();
			logger.info("count " + records.size());
			// log content of each record
			records.forEach(record -> {
				byte[] dataInBytes = record.data().asByteArray();
	            // Create a byte array input stream
	            ByteArrayInputStream bis = new ByteArrayInputStream(dataInBytes); 
	            // Use byte array input stream to create an object input stream
	            ObjectInputStream ois;
				try {
					ois = new ObjectInputStream(bis);
		            ClientRequest clientRequest = (ClientRequest) ois.readObject();	
		            clientRequestList.add(clientRequest);
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

		}

		kinesisClient.close();

		return clientRequestList;
	}
}
