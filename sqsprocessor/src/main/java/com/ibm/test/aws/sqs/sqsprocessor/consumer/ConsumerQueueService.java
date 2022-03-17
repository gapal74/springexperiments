<<<<<<< HEAD
/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.consumer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

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
	public Map<String,String> receiveMessage(){
		
		Map<String,String> recvdMessageMap = new HashMap<String,String>();
		recvdMessageMap = queueMessagingTemplate.receiveAndConvert(endPoint,HashMap.class);
		return recvdMessageMap;
	}
}
=======
/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.consumer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

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
	public Map<String,String> receiveMessage(){
		
		Map<String,String> recvdMessageMap = new HashMap<String,String>();
		recvdMessageMap = queueMessagingTemplate.receiveAndConvert(endPoint,HashMap.class);
		return recvdMessageMap;
	}
}
>>>>>>> branch 'master' of https://github.com/gapal74/springexperiments.git
