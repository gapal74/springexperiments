package com.ibm.test.aws.sqs.sqsprocessor.messagehandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SqsMessage {
	
	private String requestId;
	private String requestedDomain;
	private String requestedDomainResourceId;
	

}
