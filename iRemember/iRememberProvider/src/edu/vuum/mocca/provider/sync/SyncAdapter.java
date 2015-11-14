// ST:BODY:start

package edu.vuum.mocca.provider.sync;

import java.io.IOException;

import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import edu.vuum.mocca.authentication.AccountLoginActivity;
import edu.vuum.mocca.main.ApplicationConstants;
import edu.vuum.mocca.network.ServerCommunication;
import edu.vuum.mocca.orm.MoocResolver;
import edu.vuum.mocca.provider.sync.logic.SyncAdapterLogic;
import edu.vuum.mocca.provider.sync.logic.SyncAdapterLogicInterface;
import edu.vuum.mocca.provider.sync.logic.SyncAdapterNetworkInterface;

/**
 * This is the SyncAdapater class that synchronizes the Local ContentProvider
 * with a remote data store.
 * <p>
 * Currently this is only a STUB implementation.
 * 
 * @author Michael A. Walker
 * @Date 2012-11-23
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

	public final static String LOG_TAG = SyncAdapter.class.getCanonicalName();

	private AccountManager mAccountManager;
	MoocResolver resolver;
	SyncAdapterLogicInterface logic;
	SyncAdapterNetworkInterface network;

	String AUTH_TOKEN_TYPE = ApplicationConstants
			.getValue(ApplicationConstants.ACCOUNT_TYPE);

	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		resolver = new MoocResolver(context);
		network = new ServerCommunication();
		logic = new SyncAdapterLogic();
	}

	private SyncResult mSyncResult = null;

	// This is where the actual sync is called by the SyncManager.
	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		Log.v(LOG_TAG, "onPerformSync(...)");

		mSyncResult = syncResult;

		String authToken = "valid";
		try {
			// TODO properly get authToken
			// authToken = mAccountManager.blockingGetAuthToken(account,
			// AUTH_TOKEN_TYPE, false);
			Log.v(LOG_TAG, "got authtoken =>" + authToken);
			logic.onPerformSync(account, extras, authority, resolver,
					syncResult, network);
			Log.v(LOG_TAG, "perform sync finished");
		} catch (Exception e) {
			handleException(authToken, e, mSyncResult);
		}
	}

	// handle any exceptions....
	private void handleException(String authtoken, Exception e,
			SyncResult syncResult) {
		if (e instanceof AuthenticatorException) {
			syncResult.stats.numParseExceptions++;
			Log.e(LOG_TAG, "AuthenticatorException", e);
		} else if (e instanceof OperationCanceledException) {
			Log.e(LOG_TAG, "OperationCanceledExcepion", e);
		} else if (e instanceof IOException) {
			Log.e(LOG_TAG, "IOException", e);
			syncResult.stats.numIoExceptions++;
		} else if (e instanceof AuthenticationException) {
			if (authtoken != null) {
				mAccountManager.invalidateAuthToken(
						AccountLoginActivity.PARAM_ACCOUNT_TYPE, authtoken);
			}
			// The numAuthExceptions require user intervention and are
			// considered hard errors.
			// We automatically get a new hash, so let's make SyncManager retry
			// automatically.
			syncResult.stats.numIoExceptions++;
			Log.e(LOG_TAG, "AuthenticationException", e);
		} else if (e instanceof ParseException) {
			syncResult.stats.numParseExceptions++;
			Log.e(LOG_TAG, "ParseException", e);
		} else if (e instanceof JSONException) {
			syncResult.stats.numParseExceptions++;
			Log.e(LOG_TAG, "JSONException", e);
		}
	}
}
// ST:BODY:end