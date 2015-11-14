package com.walkernation.rest.core.events;

import com.walkernation.rest.core.events.base.RequestReadEvent;

public class RequestListStoryDataEvent extends RequestReadEvent {
	private final double latitude;
	private final double longitude;
	private final double distance;

	public RequestListStoryDataEvent(double latitude, double longitude,
			double distance) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getDistance() {
		return distance;
	}

}
