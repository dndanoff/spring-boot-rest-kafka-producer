package com.danoff.app.web.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danoff.app.config.AppConfig;
import com.danoff.app.config.kafka.reflect.AvroSerializable;
import com.danoff.app.config.kafka.reflect.AvroSerializableWrapper;
import com.danoff.app.dto.FlightDto;
import com.danoff.app.dto.Route;
import com.danoff.app.event.DtoReceived;
import com.danoff.app.service.kafka.GenericKafkaProducer;
import com.danoff.app.service.kafka.KafkaPublishingException;

@RestController
@RequestMapping("/payloads")
public class RestKafkaProducerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestKafkaProducerController.class);
	
	private final AppConfig appConfig;
	private final ApplicationEventPublisher publisher;
	private final GenericKafkaProducer<AvroSerializable, AvroSerializable> reflectAvroProducer;
	
	@Autowired
	public RestKafkaProducerController(AppConfig appConfig, GenericKafkaProducer<AvroSerializable, AvroSerializable> reflectAvroProducer, ApplicationEventPublisher publisher) {
		super();
		this.reflectAvroProducer = reflectAvroProducer;
		this.publisher = publisher;
		this.appConfig = appConfig;
	}

	@RequestMapping(value="/flights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void publishFlightMessage(@RequestBody @Valid FlightDto flight) throws KafkaPublishingException {
		MDC.clear();
		MDC.put("UUID", UUID.randomUUID().toString());
		LOGGER.info("Handling request to publishFlightMessage.");
		LOGGER.debug("Handling request to publishFlightMessage with payload={}", flight);
		publisher.publishEvent(new DtoReceived<>(flight));
	}
	
	@RequestMapping(value="/routes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void publishRouteMessage(@RequestBody @Valid Route route) throws KafkaPublishingException {
		MDC.clear();
		MDC.put("UUID", UUID.randomUUID().toString());
		LOGGER.info("Handling request to publishRouteMessage.");
		LOGGER.debug("Handling request to publishRouteMessage with payload={}", route);
		reflectAvroProducer.publishMessage(appConfig.getKafkaRouteTopic(), new AvroSerializableWrapper<>(route.getId()), route);
	}
}
