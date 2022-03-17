package com.ibm.test.aws.sqs.sqsprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

<<<<<<< HEAD
@SpringBootApplication
=======
@SpringBootApplication(        
		exclude = {
                org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
                org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
                org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
        }
		)
>>>>>>> branch 'master' of https://github.com/gapal74/springexperiments.git
public class SqsprocessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqsprocessorApplication.class, args);
	}

}
