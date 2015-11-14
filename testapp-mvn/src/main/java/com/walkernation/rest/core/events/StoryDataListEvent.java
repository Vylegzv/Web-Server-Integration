package com.walkernation.rest.core.events;

import java.util.Collection;
import java.util.List;

import com.walkernation.rest.core.events.base.ReadEvent;
import com.walkernation.rest.domain.StoryData;

public class StoryDataListEvent extends ReadEvent {

	private final List<StoryData> storyDataDetails;
	private final long latitude;
	private final long longitude;
	private final long range;

	public StoryDataListEvent(List<StoryData> storyDataDetails) {
		this.storyDataDetails = storyDataDetails;
		this.latitude = -1;
		this.longitude = -1;
		this.range = -1;
	}

	public StoryDataListEvent(List<StoryData> storyDataDetails, long latitude,
			long longitude, long range) {
		super();
		this.storyDataDetails = storyDataDetails;
		this.latitude = latitude;
		this.longitude = longitude;
		this.range = range;
	}

	public long getLatitude() {
		return latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public long getRange() {
		return range;
	}

	public Collection<StoryData> getStoryDataDetails() {
		return storyDataDetails;
	}

}
