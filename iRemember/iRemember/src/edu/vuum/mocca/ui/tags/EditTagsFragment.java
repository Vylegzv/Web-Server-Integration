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
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.vanderbilt.mooc.R;
import edu.vuum.mocca.orm.MoocResolver;
import edu.vuum.mocca.orm.TagsData;

public class EditTagsFragment extends Fragment {

    final static public String LOG_TAG = EditTagsFragment.class
            .getCanonicalName();
    // variable for passing around row index
    final static public String rowIdentifyerTAG = "index";

        EditText loginIdET;
    EditText storyIdET;
    EditText tagET;
    
    // Button(s) used
    Button saveButton;
    Button resetButton;
    Button cancelButton;

    // parent Activity
    OnOpenWindowInterface mOpener;
    // custom ContentResolver wrapper.
    MoocResolver resolver;

    // listener to button presses.
    // TODO determine/label pattern.
    OnClickListener myOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.tags_edit_button_save:
                doSaveButtonClick();
                break;
            case R.id.tags_edit_button_reset:
                doResetButtonClick();
                break;
            case R.id.tags_edit_button_cancel:
                doCancelButtonClick();
                break;
            default:
                break;
            }
        }
    };

    public static EditTagsFragment newInstance(long index) {
        EditTagsFragment f = new EditTagsFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putLong(rowIdentifyerTAG, index);
        f.setArguments(args);
        return f;
    }

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

    @Override
    public void onDetach() {
        mOpener = null;
        resolver = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get the Buttons
        saveButton = (Button) getView()
                .findViewById(R.id.tags_edit_button_save);
        resetButton = (Button) getView().findViewById(
                R.id.tags_edit_button_reset);
        cancelButton = (Button) getView().findViewById(
                R.id.tags_edit_button_cancel);

        // Get the EditTexts
                loginIdET = (EditText) getView().findViewById(R.id.tags_edit_login_id);
        storyIdET = (EditText) getView().findViewById(R.id.tags_edit_story_id);
        tagET = (EditText) getView().findViewById(R.id.tags_edit_tag);
        
        // setup the onClick callback methods
        saveButton.setOnClickListener(myOnClickListener);
        resetButton.setOnClickListener(myOnClickListener);
        cancelButton.setOnClickListener(myOnClickListener);
        // set the EditTexts to this Tags's Values
        setValuesToDefault();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tags_edit_fragment, container,
                false);
        container.setBackgroundColor(Color.GRAY);
        return view;
    }

    public void doResetButtonClick() {
        setValuesToDefault();
    }

    public void doSaveButtonClick() {
        Toast.makeText(getActivity(), "Updated.", Toast.LENGTH_SHORT).show();
        TagsData location = makeTagsDataFromUI();
        if (location != null) {
            try {
                resolver.updateTagsWithID(location);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
        } else {
            return;
        }
        if (getResources().getBoolean(R.bool.isTablet) == true) {
            mOpener.openViewTagsFragment(getUniqueKey());
        } else {
            getActivity().finish(); // same as hitting 'back' button
        }
    }

    public TagsData makeTagsDataFromUI() {

        // local Editables
                Editable loginIdEditable = loginIdET.getText();
        Editable storyIdEditable = storyIdET.getText();
        Editable tagEditable = tagET.getText();
        
                long loginId = 0;
        long storyId = 0;
        String tag = "";
        
        // pull values from Editables
                loginId = Long.valueOf(loginIdEditable.toString());
        storyId = Long.valueOf(storyIdEditable.toString());
        tag = String.valueOf(tagEditable.toString());
        
        // return new TagsData object with
        return new TagsData(getUniqueKey(),
                        loginId, storyId, tag
                );

    }

    public void doCancelButtonClick() {
        if (getResources().getBoolean(R.bool.isTablet) == true) {
            // put
            mOpener.openViewTagsFragment(getUniqueKey());
        } else {
            getActivity().finish(); // same as hitting 'back' button
        }

    }

    public boolean setValuesToDefault() {

        TagsData tagsData;
        try {
            tagsData = resolver.getTagsDataViaRowID(getUniqueKey());
        } catch (RemoteException e) {
            Log.d(LOG_TAG, "" + e.getMessage());
            e.printStackTrace();
            return false;
        }

        if (tagsData != null) {
            Log.d(LOG_TAG, "setValuesToDefualt :" + tagsData.toString());
            // set the EditTexts to the current values
                        loginIdET.setText(Long.valueOf(tagsData.loginId).toString());
            storyIdET.setText(Long.valueOf(tagsData.storyId).toString());
            tagET.setText(String.valueOf(tagsData.tag).toString());
                        return true;
        }
        return false;
    }

    public long getUniqueKey() {
        return getArguments().getLong(rowIdentifyerTAG, 0);
    }

}
