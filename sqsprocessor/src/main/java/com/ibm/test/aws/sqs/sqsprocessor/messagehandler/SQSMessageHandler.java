/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.messagehandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.Message;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 02029H744
 *
 */
@Service
@Slf4j
public class SQSMessageHandler {
	
	public SQSConsumedMessage handleConsumedMessage(Message queueMsg) {
		
		SQSConsumedMessage sqsConsumedMessage = new SQSConsumedMessage();
		Map<String,String> msgAttrMap= new HashMap<String,String>();
		sqsConsumedMessage.setMessageBody(queueMsg.getBody());
		sqsConsumedMessage.setMessageAttributeMap(queueMsg.getAttributes());
		queueMsg.getMessageAttributes().forEach((key,val) -> msgAttrMap.put(key, val.getStringValue()));
		//log.info("SQSConsumedMessage : "+queueMsg);
		sqsConsumedMessage.setMessageAttributeMap(msgAttrMap);
		log.info("SQSConsumedMessage : "+sqsConsumedMessage);
		return sqsConsumedMessage;
		
	}
}
