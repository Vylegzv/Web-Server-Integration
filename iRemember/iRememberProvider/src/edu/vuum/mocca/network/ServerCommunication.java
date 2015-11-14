package edu.vuum.mocca.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.util.Log;
import edu.vuum.mocca.main.ApplicationConstants;
import edu.vuum.mocca.orm.StoryData;
import edu.vuum.mocca.provider.sync.logic.SyncAdapterNetworkInterface;

public class ServerCommunication implements SyncAdapterNetworkInterface {

	private final static String LOG_TAG = ServerCommunication.class
			.getCanonicalName();

	private static String SERVER_REST_LIST;
	private static String SERVER_REST_CREATE;

	public ServerCommunication() {
		SERVER_REST_LIST = "http://2.test-rest-ful-api.appspot.com/v1/stories/";
		SERVER_REST_CREATE = ApplicationConstants
				.getValue(ApplicationConstants.SERVER_REST_CREATE);
	}

	@Override
	public void uploadStoryData(StoryData storyData) throws Exception {
		Log.v(LOG_TAG, "uploadStoryData(StoryData)");
		uploadStoryData(new StoryData[] { storyData });
	}

	@Override
	public void uploadStoryData(StoryData[] storyDataList) throws Exception {
		Log.v(LOG_TAG, "uploadStoryData(StoryData[])");

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(SERVER_REST_CREATE);
		// responses to be returned
		ArrayList<String> responses = new ArrayList<String>();

		for (StoryData storyData : storyDataList) {
			HttpResponse response = null;
			try {
				httpPost.setEntity(storyData.getUrlEncodedFormEntity());

				response = httpclient.execute(httpPost);
				String responseText = EntityUtils
						.toString(response.getEntity());

				JSONObject jsonObj = new JSONObject(responseText);
				String key = (String) jsonObj.get("key");
				storyData.key = key;
				storyData.needsSyncedLocally = true;

				Log.v(LOG_TAG, "http response:"
						+ response.getStatusLine().toString());
				responses.add(response.getStatusLine().toString());
				response.getEntity().consumeContent();

			} catch (ClientProtocolException ex) {
				handleException(ex);
			}
		}
	}

	// handle any exceptions....
	private void handleException(Exception ex) {
		if (ex instanceof UnsupportedEncodingException) {
			Log.e(LOG_TAG, "UnsupportedEncodingException", ex);
		} else if (ex instanceof ClientProtocolException) {
			Log.e(LOG_TAG, "ClientProtocolException", ex);
		} else if (ex instanceof IOException) {
			Log.e(LOG_TAG, "IOException", ex);
		} else if (ex instanceof JSONException) {
			Log.e(LOG_TAG, "JSONException", ex);
		} else if (ex instanceof ParseException) {
			Log.e(LOG_TAG, "ParseException", ex);
		} else if (ex instanceof Exception) {
			Log.e(LOG_TAG, "Exception", ex);
		}
	}

	@Override
	public ArrayList<StoryData> downloadAllStoryData() throws Exception {
		Log.v(LOG_TAG, "downloadAllStoryData()");
		return downloadAllStoryData(null, null);
	}

	@Override
	public ArrayList<StoryData> downloadAllStoryData(String fieldName,
			String filter) throws Exception {
		Log.v(LOG_TAG, "downloadAllStoryData(String,String)");

		ArrayList<StoryData> rValue = new ArrayList<StoryData>();
		String response = "";
		String url = SERVER_REST_LIST;

		Log.v(LOG_TAG, "1");
		if (fieldName != null && filter != null) {
			Log.v(LOG_TAG, "Adding Filter Text");
			url += "&fieldname=" + fieldName + "&filter=" + filter;
		}
		Log.v(LOG_TAG, "2" + url);

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		Log.v(LOG_TAG, "3" + SERVER_REST_CREATE);

		try {
			Log.v(LOG_TAG, "4");
			HttpResponse execute = client.execute(httpGet);
			InputStream content = execute.getEntity().getContent();

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					content));
			Log.v(LOG_TAG, "5");
			String s = "";
			while ((s = buffer.readLine()) != null) {
				response += s;
			}
			Log.v(LOG_TAG, "6");

			JSONArray jsonArray = new JSONArray(response);
			long jsonArrayLength = jsonArray.length();
			Log.v(LOG_TAG, "json Array Length: " + jsonArrayLength);
			for (int i = 0; i < jsonArray.length(); ++i) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				StoryData temp = StoryData.createObjectFromJSON(jsonObject);
				if (temp != null) {
					rValue.add(temp);
					Log.v(LOG_TAG, "StoryData.key: " + temp.key);
				}
			}
		} catch (Exception ex) {
			handleException(ex);
		}
		Log.d(LOG_TAG, "rValue Size: " + rValue.size());
		return rValue;
	}

	@Override
	public void updateStoryData(StoryData storyData) throws Exception {
		Log.v(LOG_TAG, "updateStoryData(StoryData)");
		// TODO Auto-generated method stub
	}

	@Override
	public void updateStoryData(ArrayList<StoryData> storyDataList)
			throws Exception {
		Log.v(LOG_TAG, "updateStoryData(ArrayList<StoryData>)");
		// TODO Auto-generated method stub
	}

	@Override
	public void downloadStoryData(String UUIDString) throws Exception {
		Log.v(LOG_TAG, "downloadStoryData(String)");
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<StoryData> getListOfStories(long latitude, long longitude,
			long radiusInMeter) throws Exception {
		Log.v(LOG_TAG, "getListOfStories(long,long,long)");
		ArrayList<StoryData> rValue = new ArrayList<StoryData>();
		// TODO Auto-generated method stub
		return rValue;
	}

	@Override
	public ArrayList<StoryData> getListOfStories(long latitude, long longitude,
			long radiusInMeter, String filter) throws Exception {
		Log.v(LOG_TAG, "getListOfStories(long,long,long,String)");
		ArrayList<StoryData> rValue = new ArrayList<StoryData>();
		// TODO Auto-generated method stub
		return rValue;
	}

}
