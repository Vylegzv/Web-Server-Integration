package com.walkernation.rest.core.events;

import java.util.UUID;

import com.google.appengine.api.datastore.Key;
import com.walkernation.rest.core.events.base.DeletedEvent;
import com.walkernation.rest.domain.StoryData;

public class StoryDataDeletedEvent extends DeletedEvent {

	private String key;
	private StoryData details;
	private boolean deletionCompleted;

	public StoryDataDeletedEvent(String key) {
		this.key = key;
	}

	public StoryDataDeletedEvent(String key2, StoryData details2) {
		this.key = key2;
		this.details = details2;
	}

	public static StoryDataDeletedEvent deletionForbidden(String key,
			StoryData details) {
		StoryDataDeletedEvent ev = new StoryDataDeletedEvent(key, details);
		ev.entityFound = true;
		ev.deletionCompleted = false;
		return ev;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public StoryData getDetails() {
		return details;
	}

	public void setDetails(StoryData details) {
		this.details = details;
	}

	public boolean isDeletionCompleted() {
		return deletionCompleted;
	}

	public void setDeletionCompleted(boolean deletionCompleted) {
		this.deletionCompleted = deletionCompleted;
	}

	public static StoryDataDeletedEvent notFound(String key) {
		StoryDataDeletedEvent ev = new StoryDataDeletedEvent(key);
		ev.entityFound = false;
		return ev;
	}
}
