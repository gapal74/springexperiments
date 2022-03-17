
/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.consumer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.Message;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.RequiredArgsConstructor;

/**
 * @author 02029H744
 *
 */
@Service
@RequiredArgsConstructor
public class ConsumerQueueService {

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${cloud.aws.end-point.uri}")
	private String endPoint;
	
	//@SqsListener("${cloud.aws.end-point.uri}")
	public Message receiveMessage(){
		
		Message recivedMsg = queueMessagingTemplate.receiveAndConvert(endPoint,Message.class);
		return recivedMsg;
	}
}
