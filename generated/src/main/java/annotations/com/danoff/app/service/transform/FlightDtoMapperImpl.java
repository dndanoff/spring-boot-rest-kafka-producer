package com.danoff.app.service.transform;

import com.danoff.app.dto.FlightDto;
import com.danoff.dto.avro.FlightAvroDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-10-28T18:06:51+0200",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
@Component
public class FlightDtoMapperImpl implements FlightDtoMapper {

    @Override
    public FlightAvroDto dtoToAvroSpecificRecord(FlightDto dto) {
        if ( dto == null ) {
            return null;
        }

        FlightAvroDto flightAvroDto = new FlightAvroDto();

        flightAvroDto.setId( dto.getId() );
        flightAvroDto.setDepartureAirport( charsequenceToString( dto.getDepartureAirport() ) );
        flightAvroDto.setArrivalAirport( charsequenceToString( dto.getArrivalAirport() ) );

        return flightAvroDto;
    }

    @Override
    public FlightDto avroSpecificRecordToDto(FlightAvroDto entity) {
        if ( entity == null ) {
            return null;
        }

        FlightDto flightDto = new FlightDto();

        flightDto.setId( entity.getId() );
        flightDto.setDepartureAirport( charsequenceToString( entity.getDepartureAirport() ) );
        flightDto.setArrivalAirport( charsequenceToString( entity.getArrivalAirport() ) );

        return flightDto;
    }
}
