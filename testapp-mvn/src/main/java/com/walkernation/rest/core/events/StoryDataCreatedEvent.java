package com.walkernation.rest.core.events;

import com.google.appengine.api.datastore.Key;
import com.walkernation.rest.domain.StoryData;

public class StoryDataCreatedEvent {

	private final Key newOrderKey;
	private final StoryData details;

	public StoryDataCreatedEvent(final Key newOrderKey, final StoryData details) {
		this.newOrderKey = newOrderKey;
		this.details = details;
	}

	public Key getNewOrderKey() {
		return newOrderKey;
	}

	public StoryData getDetails() {
		return details;
	}

}
