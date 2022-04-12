package com.ibm.awss3.poc.springS3int.contoller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.awss3.poc.springS3int.service.AwsS3FileService;

@RestController
@RequestMapping("/api/edm/v1/files")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EdmS3MgmtController {

    private static final String MESSAGE_1 = "Uploaded the file successfully";

    @Autowired
    AwsS3FileService fileService;

    @GetMapping(path = "findall")
    public ResponseEntity<List<String>> findAllDocuments() {
        return new ResponseEntity<>(fileService.listFiles(), HttpStatus.OK);                
    }
    
    @GetMapping(path ="findfile/{filename}")
    public ResponseEntity<Object> downloadFileByName(@PathVariable("filename") String fileNm) {
    	
    	ByteArrayOutputStream downloadInputStream = fileService.findByName("files/"+fileNm);
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.noCache())
                .contentType(contentType("files/"+fileNm))
                .header("Content-disposition", "attachment; filename=\"" + fileNm + "\"")
                .body(downloadInputStream.toByteArray());

    } 
    
    @PostMapping(path = "upload")
    public ResponseEntity<Object> upLoadFile(@RequestParam("file") MultipartFile multipartFile) {
        fileService.uploadFileToS3(multipartFile);
        return new ResponseEntity<>(MESSAGE_1, HttpStatus.OK);
    }
    
    @GetMapping(value = "/delete/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable("filename") String filename) {
        return new ResponseEntity<>(fileService.deleteFile(filename), HttpStatus.OK);
    } 
    
    private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }   
}
