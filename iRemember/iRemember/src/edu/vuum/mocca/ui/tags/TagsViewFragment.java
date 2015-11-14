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
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.vanderbilt.mooc.R;
import edu.vuum.mocca.orm.MoocResolver;
import edu.vuum.mocca.orm.TagsData;

public class TagsViewFragment extends Fragment {
    // LOG TAG, handles refactoring changes
    private static final String LOG_TAG = TagsViewFragment.class
            .getCanonicalName();

    private MoocResolver resolver;
    public final static String rowIdentifyerTAG = "index";

    private OnOpenWindowInterface mOpener;

    TagsData tagsData;

        TextView loginIdTV;
    TextView storyIdTV;
    TextView tagTV;
    
    // buttons for edit and delete
    Button editButton;
    Button deleteButton;

    // on-click listener, calls appropriate methods on user click on buttons
    // TODO what pattern is this... ?
    OnClickListener myOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
            case R.id.button_tags_view_to_delete:
                deleteButtonPressed();
                break;
            case R.id.button_tags_view_to_edit:
                editButtonPressed();
                break;
            default:
                break;
            }
        }
    };

    public static TagsViewFragment newInstance(long index) {
        TagsViewFragment f = new TagsViewFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putLong(rowIdentifyerTAG, index);
        f.setArguments(args);

        return f;
    }

    // this fragment was attached to an activity

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOpener = (OnOpenWindowInterface) activity;
            resolver = new MoocResolver(activity);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnOpenWindowListener");
        }
    }

    // this fragment is being created.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    // this fragment is creating its view before it can be modified
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tags_view_fragment, container,
                false);
        container.setBackgroundColor(Color.GRAY);
        return view;
    }

    // this fragment is modifying its view before display
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

                loginIdTV = (TextView) getView().findViewById(
                R.id.tags_view_value_login_id);
        storyIdTV = (TextView) getView().findViewById(
                R.id.tags_view_value_story_id);
        tagTV = (TextView) getView().findViewById(R.id.tags_view_value_tag);
        
        // TODO: thread the lookup
        // set the displayed values to 'defualts' until the screen loads
        // (threading & progress bar not yet implmented)
        // really shouldn't need a progress bar... since the ContentProvider
        // should be local, but will add it in just incase

                loginIdTV.setText("" + 0);
        storyIdTV.setText("" + 0);
        tagTV.setText("" + "");
        
        editButton = (Button) getView().findViewById(
                R.id.button_tags_view_to_edit);
        deleteButton = (Button) getView().findViewById(
                R.id.button_tags_view_to_delete);

        editButton.setOnClickListener(myOnClickListener);
        deleteButton.setOnClickListener(myOnClickListener);

        try {
            setUiToTagsData(getUniqueKey());
        } catch (RemoteException e) {
            Toast.makeText(getActivity(),
                    "Error retrieving information from local data store.",
                    Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error getting Tags data from C.P.");
            // e.printStackTrace();
        }
    }

    public void setUiToTagsData(long getUniqueKey) throws RemoteException {
        tagsData = resolver.getTagsDataViaRowID(getUniqueKey);
        if (tagsData == null) {
            getView().setVisibility(View.GONE);
        } else { // else it just displays empty screen
                        loginIdTV.setText(Long.valueOf(tagsData.loginId).toString());
            storyIdTV.setText(Long.valueOf(tagsData.storyId).toString());
            tagTV.setText(String.valueOf(tagsData.tag).toString());
                    }
    }

    // action to be performed when the edit button is pressed
    private void editButtonPressed() {
        mOpener.openEditTagsFragment(tagsData.KEY_ID);
    }

    // action to be performed when the delete button is pressed
    private void deleteButtonPressed() {
        String message;

        message = getResources().getString(
                R.string.tags_view_deletion_dialog_message);

        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.tags_view_deletion_dialog_title)
                .setMessage(message)
                .setPositiveButton(R.string.tags_view_deletion_dialog_yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                try {
                                    resolver.deleteAllTagsWithRowID(tagsData.KEY_ID);
                                } catch (RemoteException e) {
                                    Log.e(LOG_TAG, "RemoteException Caught => "
                                            + e.getMessage());
                                    e.printStackTrace();
                                }
                                mOpener.openListTagsFragment();
                                if (getResources().getBoolean(R.bool.isTablet) == true) {
                                    mOpener.openViewTagsFragment(-1);
                                } else {
                                    getActivity().finish();
                                }
                            }

                        })
                .setNegativeButton(R.string.tags_view_deletion_dialog_no, null)
                .show();
    }

    public long getUniqueKey() {
        return getArguments().getLong(rowIdentifyerTAG, 0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpener = null;
        resolver = null;
    }
}