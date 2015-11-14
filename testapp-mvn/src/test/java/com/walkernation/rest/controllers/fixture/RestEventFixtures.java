package com.walkernation.rest.controllers.fixture;

import java.util.UUID;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.walkernation.rest.core.events.StoryDataCreatedEvent;
import com.walkernation.rest.core.events.StoryDataReadEvent;
import com.walkernation.rest.domain.StoryData;

public class RestEventFixtures {

//	public static StoryDataReadEvent storyDataNotFound(UUID key) {
//		return StoryDataReadEvent.notFound(key);
//	}
//
//	public static StoryDataReadEvent storyDataFound(UUID key) {
//
//		StoryData data = new StoryData(key, 0, 0, 0, "a", new Text("b"), "c", "d", "e",
//				"f", "g", 0, 0, 0.0, 0.0);
//		StoryDataReadEvent rValue = new StoryDataReadEvent(key, data);
//		return rValue;
//	}
//
//	public static StoryDataCreatedEvent orderCreated(UUID key) {
//		return new StoryDataCreatedEvent(key, customKeyStoryData(key));
//	}
//
//	public static StoryDataReadEvent orderFound(UUID key) {
//		return new StoryDataReadEvent(key, customKeyStoryData(key));
//	}

	/*public static StoryData customKeyStoryData(UUID uuid) {
		return new StoryData(uuid, 0, 0, 0, "a", new Text("b"), "c", "d", "e", "f", "g",
				0, 0, 0.0, 0.0);
	}*/
	
	public static StoryData customKeyStoryData(Key key) {
		return new StoryData(key, 0, 0, 0, "a", new Text("b"), "e", "f", "g",
				0, 0, 0.0, 0.0);
	}

	public static String getJSON() {
		return "{\"uuid\":\"0000be77-a62d-09ea-0000-be77a62d0a64\",\"KEY_ID\":-1,\"loginId\":0,\"storyId\":0,\"title\":\"a\",\"body\":\"b\",\"audioLink\":\"c\",\"videoLink\":\"d\",\"imageName\":\"e\",\"imageLink\":\"f\",\"tags\":\"g\",\"creationTime\":0,\"storyTime\":0,\"latitude\":0.0,\"longitude\":0.0,\"canBeDeleted\":true,\"key\":\"0000be77-a62d-09ea-0000-be77a62d0a64\"}";
	}
}
