package com.walkernation.rest.core.repository;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;


import com.google.appengine.api.datastore.Text;
import com.walkernation.rest.domain.StoryData;
import com.beoui.geocell.GeocellManager;
import com.beoui.geocell.model.Point;

/**
 * This class creates and returns an instance of the PersistenceManager class.
 * 
 * @author Violetta Vylegzhanina
 * 
 */
public class StoryDataRepository implements StoryDataRepositoryInterface {

	@Override
	public StoryData saveStoryData(StoryData storyData) {

		PersistenceManager pm = getPersistenceManager();
		Transaction transaction = null;
		try {
			transaction = pm.currentTransaction();
			transaction.begin();
			pm.makePersistent(storyData);
			transaction.commit();
		} finally {
			if(transaction.isActive()){
				transaction.rollback();
			}
			pm.close();
		}
		return storyData;
	}
	
	@Override
	public StoryData getStoryData(String key) {

		PersistenceManager pm = getPersistenceManager();
		StoryData storyData = null;

		try {
			storyData = pm.getObjectById(StoryData.class, key);
		} finally {
			pm.close();
		}

		return storyData;
	}
	
	@Override
	public StoryData updateStoryData(String key, String title, Text body, 
			String tags, long storyTime) {

		PersistenceManager pm = getPersistenceManager();
		StoryData updatedStoryData = null;
		try {
			updatedStoryData = pm.getObjectById(StoryData.class,
					key);
			updatedStoryData.setTitle(title);
			updatedStoryData.setBody(body);
			updatedStoryData.setTags(tags);
			updatedStoryData.setStoryTime(storyTime);
		} finally {
			pm.close();
		}

		return updatedStoryData;
	}

	@Override
	public StoryData updateStoryTitle(StoryData storyData, String title) {

		PersistenceManager pm = getPersistenceManager();
		StoryData updatedStoryData = null;
		try {
			updatedStoryData = pm.getObjectById(StoryData.class,
					storyData.getKey());
			updatedStoryData.setTitle(title);;
		} finally {
			pm.close();
		}

		return updatedStoryData;
	}

	@Override
	public StoryData updateStoryBody(StoryData storyData, Text body) {

		PersistenceManager pm = getPersistenceManager();
		StoryData updatedStoryData = null;
		try {
			updatedStoryData = pm.getObjectById(StoryData.class,
					storyData.getKey());
			updatedStoryData.setBody(body);
		} finally {
			pm.close();
		}

		return updatedStoryData;
	}

	@Override
	public StoryData updateStoryAudoLink(StoryData storyData, String audioLink) {

		PersistenceManager pm = getPersistenceManager();
		StoryData updatedStoryData = null;
		try {
			updatedStoryData = pm.getObjectById(StoryData.class,
					storyData.getKey());
			updatedStoryData.audioLink = audioLink;
		} finally {
			pm.close();
		}

		return updatedStoryData;
	}

	@Override
	public StoryData updateStoryVideoLink(StoryData storyData, String videoLink) {

		PersistenceManager pm = getPersistenceManager();
		StoryData updatedStoryData = null;
		try {
			updatedStoryData = pm.getObjectById(StoryData.class,
					storyData.getKey());
			updatedStoryData.videoLink = videoLink;
		} finally {
			pm.close();
		}

		return updatedStoryData;
	}

	// NEED TO CHANGE THAT
	@Override
	public StoryData updateStoryImageLink(StoryData storyData, String imageLink) {

		/*PersistenceManager pm = getPersistenceManager();
		StoryData updatedStoryData = null;
		try {
			updatedStoryData = pm.getObjectById(StoryData.class,
					storyData.getKey());
			updatedStoryData.imageLink = imageLink;
		} finally {
			pm.close();
		}

		return updatedStoryData;*/
		return null;
	}

	@Override
	public StoryData updateStoryTags(StoryData storyData, String tags) {

		PersistenceManager pm = getPersistenceManager();
		StoryData updatedStoryData = null;
		try {
			updatedStoryData = pm.getObjectById(StoryData.class,
					storyData.getKey());
			updatedStoryData.setTags(tags);
		} finally {
			pm.close();
		}

		return updatedStoryData;
	}

