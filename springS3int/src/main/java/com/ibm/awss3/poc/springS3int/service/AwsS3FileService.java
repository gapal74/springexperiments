package com.ibm.awss3.poc.springS3int.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class AwsS3FileService {

    private static final Logger LOG = LoggerFactory.getLogger(AwsS3FileService.class);
    
   
	@Autowired
	private AmazonS3 awsS3FileClient;

    @Value("${amazon.aws.s3.bucket}")
    private String s3BucketName;
    
    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
    	
    	
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }	

    private void uploadFileTos3bucket(String fileName, File file) {
    	//AmazonS3 awsS3FileClient = (AmazonS3)context.getBean("AmazonS3");
    	awsS3FileClient.putObject(s3BucketName, fileName, file);
    }

    private String generateFileName(MultipartFile multiPart) {
    	
    	 return "files/"+LocalDateTime.now() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    @Async
    public void uploadFileToS3(final MultipartFile multipartFile) {
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            final String fileName = generateFileName(multipartFile);
            LOG.info("Uploading file with name {}", fileName);
            uploadFileTos3bucket(fileName, file);
        } catch (AmazonServiceException e) {
            LOG.error("Error {} occurred while uploading file", e.getLocalizedMessage());
        }
    }
    
    @Async    
    public Stream<String> getListOfFilesInS3() {
       	
    	return awsS3FileClient.listObjectsV2(s3BucketName).getObjectSummaries().stream().map(S3ObjectSummary::getKey);
    }
    
    @Async
    public ByteArrayOutputStream findByName(String fileName) {
        LOG.info("Downloading file with name {}", fileName);
        try {
            InputStream is = awsS3FileClient.getObject(s3BucketName,fileName).getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream;
        } catch (IOException ioException) {
        	LOG.error("IOException: " + ioException.getMessage());
        } catch (AmazonServiceException serviceException) {
        	LOG.info("AmazonServiceException Message:    " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
        	LOG.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }
        return null;             
    }
    
    @Async
    public String deleteFile(final String fileName) {
    	
    	awsS3FileClient.deleteObject(s3BucketName, fileName);
        return "Deleted File: " + fileName;
    }
    
    @Async
    public List<String> listFiles() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(s3BucketName);
        List<String> keys = new ArrayList<>();
        ObjectListing objects = awsS3FileClient.listObjects(listObjectsRequest);
        while (true) {
            List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();
            if (objectSummaries.size() < 1) {
                break;
            }
            for (S3ObjectSummary item : objectSummaries) {
                if (!item.getKey().endsWith("/"))
                    keys.add(item.getKey());
            }
            objects = awsS3FileClient.listNextBatchOfObjects(objects);
        }
        return keys;
    }    
}
