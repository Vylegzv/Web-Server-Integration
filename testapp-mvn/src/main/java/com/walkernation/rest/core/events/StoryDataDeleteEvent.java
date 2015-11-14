package com.walkernation.rest.core.events;

import com.google.appengine.api.datastore.Key;
import com.walkernation.rest.core.events.base.DeleteEvent;

public class StoryDataDeleteEvent extends DeleteEvent {
	private final String key;

	public StoryDataDeleteEvent(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
