package com.notification.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.notification.service.NotificationService;

@Service
public class NotificationConsumer {

	private static final String TOPIC = "accountTopic";

	@Autowired
	private NotificationService notificationService;

	@KafkaListener(topics = TOPIC, groupId = "account-consumer-group")
	public void consumer(String message) {
		System.out.println("message - " + message);
		notificationService.processNotification(message);
	}
}