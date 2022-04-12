/**
 * 
 */
package com.ibm.dynamodb.poc.springdynamodbint.contoller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.dynamodb.poc.springdynamodbint.model.ClientRequest;
import com.ibm.dynamodb.poc.springdynamodbint.model.EdmDbRequest;
import com.ibm.dynamodb.poc.springdynamodbint.service.AwsDynamoDbService;

import lombok.RequiredArgsConstructor;

/**
 * @author 02029H744
 *
 */
@RestController
@RequestMapping(value = "/api/springdbint")
@RequiredArgsConstructor
public class SpringDynamoDbIntController {
	
	private final AwsDynamoDbService awsDynamoDbService;
	
	@PostMapping(path = "requestdtl", 
	        consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EdmDbRequest> create(@RequestBody ClientRequest newRequest) throws Exception {
		System.out.println("Request Id :" + newRequest.getApplicationId());
		EdmDbRequest requestDetail = awsDynamoDbService.saveRequest(newRequest);
	    return new ResponseEntity<>(requestDetail, HttpStatus.CREATED);
	}
	
    @GetMapping("getrequestdtl/{id}/{domain}")
    public ResponseEntity<EdmDbRequest> getById(@PathVariable("id") String id,@PathVariable("domain") String domain) throws Exception {
        Optional<EdmDbRequest> edmRequest = awsDynamoDbService.getItemById(id,domain);
        if (edmRequest.isPresent()) {
            return new ResponseEntity<>(edmRequest.get(), HttpStatus.OK);
        } else {
            throw new Exception();
        }
    }	
    @GetMapping("getrequestdtl/{id}")
    public ResponseEntity<EdmDbRequest> getByRequest(@PathVariable("id") String id) throws Exception {
    	Optional<EdmDbRequest> edmRequests = awsDynamoDbService.getItemByRequestId(id);
        if (edmRequests!=null && !edmRequests.isEmpty()) {
            return new ResponseEntity<>(edmRequests.get(), HttpStatus.OK);
        } else {
            throw new Exception();
        }
    }	
}