	@Override
	public StoryData updateStoryTime(StoryData storyData, long storyTime) {

		PersistenceManager pm = getPersistenceManager();
		StoryData updatedStoryData = null;
		try {
			updatedStoryData = pm.getObjectById(StoryData.class,
					storyData.getKey());
			updatedStoryData.setStoryTime(storyTime);;
		} finally {
			pm.close();
		}

		return updatedStoryData;
	}

	@Override
	public List<StoryData> getAllStoryData() {

		PersistenceManager pm = getPersistenceManager();
		List<StoryData> allStories = new ArrayList<StoryData>();
		try {
			Query q = pm.newQuery(StoryData.class);
			try {
				List<StoryData> results = (List<StoryData>) q.execute();
				if (!results.isEmpty()) {
					allStories.addAll(results);
				} else {
					// Handle "no results" case
				}
			} finally {
				q.closeAll();
			}
		} finally {
			pm.close();
		}

		return allStories;
	}
	
	@Override
	public List<StoryData> getAllStoryData(double latitude, double longitude,
			double distance) {
		
		Point center = new Point(latitude, longitude);
		PersistenceManager pm = getPersistenceManager();
		
		List<StoryData> allStoriesWithin = null;
		try{
			allStoriesWithin = GeocellManager.proximitySearch(center, 100, distance, StoryData.class, null, pm);
		}catch(Exception e){
			
		}
		
		return allStoriesWithin;
	}

	/*@Override
	public List<StoryData> getAllStoryData(double latitude, double longitude,
			double distance) {
		
		double earthRadius = 6371.01;
		GeoLocation location = GeoLocation.fromDegrees(latitude, longitude);
		GeoLocation[] boundingCoordinates = location.boundingCoordinates(distance, earthRadius);
		boolean meridian180WithinDistance = boundingCoordinates[0].getLongitudeInRadians() >
			boundingCoordinates[1].getLongitudeInRadians();
		double calculation = Math.acos(Math.sin(location.getLatitudeInRadians() * Math.sin(latitude) +
				Math.cos(location.getLatitudeInRadians()* Math.cos(latitude) * 
						Math.cos(longitude - location.getLongitudeInRadians()))));
		double div = distance / earthRadius;
		
		PersistenceManager pm = getPersistenceManager();
		List<StoryData> allStories = new ArrayList<StoryData>();
		try {
			Query q = pm.newQuery(StoryData.class, "");
			q.setFilter(":latitude >= " + boundingCoordinates[0].getLatitudeInRadians() + " && :latitude <= " +
					boundingCoordinates[1].getLatitudeInRadians() + " && :longitude >= " + boundingCoordinates[0].getLongitudeInRadians() +
					(meridian180WithinDistance ? "|| " : " && ") + ":longitude <= " + boundingCoordinates[1].getLongitudeInRadians() + 
					" && " + calculation + " <= " + div);
			try {
				List<StoryData> results = (List<StoryData>) q.execute();
				if (!results.isEmpty()) {
					allStories.addAll(results);
				} else {
					// Handle "no results" case
				}
			} finally {
				q.closeAll();
			}
		} finally {
			pm.close();
		}

		return allStories;
	}*/

	@Override
	public boolean deleteStoryData(String key) {

		PersistenceManager pm = getPersistenceManager();
		boolean deleted = false;
		try {
			StoryData storyDataToDelete = pm
					.getObjectById(StoryData.class, key);
			pm.deletePersistent(storyDataToDelete);
			deleted = true;
		} finally {
			pm.close();
		}

		return deleted;
	}

	@Override
	public boolean deleteStoryDataAll() {
		
		PersistenceManager pm = getPersistenceManager();
		boolean res = false;
		List<StoryData> allStories = new ArrayList<StoryData>();
		try {
			Query q = pm.newQuery(StoryData.class);
			try {
				List<StoryData> results = (List<StoryData>) q.execute();
				if (!results.isEmpty()) {
					allStories.addAll(results);
				} else {
					// Handle "no results" case
				}
			} finally {
				q.closeAll();
			}
		} finally {
			if(!allStories.isEmpty()){
				pm.deletePersistentAll(allStories);
				res = true;
			}
			pm.close();
		}

		return res;
	}

	private PersistenceManager getPersistenceManager() {

		return MyPersistenceManagerFactory.get().getPersistenceManager();
	}

}
