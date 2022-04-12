/**
 * 
 */
package com.ibm.dynamodb.poc.springdynamodbint.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.ibm.dynamodb.poc.springdynamodbint.model.ClientRequest;
import com.ibm.dynamodb.poc.springdynamodbint.model.EdmDbRequest;
import com.ibm.dynamodb.poc.springdynamodbint.model.EdmDbRequestPrimaryId;
import com.ibm.dynamodb.poc.springdynamodbint.repository.EdmRequestRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author 02029H744
 *
 */
@Service
@RequiredArgsConstructor
public class AwsDynamoDbService {
	
	@Autowired
	private EdmRequestRepository dynamoDbRequestRepository;

	public EdmDbRequest saveRequest(ClientRequest requestDetail) {
		EdmDbRequest member = new EdmDbRequest();
		//EdmDbRequest member = null;
		requestDetail.setInterDmRequest(requestDetail.getIsInterDmRequest().trim().equalsIgnoreCase("Y")? Boolean.TRUE :Boolean.FALSE);
		if (requestDetail.getEdmRequestId()==null || requestDetail.getEdmRequestId().isEmpty()) {
			requestDetail.setEdmRequestId(UUID.randomUUID().toString());
		}
		//EdmDbRequestPrimaryId dbRequestPrimaryId = new EdmDbRequestPrimaryId(requestDetail.getEdmRequestId(), requestDetail.getRequestedDomain());
		//member.setRequestPrimaryId(dbRequestPrimaryId);
		BeanUtils.copyProperties(requestDetail, member);
		//member = new EdmDbRequest(new EdmDbRequest(dbRequestPrimaryId,requestDetail.));
		
       // System.out.println("Db requestId : " + member.getRequestPrimaryId().getEdmRequestId());
        System.out.println("Service Status : " + Region.getRegion(Regions.AP_SOUTH_1).isServiceSupported(AmazonDynamoDB.ENDPOINT_PREFIX));
        try {
			member = dynamoDbRequestRepository.save(member);
		}catch (AmazonServiceException ase) {
			System.out.println(ase.getRawResponseContent());
		}catch (Exception e) {
			e.printStackTrace();
		}
        return member;		
	}
	
	
  public Optional<EdmDbRequest> getItemById(String id,String requestedDomain) {
  
	  Optional<EdmDbRequest> member = Optional.empty(); 
	  EdmDbRequestPrimaryId edmDbRequestPrimaryId = new EdmDbRequestPrimaryId(id, requestedDomain);
	  try { 
		  System.out.println("id :" + id);
		  member  = dynamoDbRequestRepository.findByRequestPrimaryId(edmDbRequestPrimaryId); 		
		  System.out.println(member);
	  } catch (Exception e) {
		  e.printStackTrace(); 
	  }
	  
	  return member; 
  }

  public Optional<EdmDbRequest> getItemByRequestId(String id) {
	  
	  Optional<EdmDbRequest> memberList = null; 
	  EdmDbRequestPrimaryId edmDbRequestPrimaryId = new EdmDbRequestPrimaryId();
	  try { 
		  System.out.println("id :" + id);
		  edmDbRequestPrimaryId.setEdmRequestId(id);
		  
		  memberList  = dynamoDbRequestRepository.findByEdmRequestId(id); 
		  System.out.println(memberList);
	  } catch (Exception e) {
		  e.printStackTrace(); 
	  }
	  
	  return memberList; 
  }
	
}
