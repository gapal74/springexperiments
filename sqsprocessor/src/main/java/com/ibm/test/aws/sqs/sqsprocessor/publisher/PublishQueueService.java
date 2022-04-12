
/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.publisher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
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
			MessageAttributeValue traceIdValue = new MessageAttributeValue();
			MessageAttributeValue operationTypeValue = new MessageAttributeValue();
			Map<String, String> attributeMap= new HashMap<String,String>();
			Message msg = new Message();
			traceIdValue.setDataType("String");
			traceIdValue.setStringValue(UUID.randomUUID().toString());
			
			// generates a UUID as the traceId
			msg.addMessageAttributesEntry("TRACE_ID", traceIdValue);
			msg.addMessageAttributesEntry("OPERATION_TYPE", operationTypeValue.withDataType("String").withStringValue("DELIVER"));
			//attributeMap.put("SOURCE_DOMAIN", "ORCH");
			//attributeMap.put("DESTINATION_DOMAIN", "TASK");
			msg.setBody(jsonString);
			//msg.setAttributes(attributeMap);
			msg.addAttributesEntry("SOURCE_DOMAIN", "ORCH");
			msg.addAttributesEntry("DESTINATION_DOMAIN", "TASK");
			queueMessagingTemplate.convertAndSend(endPoint, msg);
			// logger.info("Message sent successfully " + jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageMap;
	}
}
