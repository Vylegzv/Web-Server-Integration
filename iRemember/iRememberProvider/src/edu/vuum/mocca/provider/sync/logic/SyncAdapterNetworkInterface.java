package edu.vuum.mocca.provider.sync.logic;

import java.util.ArrayList;

import edu.vuum.mocca.orm.StoryData;
import edu.vuum.mocca.provider.sync.SyncAdapterNetworkBaseInterface;

/**
 * Model Specific changes to the SyncAdapterNetworkInterface, These are methods
 * that are unique to your model, In this example, we have two, both get a list
 * of stories around the user's current location, with and without filtering.
 * 
 * @author Michael A. Walker
 * 
 */
public interface SyncAdapterNetworkInterface extends
		SyncAdapterNetworkBaseInterface {

	public ArrayList<StoryData> getListOfStories(long latitude, long longitude,
			long radiusInMeter) throws Exception;

	public ArrayList<StoryData> getListOfStories(long latitude, long longitude,
			long radiusInMeter, String filter) throws Exception;

}
