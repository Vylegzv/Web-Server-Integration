package com.walkernation.rest.core.services;

import com.walkernation.rest.core.events.RequestListAllStoryDataEvent;
import com.walkernation.rest.core.events.RequestListStoryDataEvent;
import com.walkernation.rest.core.events.RequestStoryDataReadEvent;
import com.walkernation.rest.core.events.StoryDataCreateEvent;
import com.walkernation.rest.core.events.StoryDataCreatedEvent;
import com.walkernation.rest.core.events.StoryDataDeleteEvent;
import com.walkernation.rest.core.events.StoryDataDeletedEvent;
import com.walkernation.rest.core.events.StoryDataListEvent;
import com.walkernation.rest.core.events.StoryDataReadEvent;
import com.walkernation.rest.core.events.StoryDataUpdateEvent;
import com.walkernation.rest.core.events.StoryDataUpdatedEvent;

public interface StoryDataServiceInterface {

	// create
	public StoryDataCreatedEvent createStoryData(
			StoryDataCreateEvent createStoryDataEvent);

	// list (read)
	public StoryDataListEvent requestStoryDataListAll(
			RequestListAllStoryDataEvent requestListAllStoryDataEvent);

	// list some (read)
	public StoryDataListEvent requestStoryDataList(
			RequestListStoryDataEvent requestListStoryDataEvent);

	// view single (read)
	public StoryDataReadEvent requestStoryData(
			RequestStoryDataReadEvent requestReadEvent);

	// update
	public StoryDataUpdatedEvent requestStoryDataUpdate(
			StoryDataUpdateEvent storyDataUpdateEvent);

	// delete
	public StoryDataDeletedEvent deleteStoryData(
			StoryDataDeleteEvent deleteStoryDataEvent);

}
