
package com.ibm.test.aws.sqs.sqsprocessor.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.test.aws.sqs.sqsprocessor.consumer.ConsumerQueueService;
import com.ibm.test.aws.sqs.sqsprocessor.messagehandler.SqsMessage;
import com.ibm.test.aws.sqs.sqsprocessor.publisher.PublishQueueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/sqstest")
@RequiredArgsConstructor
public class SqsMessageController {
	
	
	private final PublishQueueService publishQueueService;
	private final ConsumerQueueService consumerQueueService;
	

	@PostMapping(path ="sendMessage", 
	        consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public void sendMessage(@RequestBody  SqsMessage sqsMessage) throws JsonProcessingException, IllegalArgumentException, IllegalAccessException {
		
		Map<String,String> messageMap = new HashMap<String,String>();
		Field[] fields = sqsMessage.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			messageMap.put(fields[i].getName(), (String)fields[i].get(sqsMessage));
		}
		System.out.println("Message Map :" + messageMap);	
		publishQueueService.publishMessageInQueue(messageMap);
	}

	
    @GetMapping("getmessage")
	public ResponseEntity<Message> getMessage(){
		
    	Message rercvdMsg = consumerQueueService.receiveMessage();
    	System.out.println("Message :" + rercvdMsg.getBody());
    	System.out.println("Message Attributes :" + rercvdMsg.getMessageAttributes());
    	System.out.println("Attributes :" + rercvdMsg.getAttributes());
		return new ResponseEntity<>(consumerQueueService.receiveMessage(), HttpStatus.OK);
		
	}
}
