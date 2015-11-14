package edu.vuum.mocca.provider.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Service that creates a singleton instance of the SyncAdapter.
 * <p>
 * This is the public Service that is listed in the Manifest.
 * 
 * @author Michael A. Walker
 */
public class SyncAdapterService extends Service {

	private static final String LOG_TAG = SyncAdapterService.class
			.getCanonicalName();

	private static final Object syncAdapterLock = new Object();
	private static SyncAdapter syncAdapter = null;

	/**
	 * Thread-safe Singleton Pattern creator of the SyncAdapter instance
	 */
	@Override
	public void onCreate() {
		Log.i(LOG_TAG, LOG_TAG + " created");
		synchronized (syncAdapterLock) {
			if (syncAdapter == null)
				syncAdapter = new SyncAdapter(getApplicationContext(), true);
		}
	}

	/**
	 * Logging-only destructor.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(LOG_TAG, LOG_TAG + " destroyed");
	}

	/**
	 * Return IPC Binder of the SyncAdapter 
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.i(LOG_TAG, LOG_TAG + " bound");
		return syncAdapter.getSyncAdapterBinder();
	}
}
