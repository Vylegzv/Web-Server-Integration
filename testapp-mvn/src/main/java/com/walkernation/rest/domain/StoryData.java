/*
The iRemember source code (henceforth referred to as "iRemember") is
copyrighted by Mike Walker, Adam Porter, Doug Schmidt, and Jules White
at Vanderbilt University and the University of Maryland, Copyright (c)
2014, all rights reserved.  Since iRemember is open-source, freely
available software, you are free to use, modify, copy, and
distribute--perpetually and irrevocably--the source code and object code
produced from the source, as well as copy and distribute modified
versions of this software. You must, however, include this copyright
statement along with any code built using iRemember that you release. No
copyright statement needs to be provided if you just ship binary
executables of your software products.

You can use iRemember software in commercial and/or binary software
releases and are under no obligation to redistribute any of your source
code that is built using the software. Note, however, that you may not
misappropriate the iRemember code, such as copyrighting it yourself or
claiming authorship of the iRemember software code, in a way that will
prevent the software from being distributed freely using an open-source
development model. You needn't inform anyone that you're using iRemember
software in your software, though we encourage you to let us know so we
can promote your project in our success stories.

iRemember is provided as is with no warranties of any kind, including
the warranties of design, merchantability, and fitness for a particular
purpose, noninfringement, or arising from a course of dealing, usage or
trade practice.  Vanderbilt University and University of Maryland, their
employees, and students shall have no liability with respect to the
infringement of copyrights, trade secrets or any patents by DOC software
or any part thereof.  Moreover, in no event will Vanderbilt University,
University of Maryland, their employees, or students be liable for any
lost revenue or profits or other special, indirect and consequential
damages.

iRemember is provided with no support and without any obligation on the
part of Vanderbilt University and University of Maryland, their
employees, or students to assist in its use, correction, modification,
or enhancement.

The names Vanderbilt University and University of Maryland may not be
used to endorse or promote products or services derived from this source
without express written permission from Vanderbilt University or
University of Maryland. This license grants no permission to call
products or services derived from the iRemember source, nor does it
grant permission for the name Vanderbilt University or
University of Maryland to appear in their names.
 */

package com.walkernation.rest.domain;

import java.util.List;
import java.util.UUID;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.beoui.geocell.annotations.Geocells;
import com.beoui.geocell.annotations.Latitude;
import com.beoui.geocell.annotations.Longitude;

/**
 * Custom ORM container class, for Story Data.
 * <p>
 * This class is meant as a helper class for those working with the
 * ContentProvider. The use of this class is completely optional.
 * <p>
 * ORM = Object Relational Mapping
 * http://en.wikipedia.org/wiki/Object-relational_mapping
 * <p>
 * This class is a simple one-off POJO class with some simple ORM additions that
 * allow for conversion between the incompatible types of the POJO java classes,
 * the 'ContentValues', and the 'Cursor' classes from the use with
 * ContentProviders.
 * 
 * @author Michael A. Walker, Violetta Vylegzhanina
 * 
 */

// @SuppressWarnings("deprecation")
@PersistenceCapable
public class StoryData {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	// public String uuidString;
	private Key key;
	// @Persistent
	// public final long KEY_ID;
	@Persistent
	private long loginId;
	@Persistent
	private long storyId;
	@Persistent
	private String title;
	@Persistent
	private Text body;
	@Persistent
	public String audioLink;
	@Persistent
	public String videoLink;
	//@Persistent
	//public String imageName;
	//@Persistent
	//public String imageLink;
	@Persistent(defaultFetchGroup = "true", dependent = "true") 
	private Image image;
	
	@Persistent(defaultFetchGroup = "true", dependent = "true") 
	private Video video;
	
	@Persistent
	private String tags;
	@Persistent
	private long creationTime;
	@Persistent
	private long storyTime;
	@Latitude
	@Persistent
	private double latitude;
	@Longitude
	@Persistent
	private double longitude;

	@Persistent
	public String href;

	@Geocells
	@Persistent
	private List<String> geocells;

