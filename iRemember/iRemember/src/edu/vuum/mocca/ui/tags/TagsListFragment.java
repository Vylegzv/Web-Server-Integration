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

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import edu.vanderbilt.mooc.R;
import edu.vuum.mocca.orm.MoocResolver;
import edu.vuum.mocca.orm.TagsData;

public class TagsListFragment extends ListFragment {

    static final String LOG_TAG = TagsListFragment.class.getCanonicalName();

    OnOpenWindowInterface mOpener;
    MoocResolver resolver;
    ArrayList<TagsData> TagsData;
    private TagsDataArrayAdaptor aa;

    @Override
    public void onAttach(Activity activity) {
        Log.d(LOG_TAG, "onAttach start");
        super.onAttach(activity);
        try {
            mOpener = (OnOpenWindowInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnOpenWindowListener" + e.getMessage());
        }
        Log.d(LOG_TAG, "onAttach end");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpener = null;
    }

    /**
     * The system calls this when creating the fragment. Within your
     * implementation, you should initialize essential components of the
     * fragment that you want to retain when the fragment is paused or stopped,
     * then resumed.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        resolver = new MoocResolver(getActivity());
        TagsData = new ArrayList<TagsData>();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tags_listview, container, false);
        // get the ListView that will be displayed
        ListView lv = (ListView) view.findViewById(android.R.id.list);

        // customize the ListView in whatever desired ways.
        lv.setBackgroundColor(Color.GRAY);
        // return the parent view
        return view;
    }

    public void updateTagsData() {
        Log.d(LOG_TAG, "updateTagsData");
        try {
            TagsData.clear();

            ArrayList<TagsData> currentList = resolver.getAllTagsData();

            TagsData.addAll(currentList);
            aa.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(LOG_TAG,
                    "Error connecting to Content Provider" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // create the custom array adapter that will make the custom row
        // layouts
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "onActivityCreated");
        aa = new TagsDataArrayAdaptor(getActivity(),
                R.layout.tags_listview_custom_row, TagsData);

        // update the back end data.
        updateTagsData();

        setListAdapter(aa);

        // TODO see why I have to manually "setOnClickListener"

        Button createNewButton = (Button) getView().findViewById(
                R.id.tags_listview_create);
        createNewButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mOpener.openCreateTagsFragment();
            }
        });

        Button refreshListButton = (Button) getView().findViewById(
                R.id.tags_listview_refresh);
        refreshListButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTagsData();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(LOG_TAG, "onListItemClick");
        Log.d(LOG_TAG,
                "position: " + position + "id = "
                        + (TagsData.get(position)).KEY_ID);
        mOpener.openViewTagsFragment((TagsData.get(position)).KEY_ID);
    }

}
