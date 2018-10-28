package com.danoff.app.config.kafka.reflect;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.kafka.common.errors.SerializationException;

import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class ReflectKafkaAvroSerializer extends KafkaAvroSerializer {
	
	private static final EncoderFactory ENCODER_FACTORY = EncoderFactory.get();
	private static final ReflectData REFLECT_DATA = ReflectData.get();

	@Override
	protected byte[] serializeImpl(String subject, Object object) throws SerializationException {
		if (object == null) {
			return null;
		}

		if (object instanceof AvroSerializableWrapper) {
			return serializeObject(subject, ((AvroSerializableWrapper<?>)object).getObject());
		}else if(object instanceof AvroSerializable) {
			return serializeObject(subject, object);
		}
		
		return super.serializeImpl(subject, object);
	}

	private byte[] serializeObject(String subject, Object object) {
		Schema schema = REFLECT_DATA.getSchema(object.getClass());
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
			int registeredSchemaId = this.schemaRegistry.register(subject, schema);

			out.write(0);
			out.write(ByteBuffer.allocate(4).putInt(registeredSchemaId).array());

			DatumWriter<Object> dw = new ReflectDatumWriter<>(schema);
			Encoder encoder = ENCODER_FACTORY.directBinaryEncoder(out, null);
			dw.write(object, encoder);
			encoder.flush();
			return out.toByteArray();
		} catch (RuntimeException | IOException e) {
			throw new SerializationException("Error serializing Avro message", e);
		} catch (RestClientException e) {
			throw new SerializationException("Error registering Avro schema: " + schema, e);
		}
	}
}
