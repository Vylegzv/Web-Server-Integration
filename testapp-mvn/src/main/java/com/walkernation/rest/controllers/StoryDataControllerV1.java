package com.walkernation.rest.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.jdo.Query;

import org.apache.commons.io.IOUtils;
import org.gmr.web.multipart.GMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
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
import com.walkernation.rest.core.repository.MyPersistenceManagerFactory;
import com.walkernation.rest.core.services.StoryDataServiceInterface;
import com.walkernation.rest.domain.Image;
import com.walkernation.rest.domain.StoryData;
import com.walkernation.rest.domain.Video;

@Controller
@RequestMapping("/v1")
public class StoryDataControllerV1 {

	/*
	 * HTTP HEADERS HAS BOTH APP-ENGINE and SPRING imports possible
	 */

	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private StoryDataServiceInterface storyService;

	public void setStoryService(StoryDataServiceInterface storyService) {
		this.storyService = storyService;
	}

	@RequestMapping("/story")
	public @ResponseBody
	StoryData story(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			@RequestParam(value = "body", required = false, defaultValue = "defaultBody") String body) {

		return new StoryData(counter.incrementAndGet(), 2, name,
				new Text(body), "audiolink", "videoLink", "tags", 4444L, 5555L,
				10.2, 10.3);

	}

	@RequestMapping(value = "/stories/add", method = RequestMethod.GET)
	public String getAddStoryPage(ModelMap model) {

		return "add";

	}	
	
