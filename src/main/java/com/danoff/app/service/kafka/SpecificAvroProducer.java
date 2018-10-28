package com.danoff.app.service.kafka;

import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.danoff.app.config.AppConfig;

@Service
public class SpecificAvroProducer extends GenericKafkaProducer<SpecificRecord, SpecificRecord> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpecificAvroProducer.class);
	
	private final KafkaTemplate<SpecificRecord, SpecificRecord> kafkaTemplate;

	@Autowired
	public SpecificAvroProducer(KafkaTemplate<SpecificRecord, SpecificRecord> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	protected KafkaTemplate<SpecificRecord, SpecificRecord> getKafkaTemplate() {
		return kafkaTemplate;
	}
}
