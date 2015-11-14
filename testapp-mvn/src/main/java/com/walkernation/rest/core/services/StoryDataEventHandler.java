package com.walkernation.rest.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.walkernation.rest.core.repository.StoryDataRepositoryInterface;
import com.walkernation.rest.domain.StoryData;

public class StoryDataEventHandler implements StoryDataServiceInterface {

	private final StoryDataRepositoryInterface storyDataRepository;

	// public StoryDataEventHandler() {
	// storyDataRepository = new FakeDataRepository(
	// new HashMap<UUID, StoryData>());
	// }

	/*
	 * Spring creation/injection of repository
	 */
	public StoryDataEventHandler(
			final StoryDataRepositoryInterface storyDataRepository) {
		System.out.println("**** Story Data repo: " + storyDataRepository);
		this.storyDataRepository = storyDataRepository;
	}

	@Override
	public StoryDataCreatedEvent createStoryData(
			StoryDataCreateEvent createStoryDataEvent) {

		StoryData storyData = StoryData.fromStoryData(createStoryDataEvent
				.getmStoryData());

		createStoryDataEvent.setmStoryData(storyData.clone());

		storyData = storyDataRepository.saveStoryData(storyData);

		return new StoryDataCreatedEvent(storyData.getKey(), storyData);
	}

	@Override
	public StoryDataListEvent requestStoryDataListAll(
			RequestListAllStoryDataEvent requestListAllStoryDataEvent) {
		List<StoryData> generatedDetails = new ArrayList<StoryData>();
		for (StoryData storyData : storyDataRepository.getAllStoryData()) {
			generatedDetails.add(storyData);
		}
		// System.out.println("requestStoryDataListAll size:"
		// + generatedDetails.size());
		// for (StoryData storyData : generatedDetails) {
		// System.out.print(" " + storyData.uuid );
		// }
		return new StoryDataListEvent(generatedDetails);
	}

	@Override
	public StoryDataListEvent requestStoryDataList(
			RequestListStoryDataEvent requestListStoryDataEvent) {
		List<StoryData> generatedDetails = new ArrayList<StoryData>();
		double distance = requestListStoryDataEvent.getDistance();
		double latitude = requestListStoryDataEvent.getLatitude();
		double longitude = requestListStoryDataEvent.getLongitude();
		for (StoryData storyData : storyDataRepository.getAllStoryData(
				latitude, longitude, distance)) {
			generatedDetails.add(storyData);
		}
		return new StoryDataListEvent(generatedDetails);
	}

	@Override
	public StoryDataReadEvent requestStoryData(
			RequestStoryDataReadEvent requestReadEvent) {

		StoryData result = storyDataRepository.getStoryData(requestReadEvent
				.getKey());

		StoryDataReadEvent rValue = new StoryDataReadEvent(requestReadEvent.getKey(), result);

		return rValue;

	}

	@Override
	public StoryDataUpdatedEvent requestStoryDataUpdate(
			StoryDataUpdateEvent storyDataUpdateEvent) {

		StoryData updatedStory = storyDataRepository.updateStoryData(storyDataUpdateEvent.getKey(), storyDataUpdateEvent.getTitle(),
				storyDataUpdateEvent.getBody(), storyDataUpdateEvent.getTags(), storyDataUpdateEvent.getStoryTime());


		return new StoryDataUpdatedEvent(updatedStory.getKey(), updatedStory);
	}

	@Override
	public StoryDataDeletedEvent deleteStoryData(
			StoryDataDeleteEvent deleteStoryDataEvent) {

		StoryData result = storyDataRepository
				.getStoryData(deleteStoryDataEvent.getKey());

		if (result == null) {
			return StoryDataDeletedEvent
					.notFound(deleteStoryDataEvent.getKey());
		}

		if (!result.canBeDeleted()) {
			return StoryDataDeletedEvent.deletionForbidden(
					deleteStoryDataEvent.getKey(), result);
		}

		storyDataRepository.deleteStoryData(deleteStoryDataEvent.getKey());

		return new StoryDataDeletedEvent(deleteStoryDataEvent.getKey(), result);
	}

}
