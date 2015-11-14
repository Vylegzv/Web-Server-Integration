package edu.vuum.mocca.provider.sync.logic;

import java.util.ArrayList;

import android.accounts.Account;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;
import edu.vuum.mocca.orm.MoocResolver;
import edu.vuum.mocca.orm.StoryData;

public class SyncAdapterLogic implements SyncAdapterLogicInterface {

	private static final String LOG_TAG = SyncAdapterLogic.class
			.getCanonicalName();

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			MoocResolver resolver, SyncResult syncResult,
			SyncAdapterNetworkInterface network) throws Exception {

		Log.d(LOG_TAG, "onPerformSync(...)");
		// get list (for now, Entire StoryData themselves) of StoryData on
		// server
		ArrayList<StoryData> serverStoryData = network.downloadAllStoryData();
		// get list of StoryData stored locally.
		ArrayList<StoryData> localStoryData = resolver.getAllStoryData();
		// report both local and server list sizes.
		Log.d(LOG_TAG, "Number of local StoryData:" + localStoryData.size());
		Log.d(LOG_TAG, "Number of Server StoryData:" + serverStoryData.size());

		// temp storage for data that needs to be handled.
		ArrayList<StoryData> newStoryDataFromServer = new ArrayList<StoryData>();
		ArrayList<StoryData> newStoryDataToUpload = new ArrayList<StoryData>();

		// compare to lists, and fill storage of StoryData that needs handled.

		for (StoryData storyData : localStoryData) {
			if (storyData.key == null || storyData.key.equals("0")) {
				newStoryDataToUpload.add(storyData);
			}
		}

		Log.d(LOG_TAG,
				"newStoryDataToUpload.size(): " + newStoryDataToUpload.size());

		// bad O(mn) way of checking this... but 'ehh' easy for right now.
		for (StoryData storyDataRemote : serverStoryData) {
			boolean found = false;
			nextLocal: for (StoryData storyDataLocal : localStoryData) {
				// Log.d(LOG_TAG, "Keys:");
				// Log.d(LOG_TAG, "   Local key:" + storyDataLocal.key);
				// Log.d(LOG_TAG, "  Remote key:"+ storyDataRemote.key);
				if (storyDataRemote.key.equals(storyDataLocal.key)) {
					found = true;
					break nextLocal;
				}
			}
			if (found == false) {
				newStoryDataFromServer.add(storyDataRemote);
			}
		}

		Log.d(LOG_TAG, "newStoryDataFromServer.size(): "
				+ newStoryDataFromServer.size());

		// create new stories locally
		Log.d(LOG_TAG, "bulkInsert(...): ");
		resolver.bulkInsertStory(newStoryDataFromServer);

		// upload new stories to server
		Log.d(LOG_TAG, "uploadStoryData(...): ");
		network.uploadStoryData(newStoryDataToUpload
				.toArray(new StoryData[newStoryDataToUpload.size()]));

		for (StoryData storyData : newStoryDataToUpload) {
			if (storyData.needsSyncedLocally == true) {
				Log.d(LOG_TAG, "Needs to be stored locally.");
				resolver.updateStoryWithID(storyData);
			}
		}

	}

}
