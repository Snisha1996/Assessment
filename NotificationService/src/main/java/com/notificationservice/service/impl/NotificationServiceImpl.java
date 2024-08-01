package com.notificationservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.notificationservice.producer.KafkaProducerConfig;
import com.notificationservice.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Override
	public ResponseEntity<Object> accountProcessor(JsonNode jsonNode) {
		if (jsonNode != null) {
			try {
				KafkaProducerConfig kafkaProducerConfig = new KafkaProducerConfig();
				kafkaProducerConfig.sendMessage("accountTopic", "", // Blank key
						jsonNode.toString());
			} catch (Exception e) {
				return ResponseEntity.ok("failed");
			}
		}
		logger.info("successfully sent on topic");
		return ResponseEntity.ok("success");
	}
}
