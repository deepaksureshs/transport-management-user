package com.company.transportmanagementuser.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.transportmanagementuser.model.Request;
import com.company.transportmanagementuser.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserService userService;

	@GetMapping("/route")
	public ResponseEntity getVehicles(@RequestBody String payload) {

		LOGGER.info("Vehicles request received. payload :: " + payload);
		Request request = new Request();
		String responsePayload = "";
		Map<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("status", "ERROR");
		try {
			request = objectMapper.readValue(payload, Request.class);
			responsePayload = objectMapper.writeValueAsString(userService.getVehicleList(request));
		} catch (JsonMappingException exception) {
			errorResponse.put("error-message", "payload mapping error occured" + exception);
			LOGGER.error("payload mapping exception occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);

		} catch (JsonProcessingException exception) {
			errorResponse.put("error-message", "payload processing error occured" + exception);
			LOGGER.error("payload processing error occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		} catch (Exception exception) {
			errorResponse.put("error-message", exception.getMessage());
			LOGGER.error(exception.getMessage());
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responsePayload);

	}

	@GetMapping("/route/seat")
	public ResponseEntity checkSeat(@RequestBody String payload) {
		LOGGER.info("Vehicles request received. payload :: " + payload);
		Request request = new Request();
		boolean responsePayload = false;
		Map<String, String> errorResponse = new HashMap<String, String>();
		errorResponse.put("status", "ERROR");
		try {
			request = objectMapper.readValue(payload, Request.class);
			responsePayload = userService.checkSeat(request);
		} catch (JsonMappingException exception) {
			errorResponse.put("error-message", "payload mapping error occured" + exception);
			LOGGER.error("payload mapping exception occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		} catch (JsonProcessingException exception) {
			errorResponse.put("error-message", "payload processing error occured" + exception);
			LOGGER.error("payload processing error occured" + exception);
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		} catch (Exception exception) {
			errorResponse.put("error-message", exception.getMessage());
			LOGGER.error(exception.getMessage());
			return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
		}

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responsePayload);

	}
}
