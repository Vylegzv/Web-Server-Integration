package com.walkernation.rest.core.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.appengine.api.datastore.Text;
import com.walkernation.rest.domain.StoryData;

public class FakeDataRepository implements StoryDataRepositoryInterface {

	private Map<UUID, StoryData> stories;

	public FakeDataRepository() {
		HashMap<UUID, StoryData> rValue = new HashMap<UUID, StoryData>();
//		int MAX_DEFAULT_STORIES = 10;
//		for (int i = 0; i < MAX_DEFAULT_STORIES; i++) {
//			UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
//			StoryData storyData = new StoryData(0, 0, "a", new Text("b"), "c", "d", "e",
//					"f", "g", 0, 0, 0.0, 0.0);
//			storyData.uuid = uuid;
//			rValue.put(uuid, storyData);
//		}
		this.stories = Collections.unmodifiableMap(rValue);
	}

	public FakeDataRepository(final Map<UUID, StoryData> stories) {
		this.stories = Collections.unmodifiableMap(stories);
	}

	@Override
	public synchronized StoryData saveStoryData(StoryData storyData) {
		Map<UUID, StoryData> modifiableOrders = new HashMap<UUID, StoryData>(
				stories);
		modifiableOrders.put(UUID.fromString(storyData.getKey()+""), storyData);
		this.stories = Collections.unmodifiableMap(modifiableOrders);

		return storyData;
	}

	@Override
	public List<StoryData> getAllStoryData() {
		return Collections.unmodifiableList(new ArrayList<StoryData>(stories
				.values()));
	}

	@Override
	public List<StoryData> getAllStoryData(double latitude, double longitude,
			double distance) {
		// TODO: This purposely doesn't do anything useful other than just the
		// same as 'getAllStoryData', this will be a 'final' class issue to
		// address
		return Collections.unmodifiableList(new ArrayList<StoryData>(stories
				.values()));
	}

	@Override
	public StoryData getStoryData(String key) {
		return stories.get(key);
	}

	@Override
	public synchronized boolean deleteStoryData(String key) {
		if (stories.containsKey(key)) {
			Map<UUID, StoryData> modifiableOrders = new HashMap<UUID, StoryData>(
					stories);
			modifiableOrders.remove(key);
			this.stories = Collections.unmodifiableMap(modifiableOrders);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public synchronized boolean deleteStoryDataAll() {
		if (stories.size() > 0) {
			Map<UUID, StoryData> modifiableOrders = new HashMap<UUID, StoryData>(
					stories);
			// TODO: probably a better way to do this, but it 'works' for now
			// this is only TEMP class anyways, so 'good enough' for now.
			for (UUID key : stories.keySet()) {
				modifiableOrders.remove(key);
			}
			this.stories = Collections.unmodifiableMap(modifiableOrders);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public StoryData updateStoryTitle(StoryData storyData, String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoryData updateStoryBody(StoryData storyData, Text body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoryData updateStoryAudoLink(StoryData storyData, String audioLink) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoryData updateStoryVideoLink(StoryData storyData, String videoLink) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoryData updateStoryImageLink(StoryData storyData, String imageLink) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoryData updateStoryTags(StoryData storyData, String tags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoryData updateStoryTime(StoryData storyData, long storyTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoryData updateStoryData(String key, String title, Text body,
			String tags, long storyTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
