package com.danoff.app.service.transform;

import org.mapstruct.Mapper;

import com.danoff.app.dto.FlightDto;
import com.danoff.app.service.transform.common.AvroCommonConverter;
import com.danoff.app.service.transform.common.GenericObjectConverter;
import com.danoff.dto.avro.FlightAvroDto;

@Mapper(componentModel = "spring")
public interface FlightDtoMapper extends GenericObjectConverter<FlightAvroDto, FlightDto>, AvroCommonConverter{
		
}
