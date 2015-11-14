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
import edu.vanderbilt.mooc.R;
import edu.vuum.mocca.orm.MoocResolver;
import edu.vuum.mocca.orm.TagsData;

public class CreateTagsFragment extends Fragment {

    public final static String LOG_TAG = CreateTagsFragment.class
            .getCanonicalName();

    // EditText(s) used

        EditText loginIdET;
    EditText storyIdET;
    EditText tagET;
    
    Button buttonCreate;
    Button buttonClear;
    Button buttonCancel;

    // int index;
    OnOpenWindowInterface mOpener;
    MoocResolver resolver;

    public final static String LOCATION = "tags";

    // listener to button presses.
    // TODO determine/label pattern.
    OnClickListener myOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.tags_create_button_save:
                doSaveButtonClick();
                break;
            case R.id.tags_create_button_reset:
                doResetButtonClick();
                break;
            case R.id.tags_create_button_cancel:
                doCancelButtonClick();
                break;
            default:
                break;
            }
        }
    };

    void doSaveButtonClick() {
    }

    void doCancelButtonClick() {
    }

    void doResetButtonClick() {
    }

    public static CreateTagsFragment newInstance() {
        CreateTagsFragment f = new CreateTagsFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get the EditTexts
                loginIdET = (EditText) getView().findViewById(
                R.id.tags_create_value_login_id);
        storyIdET = (EditText) getView().findViewById(
                R.id.tags_create_value_story_id);
        tagET = (EditText) getView().findViewById(R.id.tags_create_value_tag);
        
        buttonClear = (Button) getView().findViewById(
                R.id.tags_create_button_reset);
        buttonCancel = (Button) getView().findViewById(
                R.id.tags_create_button_cancel);
        buttonCreate = (Button) getView().findViewById(
                R.id.tags_create_button_save);

        buttonClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                                loginIdET.setText("" + 0);
                storyIdET.setText("" + 0);
                tagET.setText("" + "");
                            }
        });

        buttonCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getResources().getBoolean(R.bool.isTablet) == true) {
                    // put
                    mOpener.openViewTagsFragment(0);
                } else {
                    getActivity().finish(); // same as hitting 'back' button
                }
            }
        });

        buttonCreate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // local Editables
                                Editable loginIdCreateable = loginIdET.getText();
                Editable storyIdCreateable = storyIdET.getText();
                Editable tagCreateable = tagET.getText();
                
                                long loginId = 0;
                long storyId = 0;
                String tag = "";
                
                // pull values from Editables
                                loginId = Long.valueOf(loginIdCreateable.toString());
                storyId = Long.valueOf(storyIdCreateable.toString());
                tag = String.valueOf(tagCreateable.toString());
                
                // new TagsData object with above info
                TagsData newData = new TagsData(-1,
                // -1 row index, because there is no way to know which row it
                // will go into
                                        loginId, storyId, tag
                                );

                // insert it through Resolver to be put into ContentProvider
                try {
                    resolver.insert(newData);
                } catch (RemoteException e) {
                    Log.e(LOG_TAG,
                            "Caught RemoteException => " + e.getMessage());
                    e.printStackTrace();
                }
                // return back to proper state
                if (getResources().getBoolean(R.bool.isTablet) == true) {
                    // put
                    mOpener.openViewTagsFragment(0);
                } else {
                    getActivity().finish(); // same as hitting 'back' button
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tags_creation_fragment,
                container, false);
        container.setBackgroundColor(Color.GRAY);
        return view;
    }

}
