/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.publisher;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.RequiredArgsConstructor;

/**
 * @author 02029H744
 *
 */
@Service
@RequiredArgsConstructor
public class PublishQueueService {

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${cloud.aws.end-point.uri}")
	private String endPoint;

	public Map<String, String> publishMessageInQueue(Map<String, String> messageMap) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(messageMap);
			System.out.println("In building Service"+jsonString);
			queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(jsonString).build());
			// logger.info("Message sent successfully " + jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageMap;
	}
}
