package com.danoff.app.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.danoff.app.config.AppConfig;
import com.danoff.app.config.kafka.reflect.AvroSerializable;

@Service
public class ReflectAvroProducer extends GenericKafkaProducer<AvroSerializable, AvroSerializable> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectAvroProducer.class);
	
	private final KafkaTemplate<AvroSerializable, AvroSerializable> kafkaTemplate;

	@Autowired
	public ReflectAvroProducer(KafkaTemplate<AvroSerializable, AvroSerializable> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	protected KafkaTemplate<AvroSerializable, AvroSerializable> getKafkaTemplate() {
		return kafkaTemplate;
	}
}
