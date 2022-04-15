/**
 * 
 */
package com.ibm.spring.kinesis.springkinesisint.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @author 02029H744
 *
 */
@Data
public class ClientRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productType;
	private String requestedDomain;
	private String parentRequestId;
	private String isInterDmRequest;
	private boolean interDmRequest;
	private String applicationId;
	private String edmRequestId;
	private String requestStatus;
	

}
