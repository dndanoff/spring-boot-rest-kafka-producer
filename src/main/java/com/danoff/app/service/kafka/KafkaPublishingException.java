package com.danoff.app.service.kafka;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class KafkaPublishingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -412025850141827903L;

	public KafkaPublishingException(String message) {
		super(message);
	}
	
	public KafkaPublishingException(Exception e) {
		super(e);
	}

	public KafkaPublishingException(String message, Exception e) {
		super(message,e);
	}
}
