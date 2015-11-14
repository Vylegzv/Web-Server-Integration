package edu.vuum.mocca.orm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class ServerCommunication {

	public final static String LOG_TAG = ServerCommunication.class
			.getCanonicalName();

	public static String WEBSITE = "http://2.test-rest-ful-api.appspot.com/v1/stories/";

	public ArrayList<StoryData> getListOfStories() throws Exception {

		Log.d(LOG_TAG, "IN GET LIST");
		ArrayList<StoryData> rValue = new ArrayList<StoryData>();

		DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute(new String[] { WEBSITE });

		// SyncCommands commands = new SyncCommands();
		//
		// String result = commands.Read(WEBSITE
		// + "000b5201-70c0-ccaa-000b-520170c0cd17");

		//Log.d(LOG_TAG, stringResult);

		return rValue;

	}

	private String stringResult = "blah";

	private class DownloadWebPageTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			
		}

		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			
			try {
				JSONArray jsonArray = new JSONArray(result);
				long jsonArrayLength = jsonArray.length();
				JSONObject jsonObject = (JSONObject) jsonArray.get(0);
				Log.d(LOG_TAG, "jsonArrayLength => " + jsonObject.toString());
				StoryData temp = StoryData.createObjectFromJSON(jsonObject);
				Log.d(LOG_TAG, "jsonArrayLength => " + jsonArrayLength + " temp StoryData=> " + temp.toString());
			} catch (JSONException e) {
				Log.e(LOG_TAG, "JSON parsing failed: " + e.getMessage());
			}
			stringResult = result;
		}
	}

	public ArrayList<StoryData> getListOfStories(long latitude, long longitude,
			long radiusInMeter, String filter) throws Exception {

		ArrayList<StoryData> rValue = new ArrayList<StoryData>();

		// apply filter
		if (filter != null) {

		}

		SyncCommands commands = new SyncCommands();

		String result = commands.Read(WEBSITE
				+ "000b5201-70c0-ccaa-000b-520170c0cd17");

		Log.d(LOG_TAG, result);

		// handle lat/lon/radius

		// query the web-server

		// parse reply and store in rValue

		return rValue;
	}

	public boolean uploadStoryData(StoryData storyData) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean uploadStoryData(ArrayList<StoryData> storyDataList)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateStoryData(StoryData storyData) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateStoryData(ArrayList<StoryData> storyDataList)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public StoryData downloadStoryData(String UUIDString) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public StoryData downloadStoryData(UUID uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<StoryData> downloadAllStoryData() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<StoryData> downloadAllStoryData(String fieldName,
			String filter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String userSignUp(String name, String email, String pass,
			String authType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String userSignIn(String user, String pass, String authType)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<StoryData> getListOfStories(long latitude, long longitude,
			long radiusInMeter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
