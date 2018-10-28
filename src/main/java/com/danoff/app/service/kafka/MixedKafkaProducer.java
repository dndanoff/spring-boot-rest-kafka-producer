package com.danoff.app.service.kafka;

import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MixedKafkaProducer extends GenericKafkaProducer<String, SpecificRecord> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MixedKafkaProducer.class);

	private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

	@Autowired
	public MixedKafkaProducer(KafkaTemplate<String, SpecificRecord> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	protected KafkaTemplate<String, SpecificRecord> getKafkaTemplate() {
		return kafkaTemplate;
	}
}
