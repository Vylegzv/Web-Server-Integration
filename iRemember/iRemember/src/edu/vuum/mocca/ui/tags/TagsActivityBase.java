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

package edu.vuum.mocca.ui.tags;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import edu.vanderbilt.mooc.R;

/**
 * Base class for all Tags activities
 * 
 * @author Michael A. Walker
 * 
 */
public class TagsActivityBase extends FragmentActivity implements
        OnOpenWindowInterface {

    boolean promptOnBackPressed = false;
    TagsListFragment fragment;
    private static final String LOG_TAG = TagsActivityBase.class
            .getCanonicalName();
    boolean mDualPane;

    @Override
    /**
     * Handle when the back button is pressed. Overridden to require
     * confirmation of wanting to exit via back button. 
     * This functionality can easily be removed.
     */
    public void onBackPressed() {
        if (promptOnBackPressed == true) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    finish();
                                }

                            }).setNegativeButton("No", null).show();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Determine if the device this app is running on is a tablet or a phone.
     * 
     * <p>
     * phones generally have less than 600dp (this is the threshold we use, this
     * can be adjusted)
     * 
     * @return boolean truth
     */
    private boolean determineDualPane() {
        if (getResources().getBoolean(R.bool.isTablet) == true) {
            mDualPane = true;
            return true;
        } else {
            mDualPane = false;
            return false;
        }
    }

    /**
     * Logic required to open the appropriate View TagsData Fragment/Activity
     * combination to display properly on the phone or tablet.
     */
    public void openViewTagsFragment(long index) {
        Log.d(LOG_TAG, "openTagsViewFragment(" + index + ")");
        if (determineDualPane()) {

            Fragment test = getSupportFragmentManager().findFragmentById(
                    R.id.details);

            // Log.d(LOG_TAG, "open view class:" + test.getClass());
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            if (test != null && test.getClass() != TagsViewFragment.class) {
                TagsViewFragment details = TagsViewFragment.newInstance(index);

                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.
                ft.replace(R.id.details, details);

            } else {
                // Check what fragment is shown, replace if needed.
                TagsViewFragment details = (TagsViewFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.details);
                if (details == null || details.getUniqueKey() != index) {
                    // Make new fragment to show this selection.
                    details = TagsViewFragment.newInstance(index);

                }
                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.

                ft.replace(R.id.details, details);

            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = newTagsViewIntent(this, index);
            startActivity(intent);
        }
    }

    /**
     * Logic required to open the appropriate Edit TagsData Fragment/Activity
     * combination to display properly on the phone or tablet.
     */
    public void openEditTagsFragment(final long index) {
        Log.d(LOG_TAG, "openEditTagsFragment(" + index + ")");
        if (determineDualPane()) {

            Fragment test = getSupportFragmentManager().findFragmentById(
                    R.id.details);

            // Log.d(LOG_TAG, "open view class:" + test.getClass());
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            if (test != null && test.getClass() != EditTagsFragment.class) {
                EditTagsFragment editor = EditTagsFragment.newInstance(index);

                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.

                ft.replace(R.id.details, editor);

            } else {
                // Check what fragment is shown, replace if needed.
                EditTagsFragment editor = (EditTagsFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.details);
                if (editor == null || editor.getUniqueKey() != index) {
                    // Make new fragment to show this selection.
                    editor = EditTagsFragment.newInstance(index);

                }
                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.

                ft.replace(R.id.details, editor);

            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = newEditTagsIntent(this, index);
            startActivity(intent);
        }
    }

    /**
     * Logic required to open the appropriate Create TagsData Fragment/Activity
     * combination to display properly on the phone or tablet.
     */
    public void openCreateTagsFragment() {
        Log.d(LOG_TAG, "openCreateTagsFragment");
        if (determineDualPane()) {

            Fragment test = getSupportFragmentManager().findFragmentById(
                    R.id.details);

            // Log.d(LOG_TAG, "open view class:" + test.getClass());
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            if (test != null && test.getClass() != CreateTagsFragment.class) {
                CreateTagsFragment details = CreateTagsFragment.newInstance();

                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.

                ft.replace(R.id.details, details);

            } else {
                // Check what fragment is shown, replace if needed.
                CreateTagsFragment details = (CreateTagsFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.details);
                if (details == null) {
                    // Make new fragment to show this selection.
                    details = CreateTagsFragment.newInstance();

                }
                // Execute a transaction, replacing any existing
                // fragment with this one inside the frame.

                ft.replace(R.id.details, details);

            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = newCreateTagsIntent(this);
            startActivity(intent);
        }
    }

    @Override
    /**
     * Logic required to open the appropriate List TagsData Fragment/Activity
     * combination to display properly on the phone or tablet.
     */
    public void openListTagsFragment() {
        Log.d(LOG_TAG, "openCreateTagsFragment");
        if (determineDualPane()) {
            // already displayed
            Fragment test = getSupportFragmentManager().findFragmentByTag(
                    "imageFragmentTag");
            if (test != null) {
                TagsListFragment t = (TagsListFragment) test;
                // TODO: FIX THIS... resolver?
                t.updateTagsData();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = newListTagsIntent(this);
            startActivity(intent);
        }
    }

    /*************************************************************************/
    /*
     * Create Intents for Intent Launching
     */
    /*************************************************************************/

    public static Intent newTagsViewIntent(Activity activity, long index) {
        Intent intent = new Intent();
        intent.setClass(activity, ViewTagsActivity.class);
        intent.putExtra(TagsViewFragment.rowIdentifyerTAG, index);
        return intent;
    }

    public static Intent newEditTagsIntent(Activity activity, long index) {
        Intent intent = new Intent();
        intent.setClass(activity, EditTagsActivity.class);
        intent.putExtra(EditTagsFragment.rowIdentifyerTAG, index);
        return intent;
    }

    public static Intent newListTagsIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, TagsListActivity.class);
        return intent;
    }

    public static Intent newCreateTagsIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, CreateTagsActivity.class);
        return intent;
    }
}