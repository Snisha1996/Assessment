package com.notificationservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public interface NotificationService {

	ResponseEntity<Object> accountProcessor(JsonNode accountRequest);
}
