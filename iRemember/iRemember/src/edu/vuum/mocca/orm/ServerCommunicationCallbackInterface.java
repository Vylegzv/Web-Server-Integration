package edu.vuum.mocca.orm;

import java.util.ArrayList;

public interface ServerCommunicationCallbackInterface {

	public void onUploadComplete(ArrayList<String> responses);

	public void onDownloadComplete(ArrayList<StoryData> responses);
	
	public void onUserLogin(String authToken);
}
