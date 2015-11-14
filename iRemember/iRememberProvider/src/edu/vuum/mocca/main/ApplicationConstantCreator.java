package edu.vuum.mocca.main;

import android.app.Application;
import android.content.Context;

public class ApplicationConstantCreator extends Application {

	// this 'Context' is temporarily shared with ApplicationConstants,
	// to allow the HashMap function to reach the Application's resources. 
	public static Context GlobalContext = null;

	/**
	 * Application class is called before any components of the Application,
	 * thereby allows us to create constant static hash-maps from XML resources.
	 * <p>
	 * Or create any other Application-wide bootstrapping
	 */
	@Override
	public void onCreate() {
		// assign the context to 'this' temporarily 
		ApplicationConstantCreator.GlobalContext = this;

		// Touch the activity class.
		// Do things complicated like this to avoid memory leaks.
		@SuppressWarnings("unused")
		// purposely unused.
		ApplicationConstants activity = new ApplicationConstants();
		// Throw it away by not using it.

		// invalidate handle to prevent GC leaks.
		GlobalContext = null;
	}
}