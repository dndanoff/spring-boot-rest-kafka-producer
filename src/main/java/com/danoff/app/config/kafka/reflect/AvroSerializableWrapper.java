package com.danoff.app.config.kafka.reflect;

public class AvroSerializableWrapper<T> implements AvroSerializable{
	private final T object;
	
	public AvroSerializableWrapper(T object){
		this.object = object;
	}
	
	public final T getObject() {
		return object;
	}
}
