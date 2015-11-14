package com.walkernation.rest.core.events;

import java.util.UUID;

import com.walkernation.rest.core.events.base.RequestReadEvent;

public class RequestStoryDataReadEvent extends RequestReadEvent {

	private String key;

	public RequestStoryDataReadEvent(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
