package com.danoff.app.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.danoff.app.config.AppConfig;
import com.danoff.app.config.kafka.reflect.AvroSerializable;
import com.danoff.app.config.kafka.reflect.ReflectKafkaAvroSerializer;

import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;

@Configuration
public class KafkaProducerConfig extends KafkaSslConfig{
	
	@Autowired
	public KafkaProducerConfig(AppConfig appConfig) {
		super(appConfig);
	}
	
	@Bean
    public ProducerFactory<AvroSerializable, AvroSerializable> avroReflectProducerFactory() {
        return createProducerFactory(ReflectKafkaAvroSerializer.class, ReflectKafkaAvroSerializer.class);
    }
 
    @Bean
    public KafkaTemplate<AvroSerializable, AvroSerializable> avroReflectKafkaTemplate(ProducerFactory<AvroSerializable, AvroSerializable> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
    
	@Bean
    public ProducerFactory<SpecificRecord, SpecificRecord> avroSpecificProducerFactory() {
        return createProducerFactory(ReflectKafkaAvroSerializer.class, ReflectKafkaAvroSerializer.class);
    }
 
    @Bean
    public KafkaTemplate<SpecificRecord, SpecificRecord> avroSpecificKafkaTemplate(ProducerFactory<SpecificRecord, SpecificRecord> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
    
	@Bean
    public ProducerFactory<String, SpecificRecord> mixedProducerFactory() {
        return createProducerFactory(StringSerializer.class, ReflectKafkaAvroSerializer.class);
    }
 
    @Bean
    public KafkaTemplate<String, SpecificRecord> mixedKafkaTemplate(ProducerFactory<String, SpecificRecord> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
    
	@Bean
    public ProducerFactory<String, String> stringProducerFactory() {
        return createSpecificProducerFactory(StringSerializer.class, StringSerializer.class);
    }
 
    @Bean
    public KafkaTemplate<String, String> stringKafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
    
	@Bean
    public ProducerFactory<Integer, Integer> integerProducerFactory() {
        return createSpecificProducerFactory(IntegerSerializer.class, IntegerSerializer.class);
    }
 
    @Bean
    public KafkaTemplate<Integer, Integer> integerKafkaTemplate(ProducerFactory<Integer, Integer> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
    
	@Bean
    public ProducerFactory<Long, Long> longProducerFactory() {
        return createSpecificProducerFactory(LongSerializer.class, LongSerializer.class);
    }
 
    @Bean
    public KafkaTemplate<Long, Long> longKafkaTemplate(ProducerFactory<Long, Long> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

	
	private <K, V> ProducerFactory<K, V> createSpecificProducerFactory(Class<? extends Serializer<K>> keySerializer, Class<? extends Serializer<V>> valueSerializer) {
        final Map<String, Object> props = new HashMap<>();
        fillCommonProperties(props);
        fillSchemaRegistryProperties(props);
        fillSslProperties(props);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        
        return new DefaultKafkaProducerFactory<>(props);
    }
	
	private <K, V> ProducerFactory<K, V> createProducerFactory(Class<? extends Serializer<?>> keySerializer, Class<? extends Serializer<?>> valueSerializer) {
        final Map<String, Object> props = new HashMap<>();
        fillCommonProperties(props);
        fillSchemaRegistryProperties(props);
        fillSslProperties(props);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        
        return new DefaultKafkaProducerFactory<>(props);
    }
	
	private Map<String, Object> fillCommonProperties(Map<String, Object> props){
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootstrapServers());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, appConfig.getKafkaProducerClientId());
		
        return props;
	}
	
	private Map<String, Object> fillSslProperties(Map<String, Object> props){
		if (appConfig.getKafkaSslEnabled()) {
			props.putAll(buildSslConfigProperties());
		}
		
		return props;
	}
	
	private Map<String, Object> fillSchemaRegistryProperties(Map<String, Object> props){
		// Schema Registry location.
        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,appConfig.getKafkaSchemaRegistry());
        
        return props;
	}
}
