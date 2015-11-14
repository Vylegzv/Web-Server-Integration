package com.walkernation.rest.core.events;
import com.google.appengine.api.datastore.Text;
import com.walkernation.rest.core.events.base.UpdatedEvent;
import com.walkernation.rest.domain.StoryData;

public class StoryDataUpdateEvent extends UpdatedEvent {

	private String key;
	private String title;
	private Text body;
	private String tags;
	private long storyTime;

	public StoryDataUpdateEvent(String key, String title, Text body, String tags, long storyTime) {
		super();
		this.key = key;
		this.title = title;
		this.body = body;
		this.tags = tags;
		this.storyTime = storyTime;
	}

	public String getKey() {
		return key;
	}
	
	public String getTitle(){
		return title;
	}
	
	public Text getBody(){
		return body;
	}
	
	public String getTags(){
		return tags;
	}
	
	public long getStoryTime(){
		return storyTime;
	}
}
