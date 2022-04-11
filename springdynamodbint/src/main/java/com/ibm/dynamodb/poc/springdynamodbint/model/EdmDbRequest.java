package com.ibm.dynamodb.poc.springdynamodbint.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBKeyed;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.KeyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Builder
@Data
@DynamoDBTable(tableName = "EDMRequestDataStore")
@NoArgsConstructor
public class EdmDbRequest {
	
	@Id
	//@DynamoDBIgnore
	@DynamoDBAttribute(attributeName = "unique_request")
	private EdmDbRequestPrimaryId requestPrimaryId;	
	/*
	@DynamoDBHashKey(attributeName = "edm_request_id")
	private String edmRequestId;
	@DynamoDBKeyed(KeyType.RANGE)  
	@DynamoDBAttribute(attributeName = "parent_requested_domain") 	
	private String requestedDomain;
	*/
	@DynamoDBAttribute(attributeName = "parent_request_id")
	private String parentRequestId;
	@DynamoDBAttribute(attributeName = "application_id")
	private String applicationId;
	@DynamoDBAttribute(attributeName = "inter_domain_request_indicator")
	private boolean interDmRequest;
	@DynamoDBAttribute(attributeName = "request_status")
	private String requestStatus;
	@DynamoDBAttribute(attributeName = "product_type")
	private String productType;

	@DynamoDBHashKey(attributeName = "edm_request_id") 
	public String getEdmRequestId() {
		return requestPrimaryId!=null? requestPrimaryId.getEdmRequestId():null;
	}
	public void setEdmRequestId(String edmRequestId) {
		if(requestPrimaryId == null) {
			requestPrimaryId = new EdmDbRequestPrimaryId();
		}
		requestPrimaryId.setEdmRequestId(edmRequestId);
		//this.edmRequestId = edmRequestId;
	}

	@DynamoDBKeyed(KeyType.RANGE)  
	@DynamoDBAttribute(attributeName = "parent_requested_domain") 	
	public String getRequestedDomain() {
		return requestPrimaryId!=null? requestPrimaryId.getRequestedDomain():null;
	}
	public void setRequestedDomain(String requestedDomain) {
		if(requestPrimaryId == null) {
			requestPrimaryId = new EdmDbRequestPrimaryId();
		}
		requestPrimaryId.setRequestedDomain(requestedDomain);		
		//this.requestedDomain = requestedDomain;
	}

	
}