	@RequestMapping(value = "/stories/add", method = RequestMethod.POST)
	public ResponseEntity<StoryData> saveStory(
			@RequestParam("loginId") long loginId,
			@RequestParam("storyId") long storyId,
			@RequestParam("title") String title,
			@RequestParam("body") Text body,
			@RequestParam("audioLink") String audioLink,
			@RequestParam("videoLink") String videoLink,
			@RequestParam("tags") String tags,
			@RequestParam("creationTime") long creationTime,
			@RequestParam("storyTime") long storyTime,
			@RequestParam("latitude") double latitude,
			@RequestParam("longitude") double longitude,
			@RequestParam("imageName") String imageName,
			@RequestParam("image") GMultipartFile image,
			@RequestParam("imageName") String videoName,
			@RequestParam("image") GMultipartFile video,
			UriComponentsBuilder builder) {
		
		StoryData story = new StoryData(loginId, storyId, title, body,
				audioLink, videoLink, tags, creationTime, storyTime, latitude,
				longitude);

		try {
			Image storyImg = new Image(imageName, new Blob(
					IOUtils.toByteArray(image.getInputStream())));
			story.setImage(storyImg);
			
			Video storyVideo = new Video(videoName, new Blob(
					IOUtils.toByteArray(video.getInputStream())));
			story.setVideo(storyVideo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (storyService == null) {
			return null;
		}

		StoryDataCreatedEvent storyDataCreated = storyService
				.createStoryData(new StoryDataCreateEvent(story));

		StoryData newStoryData = StoryData.fromStoryData(storyDataCreated
				.getDetails());
		
		// need this trick to make sure image will be loaded together
		// with StoryData
		newStoryData.getImage();
		newStoryData.getVideo();

		HttpHeaders headers = new HttpHeaders();

		URI uri = builder.path("/v1/stories/{id}")
				.buildAndExpand(storyDataCreated.getNewOrderKey() + "").toUri();
		story.href = uri.toString();
		headers.setLocation(uri);

		return new ResponseEntity<StoryData>(newStoryData, headers,
				HttpStatus.CREATED);
	}

	/**
	 * Get a list of story data.
	 * <p>
	 * It will accept blank or all 3 params.
	 * <p>
	 * will return all stories that match.
	 * 
	 * @param lat
	 * @param lon
	 * @param dist
	 * @return
	 */
	@RequestMapping(value = "/stories", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<List<StoryData>> getAllStories() {

		if (storyService == null) {
			return null;
		}
		StoryDataListEvent details = storyService
				.requestStoryDataListAll(new RequestListAllStoryDataEvent());

		if (details == null) {
			return null;
		}

		if (!details.isEntityFound()) {
			return new ResponseEntity<List<StoryData>>(HttpStatus.NOT_FOUND);
		}

		List<StoryData> stories = (List<StoryData>) details
				.getStoryDataDetails();

		return new ResponseEntity<List<StoryData>>(stories, HttpStatus.OK);

	}

	/**
	 * Get a list of story data.
	 * <p>
	 * It will accept blank or all 3 params.
	 * <p>
	 * will return all stories that match.
	 * 
	 * @param lat
	 * @param lon
	 * @param dist
	 * @return
	 */
	@RequestMapping(value = "/stories/within", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<List<StoryData>> getStoryStoriesWithin(
			@RequestParam(value = "latitude") String latitude,
			@RequestParam(value = "longitude") String longitude,
			@RequestParam(value = "distance") String distance) {

		List<StoryData> storiesWithin = new ArrayList<StoryData>();
		Collection<StoryData> event;
		double lat = Double.parseDouble(latitude);
		double lon = Double.parseDouble(longitude);
		double dist = Double.parseDouble(distance);
		event = storyService.requestStoryDataList(
				(new RequestListStoryDataEvent(lat, lon, dist)))
				.getStoryDataDetails();

		if (event == null)
			return null;

		for (StoryData detail : event) {
			storiesWithin.add(StoryData.fromStoryData(detail));
		}
		return new ResponseEntity<List<StoryData>>(storiesWithin, HttpStatus.OK);
	}

	/**
	 * Get a list of story data.
	 * <p>
	 * It will accept blank or all 3 params.
	 * <p>
	 * will return all stories that match.
	 * 
	 * @param lat
	 * @param lon
	 * @param dist
	 * @return
	 */
	/*
	 * @RequestMapping(value = "stories/", method = RequestMethod.GET)
	 * 
	 * @ResponseStatus(HttpStatus.OK)
	 * 
	 * @ResponseBody public List<StoryData> getStoryDatas(
	 * 
	 * @RequestParam(value = "lat", required = false, defaultValue = "") String
	 * lat,
	 * 
	 * @RequestParam(value = "lon", required = false, defaultValue = "") String
	 * lon,
	 * 
	 * @RequestParam(value = "dist", required = false, defaultValue = "") String
	 * dist) {
	 * 
	 * List<StoryData> orders = new ArrayList<StoryData>();
	 * Collection<StoryData> event; if (lat.length() != 0 && lon.length() != 0
	 * && dist.length() != 0) { long latitude = Long.parseLong(lat); long
	 * longitude = Long.parseLong(lon); long distance = Long.parseLong(dist);
	 * event = storyService.requestStoryDataList( (new
	 * RequestListStoryDataEvent(latitude, longitude,
	 * distance))).getStoryDataDetails(); } else if (lat.length() == 0 &&
	 * lon.length() == 0 && dist.length() == 0) { event =
	 * storyService.requestStoryDataListAll( (new
	 * RequestListAllStoryDataEvent())).getStoryDataDetails(); } else { return
	 * orders; }
	 * 
	 * for (StoryData detail : event) { // System.out.print("*********** " +
	 * detail.uuid); orders.add(StoryData.fromStoryData(detail)); } return
	 * orders; }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/stories/{key}")
	public ResponseEntity<StoryData> viewStoryData(@PathVariable String key) {

		if (storyService == null) {
			return null;
		}
		StoryDataReadEvent details = storyService
				.requestStoryData(new RequestStoryDataReadEvent(key));

		if (details == null) {
			return null;
		}

		if (!details.isEntityFound()) {
			return new ResponseEntity<StoryData>(HttpStatus.NOT_FOUND);
		}

		StoryData order = StoryData.fromStoryData(details.getStoryDetails());

		return new ResponseEntity<StoryData>(order, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "/x/{id}") public
	 * @ResponseBody StoryData viewStoryDataOld(@PathVariable String id) {
	 * 
	 * if (Long.parseLong(id) < 10) { System.out.println(" less than 10");
	 * StoryData StoryData = new StoryData(counter.incrementAndGet(),
	 * Long.parseLong(id), "name", new Text("body"), "audiolink", "videoLink",
	 * "imageName", "imageLink", "tags", 4444L, 5555L, 10.2, 10.3);
	 * 
	 * System.out.println(" StoryData: " + StoryData.toString());
	 * 
	 * ResponseEntity<StoryData> rValue = new ResponseEntity<StoryData>(
	 * StoryData, HttpStatus.OK); System.out.println(" rValue: " + rValue);
	 * System.out.println(" rValue toString: " + rValue.toString());
	 * 
	 * return StoryData; } else { System.out.println("not found"); return null;
	 * }
	 * 
	 * }
	 */

	private String hostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException uhe) {
			return defaultHostName;
		}
	}

	private static final String defaultHostName = "localhost";

	@RequestMapping(method = RequestMethod.POST, value = "stories/")
	public ResponseEntity<StoryData> createStoryData(
			@RequestBody StoryData storyData, UriComponentsBuilder builder) {

		System.out.println("" + storyData.toString());

		StoryDataCreatedEvent storyDataCreated = storyService
				.createStoryData(new StoryDataCreateEvent(storyData));

		StoryData newStoryData = StoryData.fromStoryData(storyDataCreated
				.getDetails());

		HttpHeaders headers = new HttpHeaders();

		URI uri = builder.path("/v1/stories/{id}")
				.buildAndExpand(storyDataCreated.getNewOrderKey() + "").toUri();
		storyData.href = uri.toString();
		headers.setLocation(uri);

		return new ResponseEntity<StoryData>(newStoryData, headers,
				HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/stories/update/{id}", method = RequestMethod.POST)
	public ResponseEntity<StoryData> updateStory(@PathVariable String id,
			@RequestParam("title") String title,
			@RequestParam("body") Text body,
			@RequestParam("tags") String tags,
			@RequestParam("storyTime") long storyTime,
			UriComponentsBuilder builder) {

		if (storyService == null) {
			return null;
		}

		StoryDataUpdatedEvent storyDataUpdated = storyService
				.requestStoryDataUpdate(new StoryDataUpdateEvent(id, title, body, tags, storyTime));

		StoryData updatedStoryData = StoryData.fromStoryData(storyDataUpdated.getDetails());

		return new ResponseEntity<StoryData>(updatedStoryData, HttpStatus.CREATED);
	}
	
	
	
	/*@RequestMapping(value = "stories/update/{id}", method = RequestMethod.GET)
	public String getUpdateStoryPage(@PathVariable String id) {
		 
		return "update";
 
	}
	
	@RequestMapping(value = "stories/update/{id}", method = RequestMethod.POST)
	public ResponseEntity<StoryData> update(@PathVariable String id, 
			@RequestParam("title") String title, ModelMap model, UriComponentsBuilder builder) {
 
		PersistenceManager pm = MyPersistenceManagerFactory.get().getPersistenceManager();
		StoryData updatedStoryData = null;
		try {
			updatedStoryData = pm.getObjectById(StoryData.class,
					id);
			updatedStoryData.setTitle(title);;
		} finally {
			pm.close();
		}

		HttpHeaders headers = new HttpHeaders();
		URI uri = builder.path("/v1/stories/{id}")
				.buildAndExpand(updatedStoryData.getKey() + "").toUri();
		headers.setLocation(uri);

		return new ResponseEntity<StoryData>(updatedStoryData, headers,
				HttpStatus.CREATED);
 
	}*/

	/*
	 * @RequestMapping(method = RequestMethod.GET, value = "makestories/") //
	 * @ResponseStatus(HttpStatus.OK) public String
	 * tempMakeNewStories(UriComponentsBuilder builder) { int STORIES_TO_MAKE =
	 * 5; for (int i = 0; i < STORIES_TO_MAKE; i++) { UUID uuid = new
	 * UUID(System.nanoTime(), System.nanoTime()); StoryData storyData = new
	 * StoryData(0, 0, "a", new Text("b"), "c", "d", "e", "f", "g", 0, 0, 0.0,
	 * 0.0); //storyData.uuidString = uuid.toString(); StoryDataCreatedEvent
	 * storyDataCreated = storyService .createStoryData(new
	 * StoryDataCreateEvent(storyData));
	 * 
	 * StoryData newStoryData = StoryData.fromStoryData(storyDataCreated
	 * .getDetails());
	 * 
	 * //System.out.println(newStoryData.uuidString);
	 * 
	 * URI uri = builder.path("/v1/stories/")
	 * .buildAndExpand(storyDataCreated.getNewOrderKey() + "") .toUri();
	 * storyData.href = uri.toString(); } return "5 New stories created"; }
	 */

	@RequestMapping(value = "stories/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<StoryData> deleteStory(@PathVariable String id, HttpServletRequest r) {

		StoryDataDeletedEvent StoryDataDeleted = storyService
				.deleteStoryData(new StoryDataDeleteEvent(id));

		if (!StoryDataDeleted.isEntityFound()) {
			return new ResponseEntity<StoryData>(HttpStatus.NOT_FOUND);
		}

		StoryData storyData = StoryData.fromStoryData(StoryDataDeleted
				.getDetails());

		if (StoryDataDeleted.isDeletionCompleted()) {
			return new ResponseEntity<StoryData>(storyData, HttpStatus.OK);
		}
		
		return new ResponseEntity<StoryData>(storyData, HttpStatus.FORBIDDEN);
	}

}
