package edu.vuum.mocca.provider.sync.logic;

import android.accounts.Account;
import android.content.SyncResult;
import android.os.Bundle;
import edu.vuum.mocca.orm.MoocResolver;

public interface SyncAdapterLogicInterface {

	abstract public void onPerformSync(Account account, Bundle extras,
			String authority, MoocResolver resolver, SyncResult syncResult,
			SyncAdapterNetworkInterface network) throws Exception;

}
