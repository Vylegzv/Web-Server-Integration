package com.walkernation.rest.core.events;

import com.walkernation.rest.domain.StoryData;

public class StoryDataCreateEvent {

	private StoryData mStoryData;

	public StoryDataCreateEvent(StoryData mStoryData) {
		this.mStoryData = mStoryData;
	}

	public StoryData getmStoryData() {
		return mStoryData;
	}

	public void setmStoryData(StoryData mStoryData) {
		this.mStoryData = mStoryData;
	}

}
