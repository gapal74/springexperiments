package com.ibm.dynamodb.poc.springdynamodbint.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBKeyed;
import com.amazonaws.services.dynamodbv2.model.KeyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@DynamoDBDocument
public class EdmDbRequestPrimaryId {
		
	@DynamoDBHashKey(attributeName = "edm_request_id")
	private String edmRequestId;
	@DynamoDBKeyed(KeyType.RANGE)
	@DynamoDBAttribute(attributeName = "parent_requested_domain")
	private String requestedDomain;	
	
}
