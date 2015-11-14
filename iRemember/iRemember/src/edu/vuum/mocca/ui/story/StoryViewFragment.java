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

package edu.vuum.mocca.ui.story;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.vanderbilt.mooc.R;
import edu.vuum.mocca.orm.MoocResolver;
import edu.vuum.mocca.orm.StoryData;

public class StoryViewFragment extends Fragment {
	// LOG TAG, handles refactoring changes
	private static final String LOG_TAG = StoryViewFragment.class
			.getCanonicalName();

	private MoocResolver resolver;
	public final static String rowIdentifyerTAG = "index";

	private OnOpenWindowInterface mOpener;

	StoryData storyData;

	TextView loginIdTV;
	TextView storyIdTV;
	TextView titleTV;
	TextView bodyTV;
	TextView audioLinkTV;
	TextView videoLinkTV;
	TextView imageNameTV;

	TextView tagsTV;
	TextView creationTimeTV;
	TextView storyTimeTV;
	TextView latitudeTV;
	TextView longitudeTV;

	ImageView imageMetaDataView;

	// buttons for edit and delete
	Button editButton;
	Button deleteButton;

	// DatePicker storyDate;

	// on-click listener, calls appropriate methods on user click on buttons
	// TODO what pattern is this... ?
	OnClickListener myOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {

			switch (view.getId()) {
			case R.id.button_story_view_to_delete:
				deleteButtonPressed();
				break;
			case R.id.button_story_view_to_edit:
				editButtonPressed();
				break;
			default:
				break;
			}
		}
	};

	public static StoryViewFragment newInstance(long index) {
		StoryViewFragment f = new StoryViewFragment();

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
		View view = inflater.inflate(R.layout.story_view_fragment, container,
				false);
		container.setBackgroundColor(Color.GRAY);
		return view;
	}

	// this fragment is modifying its view before display
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		loginIdTV = (TextView) getView().findViewById(
				R.id.story_view_value_login_id);
		storyIdTV = (TextView) getView().findViewById(
				R.id.story_view_value_story_id);
		titleTV = (TextView) getView()
				.findViewById(R.id.story_view_value_title);
		bodyTV = (TextView) getView().findViewById(R.id.story_view_value_body);
		audioLinkTV = (TextView) getView().findViewById(
				R.id.story_view_value_audio_link);
		videoLinkTV = (TextView) getView().findViewById(
				R.id.story_view_value_video_link);
		imageNameTV = (TextView) getView().findViewById(
				R.id.story_view_value_image_name);

		tagsTV = (TextView) getView().findViewById(R.id.story_view_value_tags);
		creationTimeTV = (TextView) getView().findViewById(
				R.id.story_view_value_creation_time);
		storyTimeTV = (TextView) getView().findViewById(
				R.id.story_view_value_story_time);
		latitudeTV = (TextView) getView().findViewById(
				R.id.story_view_value_latitude);
		longitudeTV = (TextView) getView().findViewById(
				R.id.story_view_value_longitude);

		imageMetaDataView = (ImageView) getView().findViewById(
				R.id.story_view_value_image_meta_data);

		// storyDate = (DatePicker) getView().findViewById(
		// R.id.story_create_value_story_time_date_picker);

		// TODO: thread the lookup
		// set the displayed values to 'defualts' until the screen loads
		// (threading & progress bar not yet implmented)
		// really shouldn't need a progress bar... since the ContentProvider
		// should be local, but will add it in just incase

		loginIdTV.setText("" + 0);
		storyIdTV.setText("" + 0);
		titleTV.setText("" + "");
		bodyTV.setText("" + "");
		audioLinkTV.setText("" + "");
		videoLinkTV.setText("" + "");
		imageNameTV.setText("" + "");
		// imageMetaDataTV.setText("" + "");
		tagsTV.setText("" + "");
		// creationTimeTV.setText("" + 0);
		// storyTimeTV.setText("" + 0);

		latitudeTV.setText("" + 0);
		longitudeTV.setText("" + 0);

		editButton = (Button) getView().findViewById(
				R.id.button_story_view_to_edit);
		deleteButton = (Button) getView().findViewById(
				R.id.button_story_view_to_delete);

		editButton.setOnClickListener(myOnClickListener);
		deleteButton.setOnClickListener(myOnClickListener);

		try {
			setUiToStoryData(getUniqueKey());
		} catch (RemoteException e) {
			Toast.makeText(getActivity(),
					"Error retrieving information from local data store.",
					Toast.LENGTH_LONG).show();
			Log.e(LOG_TAG, "Error getting Story data from C.P.");
			// e.printStackTrace();
		}
	}

	public void setUiToStoryData(long getUniqueKey) throws RemoteException {
		Log.d(LOG_TAG, "setUiToStoryData");
		storyData = resolver.getStoryDataViaRowID(getUniqueKey);
		if (storyData == null) {
			getView().setVisibility(View.GONE);
		} else { // else it just displays empty screen
			Log.d(LOG_TAG,
					"setUiToStoryData + storyData:" + storyData.toString());
			loginIdTV.setText(Long.valueOf(storyData.loginId).toString());
			storyIdTV.setText(Long.valueOf(storyData.storyId).toString());
			titleTV.setText(String.valueOf(storyData.title).toString());
			bodyTV.setText(String.valueOf(storyData.body).toString());
			audioLinkTV.setText(String.valueOf(storyData.audioLink).toString());
			videoLinkTV.setText(String.valueOf(storyData.videoLink).toString());
			imageNameTV.setText(String.valueOf(storyData.imageName).toString());
			tagsTV.setText(String.valueOf(storyData.tags).toString());
			creationTimeTV.setText(getStringDate(storyData.creationTime));
			storyTimeTV.setText(getStringDate(storyData.storyTime));

			latitudeTV.setText(Double.valueOf(storyData.latitude).toString());
			longitudeTV.setText(Double.valueOf(storyData.longitude).toString());

			Log.d(LOG_TAG, "image file path: " + storyData.imageLink);
			// set up image

			imageNameTV.setVisibility(View.GONE);
			imageMetaDataView.setVisibility(View.GONE);

			if (storyData.imageLink != null
					&& storyData.imageLink.equals("") == false
					&& storyData.imageLink.equals("null") == false) {
				Log.d(LOG_TAG, "image link is valid" + storyData.imageLink);
				Uri uri = Uri.parse(storyData.imageLink);
				File image = new File(uri.getPath());
				Log.d(LOG_TAG, "uri path: " + uri.getPath());
				Log.d(LOG_TAG, "image: " + image);
				Log.d(LOG_TAG, "image exists: " + image.exists());
				if (image != null && image.exists()) {
					Log.d(LOG_TAG, "image link is valid");
					imageNameTV.setVisibility(View.VISIBLE);
					imageNameTV.setText("Image");
					String imageMetaDataPath = String.valueOf(
							storyData.imageLink).toString();

					Bitmap bmp = BitmapFactory.decodeFile(image
							.getAbsolutePath());
					imageMetaDataView.setVisibility(View.VISIBLE);
					imageMetaDataView.setImageBitmap(bmp);
				}
			}

		}
	}

	public String getStringDate(long timestamp) {
		Calendar cal = Calendar.getInstance(Locale.ENGLISH);
		cal.setTimeInMillis(timestamp);
		String date = DateFormat.format("dd-MM-yyyy", cal).toString();
		return date;
	}

	// action to be performed when the edit button is pressed
	private void editButtonPressed() {
		mOpener.openEditStoryFragment(storyData.KEY_ID);
	}

	// action to be performed when the delete button is pressed
	private void deleteButtonPressed() {
		String message;

		message = getResources().getString(
				R.string.story_view_deletion_dialog_message);

		new AlertDialog.Builder(getActivity())
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.story_view_deletion_dialog_title)
				.setMessage(message)
				.setPositiveButton(R.string.story_view_deletion_dialog_yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									resolver.deleteAllStoryWithRowID(storyData.KEY_ID);
								} catch (RemoteException e) {
									Log.e(LOG_TAG, "RemoteException Caught => "
											+ e.getMessage());
									e.printStackTrace();
								}
								mOpener.openListStoryFragment();
								if (getResources().getBoolean(R.bool.isTablet) == true) {
									mOpener.openViewStoryFragment(-1);
								} else {
									getActivity().finish();
								}
							}

						})
				.setNegativeButton(R.string.story_view_deletion_dialog_no, null)
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