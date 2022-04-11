/**
 * 
 */
package com.ibm.dynamodb.poc.springdynamodbint.repository;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import com.ibm.dynamodb.poc.springdynamodbint.model.EdmDbRequest;
import com.ibm.dynamodb.poc.springdynamodbint.model.EdmDbRequestPrimaryId;

/**
 * @author 02029H744
 *
 */
@EnableScan
public interface EdmRequestRepository extends DynamoDBCrudRepository<EdmDbRequest, EdmDbRequestPrimaryId> {
	 
	Optional<EdmDbRequest> findByEdmRequestId(String edmRequestId);
	Optional<EdmDbRequest> findByRequestPrimaryId(EdmDbRequestPrimaryId requestPrimaryId);
	
}
