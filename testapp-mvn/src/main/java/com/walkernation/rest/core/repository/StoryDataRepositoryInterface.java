package com.walkernation.rest.core.repository;

import java.util.List;
import java.util.UUID;

import com.google.appengine.api.datastore.Text;
import com.walkernation.rest.domain.StoryData;

public interface StoryDataRepositoryInterface {

	// create
	StoryData saveStoryData(StoryData storyData);
	
	// update
	public StoryData updateStoryData(String key, String title, Text body, 
			String tags, long storyTime); 
	StoryData updateStoryTitle(StoryData storyData, String title);
	StoryData updateStoryBody(StoryData storyData, Text body);
	StoryData updateStoryAudoLink(StoryData storyData, String audioLink);
	StoryData updateStoryVideoLink(StoryData storyData, String videoLink);
	StoryData updateStoryImageLink(StoryData storyData, String imageLink);
	StoryData updateStoryTags(StoryData storyData, String tags);
	StoryData updateStoryTime(StoryData storyData, long storyTime);

	// read (all)
	List<StoryData> getAllStoryData();

	// read (some)
	List<StoryData> getAllStoryData(double latitude, double longitude, double distance);

	// read (one)
	StoryData getStoryData(String key);

	// delete
	boolean deleteStoryData(String key);

	// delete all
	boolean deleteStoryDataAll();

}
