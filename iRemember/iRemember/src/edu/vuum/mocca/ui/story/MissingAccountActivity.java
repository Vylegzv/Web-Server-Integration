package edu.vuum.mocca.ui.story;

import edu.vuum.mocca.ui.story.StoryListActivity;
import edu.vanderbilt.mooc.R;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MissingAccountActivity extends Activity {
	EditText usernameText;
	EditText passwordText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_missing_account);
		usernameText = (EditText) findViewById(R.id.usernameEnter);
		passwordText = (EditText) findViewById(R.id.passwordEnter);

		Toast.makeText(this, "Please create an account", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.missing_account, menu);
		return true;
	}

	public void registerAccount(View view) {

		// verifies that the username and password are the same and not NULL
		if (usernameText.getText().toString().length() > 0
				&& passwordText.getText().toString().length() > 0
				&& usernameText.getText().toString()
						.equals(passwordText.getText().toString())) {

			// create the new account if criteria is met
			Account newAccount = new Account(usernameText.getText().toString(),
					getResources().getString(R.string.ACCOUNT_TYPE));
			AccountManager accountManager = AccountManager
					.get(getApplicationContext());
			if (accountManager.addAccountExplicitly(newAccount, null, null)) {
				Toast.makeText(this, "Account Created", Toast.LENGTH_LONG)
						.show();

				Intent intent = new Intent(MissingAccountActivity.this,
						StoryListActivity.class);
				startActivity(intent);

				/******************* set preferences here ********************/
				/***********************************************************/
				/***********************************************************/
				/***********************************************************/
			}
			// if the username and password don't match it prompts to enter
			// a valid combination
		} else {
			Toast.makeText(this, "Username and Password must match",
					Toast.LENGTH_LONG).show();
		}

	}

}
