package com.walkernation.rest.core.events;

import com.walkernation.rest.core.events.base.ReadEvent;
import com.walkernation.rest.domain.StoryData;

public class StoryDataReadEvent extends ReadEvent {

	private String key;
	private StoryData storyDetails;

	public StoryDataReadEvent(String key) {
		super();
		this.key = key;
	}

	public StoryDataReadEvent(String key, StoryData storyDetails) {
		this.key = key;
		this.storyDetails = storyDetails;
	}

	public String getKey() {
		return key;
	}

	public StoryData getStoryDetails() {
		return storyDetails;
	}

	public static StoryDataReadEvent notFound(String key) {
		StoryDataReadEvent ev = new StoryDataReadEvent(key);
		ev.entityFound = false;
		return ev;
	}
}
