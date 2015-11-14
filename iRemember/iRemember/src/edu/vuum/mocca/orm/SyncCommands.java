package edu.vuum.mocca.orm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import edu.vuum.mocca.orm.StoryData;

public class SyncCommands {

	public String Read(String url) {

		String result = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;

		Log.d("test", "about to execute");
		try {
			response = httpclient.execute(httpget);

			Log.d("test", "finished executing");
			Log.d("test", response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();
			Log.d("test", entity.toString());

			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertToString(instream);
				Log.d("test", "the result is: " + result);

				instream.close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("test", "ClientProtocolException error: " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("test", "IOException error: " + e.getMessage());
		} catch (Exception e) {
			Log.e("test", "Exception error: " + e.getMessage());
		}
		return result;

	}

	public String ReadAll(String url) {

		String result = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;

		try {
			Log.d("test", "about to execute");
			response = httpclient.execute(httpget);

			Log.d("test", "finished executing");
			Log.d("test", response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();
			Log.d("test", entity.toString());

			if (entity != null) {
				InputStream instream = entity.getContent();
				result = convertToString(instream);
				Log.d("test", "the result is: " + result);

				instream.close();
			}
		} catch (Exception e) {
		}
		return result;
	}

	public HttpResponse Store(StoryData newData, String serverLocation) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(serverLocation);

		ArrayList<NameValuePair> params = makeList(newData);

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpResponse response = null;

		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static ArrayList<NameValuePair> makeList(StoryData newData) {

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("loginId", String
				.valueOf(newData.loginId)));
		params.add(new BasicNameValuePair("storyId", String
				.valueOf(newData.storyId)));
		params.add(new BasicNameValuePair("title", String
				.valueOf(newData.title)));
		params.add(new BasicNameValuePair("body", String.valueOf(newData.body)));
		params.add(new BasicNameValuePair("audioLink", String
				.valueOf(newData.audioLink)));
		params.add(new BasicNameValuePair("videoLink", String
				.valueOf(newData.videoLink)));
		params.add(new BasicNameValuePair("imageName", String
				.valueOf(newData.imageName)));
		params.add(new BasicNameValuePair("tags", String.valueOf(newData.tags)));
		params.add(new BasicNameValuePair("creationTime", String
				.valueOf(newData.creationTime)));
		params.add(new BasicNameValuePair("storyTime", String
				.valueOf(newData.storyTime)));
		params.add(new BasicNameValuePair("latitude", String
				.valueOf(newData.longitude)));
		params.add(new BasicNameValuePair("longitude", String
				.valueOf(newData.longitude)));
		return params;
	}

	private static String convertToString(InputStream inputStream) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder stringBuilder = new StringBuilder();

		String currentLine = null;
		try {
			while ((currentLine = reader.readLine()) != null) {
				stringBuilder.append(currentLine + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}

}
