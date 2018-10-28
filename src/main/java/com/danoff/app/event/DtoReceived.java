package com.danoff.app.event;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class DtoReceived<T> implements ResolvableTypeProvider {

	private final T dto;

	public DtoReceived(T dto) {
		this.dto = dto;
	}

	public final T getDto() {
		return dto;
	}

	@Override
	public ResolvableType getResolvableType() {
		return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(dto));
	}
}
