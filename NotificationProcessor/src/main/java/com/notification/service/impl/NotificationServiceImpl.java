package com.notification.service.impl;

import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.configuration.KafkaConfig;
import com.notification.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	KafkaConfig kafkaConfig;

	@Autowired
	RedisService redisService;

	private static final String EMAIL_TOPIC = "emailNotification";

	private static final String SMS_TOPIC = "smsNotification";

	private static final String DQ_TOPIC = "dqNotification";

	@Override
	public void processNotification(String payload) {
		JSONObject payloadJsonObject = null;
		try {
			payloadJsonObject = new JSONObject(payload);
		} catch (JSONException err) {
			System.out.println(err);
		}
		payloadJsonObject = redisService.evaluateAndUpdate(payloadJsonObject);

		String notificationType = payloadJsonObject.getString("route");
		notificationType = notificationType != null ? notificationType.trim() : "email";

		if (notificationType.equalsIgnoreCase("email")) {
			Future<RecordMetadata> emailFuture = kafkaConfig.emailSend(EMAIL_TOPIC, "", payloadJsonObject.toString());
			if (emailFuture.isCancelled()) {
				kafkaConfig.deadLetterQueue(DQ_TOPIC, "", payloadJsonObject.toString());
			}
		} else if (notificationType.equalsIgnoreCase("sms")) {
			Future<RecordMetadata> smsFuture = kafkaConfig.smsSend(SMS_TOPIC, "", payloadJsonObject.toString());
		}
	}
}