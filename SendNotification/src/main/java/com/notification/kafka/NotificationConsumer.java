package com.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
 	private static final String TOPIC = "emailNotification";

	@KafkaListener(topics = TOPIC, groupId = "email-consumer-group")
	public void consumer(String message) {
		System.out.println("message - " + message);
	}

}