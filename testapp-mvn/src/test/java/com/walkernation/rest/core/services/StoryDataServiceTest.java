package com.walkernation.rest.core.services;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.walkernation.rest.core.events.RequestListAllStoryDataEvent;
import com.walkernation.rest.core.events.StoryDataListEvent;
import com.walkernation.rest.core.repository.StoryDataRepositoryInterface;
import com.walkernation.rest.domain.StoryData;

public class StoryDataServiceTest {

	@Mock
	StoryDataRepositoryInterface storyRepository;

	StoryDataEventHandler handlerService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		handlerService = new StoryDataEventHandler(storyRepository);
	}

	@Test
	public void testServiceHandlingEmptyReposity() {
		when(storyRepository.getAllStoryData()).thenReturn(
				StoryDataServiceInterfaceFixtures.getAllStoryDataEmpty());

		StoryDataListEvent list = handlerService
				.requestStoryDataListAll(new RequestListAllStoryDataEvent());

		Collection<StoryData> storyDataReturned = list.getStoryDataDetails();

		if (storyDataReturned.size() != 0) {
			fail();
		}
	}

	@Test
	public void testServiceHandlingPartialReposity() {
		when(storyRepository.getAllStoryData()).thenReturn(
				StoryDataServiceInterfaceFixtures.getAllStoryDataPartial());

		StoryDataListEvent list = handlerService
				.requestStoryDataListAll(new RequestListAllStoryDataEvent());

		Collection<StoryData> storyDataReturned = list.getStoryDataDetails();

		ArrayList<StoryData> returnedList = new ArrayList<StoryData>(
				storyDataReturned);

		if (StoryDataServiceInterfaceFixtures.compareTwoResults(returnedList,
				StoryDataServiceInterfaceFixtures.getAllStoryDataPartial())) {
			fail();
		}

	}

	@Test
	public void testServiceHandlingCompleteReposity() {
		when(storyRepository.getAllStoryData()).thenReturn(
				StoryDataServiceInterfaceFixtures.getAllStoryDataAll());

		StoryDataListEvent list = handlerService
				.requestStoryDataListAll(new RequestListAllStoryDataEvent());

		Collection<StoryData> storyDataReturned = list.getStoryDataDetails();

		ArrayList<StoryData> returnedList = new ArrayList<StoryData>(
				storyDataReturned);

		if (StoryDataServiceInterfaceFixtures.compareTwoResults(returnedList,
				StoryDataServiceInterfaceFixtures.getAllStoryDataAll())) {
			fail();
		}

	}

}
