package com.danoff.app.service.transform.common;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface GenericObjectConverter<A, D> {

	A dtoToAvroSpecificRecord(D dto);
	
	D avroSpecificRecordToDto(A entity);

	default List<A> dtoToAvroSpecificRecord(Collection<D> dtos) {
		return Optional.ofNullable(dtos).map(Collection::stream).orElseGet(Stream::empty)
				.map(this::dtoToAvroSpecificRecord).collect(Collectors.toList());
	}

	default List<D> avroSpecificRecordToDto(Collection<A> entities) {
		return Optional.ofNullable(entities).map(Collection::stream).orElseGet(Stream::empty)
				.map(this::avroSpecificRecordToDto).collect(Collectors.toList());
	}
}
