/**
 * 
 */
package com.ibm.test.aws.sqs.sqsprocessor.messagehandler;

import java.util.Map;

import lombok.Data;

/**
 * @author 02029H744
 *
 */
@Data
public class SQSConsumedMessage {
	
	private Map<String,String> messageAttributeMap;
	private Map<String,String> messageBodyAttributeMap;
	private String messageBody;
}
