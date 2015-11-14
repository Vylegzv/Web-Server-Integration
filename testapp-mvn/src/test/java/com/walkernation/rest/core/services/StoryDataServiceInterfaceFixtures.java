package com.walkernation.rest.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.appengine.api.datastore.Text;
import com.walkernation.rest.core.events.StoryDataReadEvent;
import com.walkernation.rest.domain.StoryData;

public class StoryDataServiceInterfaceFixtures {
	public static List<StoryData> getAllStoryDataEmpty() {
		ArrayList<StoryData> rValue = new ArrayList<StoryData>();
		return rValue;
	}

	public static List<StoryData> getAllStoryDataPartial() {
		return getStoryData(2);
	}

	public static List<StoryData> getAllStoryDataAll() {
		return getStoryData(10);
	}

	private static List<StoryData> getStoryData(int size) {
		ArrayList<StoryData> rValue = new ArrayList<StoryData>();
		for (int i = 0; i < size; i++) {
			/*StoryData data = new StoryData(new UUID(System.nanoTime(),
					System.nanoTime()), 0, 0, 0, "a", new Text("b"), "c", "d", "e", "f",
					"g", 0, 0, 0.0, 0.0);*/
			StoryData data = new StoryData(0, 0, 0, "a", new Text("b"), "e", "f",
					"g", 0, 0, 0.0, 0.0);
			rValue.add(data);
		}
		return rValue;
	}

	public static boolean compareTwoResults(List<StoryData> a, List<StoryData> b) {
		if (a.size() != b.size()) {
			return false;
		}
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) != b.get(i)) {
				return false;
			}
		}

		return true;
	}
	
}
