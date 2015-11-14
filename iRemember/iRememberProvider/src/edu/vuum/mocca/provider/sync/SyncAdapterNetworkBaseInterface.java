package edu.vuum.mocca.provider.sync;

import java.util.ArrayList;

import edu.vuum.mocca.orm.StoryData;

public interface SyncAdapterNetworkBaseInterface {

	// TODO should this class have its own exceptions, such as:
	// the HTTP 200/400/404/etc , to represent what error was thrown

	/**
	 * Upload A StoryData object to the server.
	 * 
	 * @param storyData
	 *            StoryData Object to be uploaded
	 * @return true if upload completed, otherwise false
	 * @throws Exception
	 */
	public void uploadStoryData(StoryData storyData) throws Exception;

	/**
	 * Upload a set of StoryData objects to the server.
	 * 
	 * @param storyDataList
	 *            ArrayList of StoryData Objects to be uploaded
	 * @return true if upload completed, otherwise false
	 * @throws Exception
	 */
	public void uploadStoryData(StoryData[] storyDataList) throws Exception;

	/**
	 * Update a StoryData object on the server.
	 * 
	 * @param storyData
	 *            ArrayList of StoryData Objects to be updated
	 * @return true if update completed, otherwise false
	 * @throws Exception
	 */
	public void updateStoryData(StoryData storyData) throws Exception;

	/**
	 * Update a set of StoryData objects on the server.
	 * 
	 * @param storyDataList
	 *            StoryData object to update
	 * @return true if update completed, otherwise false
	 * @throws Exception
	 */
	public void updateStoryData(ArrayList<StoryData> storyDataList)
			throws Exception;

	/**
	 * Download a target StoryData Object with the key that matches the provided
	 * UUID as a string
	 * 
	 * @param UUIDString
	 *            String representation of the UUID key for the target StoryData
	 * @return the StoryData object if it existed, and communication completed,
	 *         otherwise null
	 * @throws Exception
	 */
	public void downloadStoryData(String UUIDString) throws Exception;

	/**
	 * Download all StoryData on the server (CAUTION, could be very dangerous)
	 * 
	 * @return ArrayList of StoryData of the results, with size 0 if no results.
	 * @throws Exception
	 */
	public ArrayList<StoryData> downloadAllStoryData() throws Exception;

	/**
	 * Download all StoryData on the server (CAUTION, could be very dangerous),
	 * filtered by the provided filter parameter
	 * 
	 * @param fieldName
	 *            Name of the field to filter on
	 * @param filter
	 *            String to filter with
	 * @return ArrayList of StoryData of the results, with size 0 if no results.
	 * @throws Exception
	 */
	public ArrayList<StoryData> downloadAllStoryData(String fieldName,
			String filter) throws Exception;

}
