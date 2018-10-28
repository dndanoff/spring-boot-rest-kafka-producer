package com.danoff.app.service.listener;

import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.danoff.app.config.AppConfig;
import com.danoff.app.dto.FlightDto;
import com.danoff.app.event.DtoReceived;
import com.danoff.app.service.kafka.GenericKafkaProducer;
import com.danoff.app.service.kafka.KafkaPublishingException;
import com.danoff.app.service.transform.FlightDtoMapper;

@Service
public class FlightsDtoKafkaProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlightsDtoKafkaProducer.class);

	private final AppConfig appConfig;
	private final GenericKafkaProducer<String, SpecificRecord> mixedProducer;
	private final FlightDtoMapper flightDtoMapper;

	@Autowired
	public FlightsDtoKafkaProducer(GenericKafkaProducer<String, SpecificRecord> mixedProducer,
			FlightDtoMapper flightDtoMapper, AppConfig appConfig) {
		this.mixedProducer = mixedProducer;
		this.flightDtoMapper = flightDtoMapper;
		this.appConfig = appConfig;
	}

	@EventListener
	public void handleEvent(DtoReceived<FlightDto> event) throws KafkaPublishingException {
		LOGGER.info("FlightDto received event handler triggered.");
		FlightDto flight = (FlightDto)event.getDto();
		publishMessage(flight.toString(), flight);
	}
	
	public void publishMessage(String key, FlightDto value) throws KafkaPublishingException {
		mixedProducer.publishMessage(appConfig.getKafkaFlightTopic(), key, flightDtoMapper.dtoToAvroSpecificRecord(value));
	}

}
