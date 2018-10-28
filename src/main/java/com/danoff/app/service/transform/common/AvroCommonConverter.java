package com.danoff.app.service.transform.common;

public interface AvroCommonConverter {
	
	public default String charsequenceToString(CharSequence sequence) {
		if(sequence == null) {
			return "";
		}
		
		return sequence.toString();
	}
}
