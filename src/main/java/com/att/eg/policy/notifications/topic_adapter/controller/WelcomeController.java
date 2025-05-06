package com.att.eg.policy.notifications.topic_adapter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.att.eg.policy.notifications.topic_adapter.camel.component.PolicyJmsComponent;

@RestController
public class WelcomeController {
	
	@Autowired
	private PolicyJmsComponent jmsComponent;

	@Value("${info.build.version}")
	private String projectVersion;

	@RequestMapping("/")
	public String index() {
		return "Policy Service Notification - Topic Adapter v" + projectVersion;
	}
	
	@RequestMapping(value = "/policy/notification" , method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> sendNotification(@RequestBody String message) {
		
		try {
			jmsComponent.publishMessage(message);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
		
		return new ResponseEntity<String>("published", HttpStatus.OK);
	}

}
