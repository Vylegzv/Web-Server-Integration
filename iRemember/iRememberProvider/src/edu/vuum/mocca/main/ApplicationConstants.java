package edu.vuum.mocca.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * This class is here because you can not pull in strings from the xml
 * declarations in a static way. I personally think there should be a way to do
 * that, but alas, there is not. Unfortunately, other than creating a static
 * hash-map, there is no way to only have these
 * 
 * @author Michael A. Walker
 * 
 */
public class ApplicationConstants extends Activity {

	/**
	 * Constants for use in pulling out the proper values.
	 */
	public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
	public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
	public static final String AUTHTOKEN_TYPE_READ_ONLY = "AUTHTOKEN_TYPE_READ_ONLY";
	public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "AUTHTOKEN_TYPE_READ_ONLY_LABEL";
	public static final String AUTHTOKEN_TYPE_WRITE_ONLY = "AUTHTOKEN_TYPE_WRITE_ONLY";
	public static final String AUTHTOKEN_TYPE_WRITE_ONLY_LABEL = "AUTHTOKEN_TYPE_WRITE_ONLY_LABEL";
	public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "AUTHTOKEN_TYPE_FULL_ACCESS";
	public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "AUTHTOKEN_TYPE_FULL_ACCESS_LABEL";
	public static final String SERVER_REST_CREATE = "SERVER_REST_CREATE";
	public static final String SERVER_REST_LIST = "SERVER_REST_LIST";

	private static Map<String, String> values = new HashMap<String, String>();
	private static ArrayList<String> valueNames = new ArrayList<String>();

	private static final String LOG_TAG = ApplicationConstants.class
			.getCanonicalName();

	/**
	 * This is kind of a 'trick' to get the values from the XML resource files,
	 * and make a static object, only works because Application class (in this
	 * case 'ApplicationConstantCreator') gets constructed before any other in
	 * the application.
	 */
	static {
		Log.v(LOG_TAG, "Staticly Declared Application"
				+ " Constants from Resources.");

		// make ArrayList of String Values to get.
		valueNames.add(ACCOUNT_TYPE);
		valueNames.add(ACCOUNT_NAME);
		valueNames.add(AUTHTOKEN_TYPE_READ_ONLY);
		valueNames.add(AUTHTOKEN_TYPE_READ_ONLY_LABEL);
		valueNames.add(AUTHTOKEN_TYPE_WRITE_ONLY);
		valueNames.add(AUTHTOKEN_TYPE_WRITE_ONLY_LABEL);
		valueNames.add(AUTHTOKEN_TYPE_FULL_ACCESS);
		valueNames.add(AUTHTOKEN_TYPE_FULL_ACCESS_LABEL);

		// get Application Context to access Resources.
		Context context = ApplicationConstantCreator.GlobalContext;
		Resources res = context.getResources();
		String packageName = context.getPackageName();
		// iterate through ArrayList & get values from Resources for each Value
		for (String name : valueNames) {
			int resId = res.getIdentifier(name, "string", packageName);
			values.put(name, res.getString(resId));
		}

		// clean up Temporary ArrayList to help save memory on GC.
		valueNames.clear();
		valueNames = null;

	}

	static public String getValue(String key) {
		return values.get(key);
	}
}
