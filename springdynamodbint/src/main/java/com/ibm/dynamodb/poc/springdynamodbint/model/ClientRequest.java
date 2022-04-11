/**
 * 
 */
package com.ibm.dynamodb.poc.springdynamodbint.model;

import lombok.Data;

/**
 * @author 02029H744
 *
 */
@Data
public class ClientRequest {
	
	private String productType;
	private String requestedDomain;
	private String parentRequestId;
	private String isInterDmRequest;
	private boolean interDmRequest;
	private String applicationId;
	private String edmRequestId;
	private String requestStatus;
	

}
