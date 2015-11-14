package com.walkernation.rest.core.events;

import com.google.appengine.api.datastore.Key;
import com.walkernation.rest.core.events.base.UpdatedEvent;
import com.walkernation.rest.domain.StoryData;

public class StoryDataUpdatedEvent extends UpdatedEvent{
	
	private final Key newOrderKey;
	private final StoryData details;

	public StoryDataUpdatedEvent(final Key newOrderKey, final StoryData details) {
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