	/**
	 * Constructor WITHOUT _id, this creates a new object for insertion into the
	 * ContentProvider
	 * 
	 * @param loginId
	 * @param storyId
	 * @param title
	 * @param body
	 * @param audioLink
	 * @param videoLink
	 * @param imageName
	 * @param imageLink
	 * @param tags
	 * @param creationTime
	 * @param storyTime
	 * @param latitude
	 * @param longitude
	 */
	public StoryData(long loginId, long storyId, String title, Text body,
			String audioLink, String videoLink, String tags, long creationTime,
			long storyTime, double latitude, double longitude) {
		// this.KEY_ID = -1;
		// UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		// this.uuidString = uuid.toString();
		this.loginId = loginId;
		this.storyId = storyId;
		this.title = title;
		this.body = body;
		this.audioLink = audioLink;
		this.videoLink = videoLink;
		this.tags = tags;
		this.creationTime = creationTime;
		this.storyTime = storyTime;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Constructor WITH _id, this creates a new object for use when pulling
	 * already existing object's information from the ContentProvider
	 * 
	 * @param KEY_ID
	 * @param loginId
	 * @param storyId
	 * @param title
	 * @param body
	 * @param audioLink
	 * @param videoLink
	 * @param imageName
	 * @param imageLink
	 * @param tags
	 * @param creationTime
	 * @param storyTime
	 * @param latitude
	 * @param longitude
	 */
	public StoryData(long KEY_ID, long loginId, long storyId, String title,
			Text body, String audioLink, String videoLink, String tags, long creationTime, long storyTime,
			double latitude, double longitude) {
		// this.KEY_ID = KEY_ID;
		this.loginId = loginId;
		this.storyId = storyId;
		this.title = title;
		this.body = body;
		this.audioLink = audioLink;
		this.videoLink = videoLink;
		this.tags = tags;
		this.creationTime = creationTime;
		this.storyTime = storyTime;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public StoryData(Key key, long KEY_ID, long loginId, long storyId,
			String title, Text body, String audioLink, String videoLink, String tags, long creationTime,
			long storyTime, double latitude, double longitude) {
		this.key = key;
		// this.KEY_ID = KEY_ID;
		this.loginId = loginId;
		this.storyId = storyId;
		this.title = title;
		this.body = body;
		this.audioLink = audioLink;
		this.videoLink = videoLink;
		this.tags = tags;
		this.creationTime = creationTime;
		this.storyTime = storyTime;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/*
	 * public StoryData(UUID uuid, long KEY_ID, long loginId, long storyId,
	 * String title, Text body, String audioLink, String videoLink, String
	 * imageName, String imageLink, String tags, long creationTime, long
	 * storyTime, double latitude, double longitude) { this.uuidString =
	 * uuid.toString(); // otherwise it won't fit in long // this.KEY_ID =
	 * KEY_ID; this.loginId = loginId; this.storyId = storyId; this.title =
	 * title; this.body = body; this.audioLink = audioLink; this.videoLink =
	 * videoLink; this.imageName = imageName; this.imageLink = imageLink;
	 * this.tags = tags; this.creationTime = creationTime; this.storyTime =
	 * storyTime; this.latitude = latitude; this.longitude = longitude; }
	 */

	/**
	 * Override of the toString() method, for testing/logging
	 */
	@Override
	public String toString() {
		return " loginId: " + loginId + " storyId: " + storyId + " title: "
				+ title + " body: " + body + " audioLink: " + audioLink
				+ " videoLink: " + videoLink + " imageName: " + image.getName() 
				+ " tags: " + tags + " creationTime: " + creationTime + " storyTime: " 
				+ storyTime + " latitude: " + latitude + " longitude: " + longitude;
	}

	/**
	 * Clone this object into a new StoryData
	 */
	public StoryData clone() {
		return new StoryData(loginId, storyId, title, body, audioLink,
				videoLink, tags, creationTime, storyTime,
				latitude, longitude);
	}

	static public StoryData fromStoryData(final StoryData data) {
		return data;
	}
	
	public void setTitle(String t){
		title = t;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setBody(Text b){
		body = b;
	}
	
	public Text getBody(){
		return body;
	}
	
	public void setTags(String t){
		tags = t;
	}
	
	public String getTags(){
		return tags;
	}
	
	public void setCreationTime(long c){
		creationTime = c;
	}
	
	public long getCreationTime(){
		return creationTime;
	}
	
	public void setStoryTime(long s){
		storyTime = s;
	}
	
	public long getStoryTime(){
		return storyTime;
	}
	
	public void setLatitude(double l){
		latitude = l;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public void setLongitude(double l){
		longitude = l;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public void setImage(Image i){
		image = i;
	}
	
	public void setVideo(Video v){
		video = v;
	}
	
	public Image getImage(){
		return image;
	}
	
	public Video getVideo(){
		return video;
	}

	@JsonIgnore
	public Key getKey() {
		return key;
	}
	
	@JsonIgnore
	public void setKey(Key key) {
		this.key = key;
	}

	public String getId() {
		return KeyFactory.keyToString(getKey());
	}

	public boolean canBeDeleted = true;

	public boolean canBeDeleted() {
		return canBeDeleted;
	}

	public void setCanBeDeleted(boolean canBeDeleted) {
		this.canBeDeleted = canBeDeleted;
	}
	/*
	 * @Override public List<String> getGeocells() { return geocells; }
	 * 
	 * @Override public String getKeyString() { return getKey(); }
	 * 
	 * @Override public Point getLocation() { return new Point(latitude,
	 * longitude); }
	 */
}