
/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.ibm.test.aws.sqs.sqsprocessor.config.SqsCloudConfig;
import com.ibm.test.aws.sqs.sqsprocessor.messagehandler.SQSConsumedMessage;
import com.ibm.test.aws.sqs.sqsprocessor.messagehandler.SQSMessageHandler;

import io.awspring.cloud.messaging.core.QueueMessageChannel;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
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

	@Autowired
	private SQSMessageHandler sqsMessageHandler;

 
	@Value("${cloud.aws.end-point.uri}")
	private String endPoint;
	
	public List<SQSConsumedMessage>  receiveMessage(){
		
		List<SQSConsumedMessage> sqsConsumedMessages = new ArrayList<SQSConsumedMessage>();
		SQSConsumedMessage consumedMessage = null;     
		Message m = queueMessagingTemplate.receiveAndConvert(endPoint,Message.class);
		consumedMessage=sqsMessageHandler.handleConsumedMessage(m);
		System.out.println(m);
		System.out.println(consumedMessage);
		sqsConsumedMessages.add(consumedMessage);
	    return sqsConsumedMessages;
	}
}
