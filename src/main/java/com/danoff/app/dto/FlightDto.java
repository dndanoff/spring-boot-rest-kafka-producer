package com.danoff.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"id","departureAirport","arrivalAirport"})
public class FlightDto {
	@JsonProperty(required=true)
	private Long id;
	@JsonProperty(required=true)
	private String departureAirport;
	@JsonProperty(required=true)
	private String arrivalAirport;
}
