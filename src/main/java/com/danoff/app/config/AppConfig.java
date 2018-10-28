package com.danoff.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {
	
	private final Environment env;
	
	@Autowired
	public AppConfig(Environment env) {
		this.env = env;
	}
	
	public boolean getKafkaSslEnabled() {
		return Boolean.valueOf(env.getProperty("kafka.ssl.enabled"));
	}
	
	public String getKafkaKeyStoreLocation() {
		return env.getProperty("kafka.keystore_location");
	}
	
	public String getKafkaKeyStorePassword() {
		return env.getProperty("kafka.keystore_password");
	}
	
	public String getKafkaTrustStoreLocation() {
		return env.getProperty("kafka.truststore_location");
	}
	
	public String getKafkaTrustStorePassword() {
		return env.getProperty("kafka.truststore_password");
	}
	
	public String getKafkaBootstrapServers() {
		return env.getProperty("spring.kafka.bootstrap-servers");
	}
	
	public String getKafkaSchemaRegistry() {
		return env.getProperty("kafka.scheam-registry");
	}
	
	public String getKafkaProducerClientId() {
		return env.getProperty("spring.kafka.producer.client-id");
	}
	
	public String getKafkaFlightTopic() {
		return env.getProperty("kafka.topics.flight");
	}
	
	public String getKafkaRouteTopic() {
		return env.getProperty("kafka.topics.route");
	}
	
}
