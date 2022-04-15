package com.ibm.spring.kinesis.springkinesisint.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.spring.kinesis.springkinesisint.model.ClientRequest;
import com.ibm.spring.kinesis.springkinesisint.service.DataStreamConsumerService;
import com.ibm.spring.kinesis.springkinesisint.service.DataStreamProducerService;



@RestController
@RequestMapping(value = "/api/v1/edmdatastream")
public class EdmDataStreamController {

	@Autowired
	private DataStreamProducerService producerService;
	@Autowired
	private DataStreamConsumerService consumerService;
	
	@PostMapping(path = "putdatainstream", 
	        consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> putInDataStream(@RequestBody ClientRequest newRequest) throws Exception {
		System.out.println("Request Id :" + newRequest.getApplicationId());
		String requestDetail = producerService.putMessage(newRequest);
	    return new ResponseEntity<>(requestDetail, HttpStatus.CREATED);
	}

	@GetMapping(path = "getdatainstream", 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClientRequest>> getFromDataStream() throws Exception {
		//System.out.println("Request Id :" + newRequest.getApplicationId());
		List<ClientRequest> requestDetailList = consumerService.getMessage();
	    return new ResponseEntity<>(requestDetailList, HttpStatus.CREATED);
	}
	
}
