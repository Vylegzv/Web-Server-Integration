package com.example.serverrequest;

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
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText mEditText;
	TextView mTextView;
	TextView storyNumberTextView;
	String mUrl = "http://2.test-rest-ful-api.appspot.com/v1/add";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// initializes the EditText
		mEditText = (EditText) findViewById(R.id.pingLocation);
		mEditText.setText(mUrl);
		mTextView = (TextView) findViewById(R.id.jsonDisplay);
		storyNumberTextView = (TextView) findViewById(R.id.storyNumDisplay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void getStory(View view) {

		Log.d("test", "getStory clicked");
		mUrl = mEditText.getText().toString();
		new downloadTask().execute();
	}

	public void getAllStories(View view) {
		Log.d("test", "getStory clicked");
		mUrl = "http://test-rest-ful-api.appspot.com/v1/stories/";
		new downloadTask().execute();
	}

	class downloadTask extends AsyncTask<Uri, Void, String> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(Uri... params) {
			String result = "error";

			HttpClient httpclient = new DefaultHttpClient();

			HttpGet httpget = new HttpGet(mUrl);

			// Execute the request
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

		@Override
		protected void onPostExecute(String result) {
			mTextView.setText(result);

		}
	}

	private static String convertToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public void store(View view) {
		Log.d("test", "Store clicked");
		mUrl = mEditText.getText().toString();
		new uploadTask().execute();
	}

	private static ArrayList<NameValuePair> makeList() {

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("loginId", "1"));
		params.add(new BasicNameValuePair("storyId", "1"));
		params.add(new BasicNameValuePair("title", "title"));
		params.add(new BasicNameValuePair("body", "body"));
		params.add(new BasicNameValuePair("audioLink", "audioLink"));
		params.add(new BasicNameValuePair("videoLink", "videoLink"));
		params.add(new BasicNameValuePair("imageName", "imageName"));
		params.add(new BasicNameValuePair("tags", "tags"));
		params.add(new BasicNameValuePair("creationTime", "1"));
		params.add(new BasicNameValuePair("storyTime", "1"));
		params.add(new BasicNameValuePair("latitude", "0"));
		params.add(new BasicNameValuePair("longitude", "0"));
		return params;
	}

	class uploadTask extends AsyncTask<Uri, Void, String> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(Uri... params) {

			Log.d("test", "STARTED UPLOADING");
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(mUrl);

			ArrayList<NameValuePair> myList = makeList();

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(myList));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpResponse response = null;

			try {
				response = httpclient.execute(httpPost);
			} catch (ClientProtocolException e) {
				Log.e("Clientprotocolexception",e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("IOException",e.toString());
				e.printStackTrace();
			}
			return response.toString();
		}

		@Override
		protected void onPostExecute(String response) {
			Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
					.show();
		}
	}
}
