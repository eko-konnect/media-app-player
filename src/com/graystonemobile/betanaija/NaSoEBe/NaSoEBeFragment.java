package com.graystonemobile.betanaija.NaSoEBe;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.graystonemobile.betanaija.PlayOptionsDialogActivity;
import com.graystonemobile.betanaija.R;
import com.graystonemobile.betanaija.UIRefresher;

public class NaSoEBeFragment extends ListFragment implements UIRefresher {
	Spinner languages;
	private ImageView banner;
	private String text;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_family, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeViews();
		setListAdapterByLanguage();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
			      new IntentFilter("my-event"));
	}
	
	// handler for received Intents for the "my-event" event 
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
	  @Override
	  public void onReceive(Context context, Intent intent) {
	    // Extract data included in the Intent
	    String message = intent.getStringExtra("message");
	    Log.d("receiver", "Got message: " + message);
	    refreshUI();
	  }
	};

	@Override
	public void onPause() {
	  // Unregister since the activity is not visible
	  LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
	  super.onPause();
	} 


	protected void initializeViews() {
		languages = (Spinner) getActivity().findViewById(R.id.languageSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.language_array, R.layout.language_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		languages.setAdapter(adapter);
		languages.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				setListAdapterByLanguage();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		banner =(ImageView) getActivity().findViewById(R.id.bannerImageView);
		banner.setBackgroundResource(R.drawable.naso);
	}

	protected void setListAdapterByLanguage(){
		text = languages.getSelectedItem().toString();
		int pos = languages.getSelectedItemPosition();
		switch (pos) {
		case 0:
			setListAdapter(new NaSoEBeListAdapter(getActivity(), "english"));
			break;

		case 1:
			//igbo is selected
			setListAdapter(new NaSoEBeListAdapter(getActivity(), "igbo"));
			break;
			
		case 2:
			//yoruba is selected
			setListAdapter(new NaSoEBeListAdapter(getActivity(), "yoruba"));
			break;

		case 3:
			//hausa is selected
			setListAdapter(new NaSoEBeListAdapter(getActivity(), "hausa"));
			break;

		case 4:
			//pidgin is selected
			setListAdapter(new NaSoEBeListAdapter(getActivity(), "pidgin"));
			break;
			
		default:
			break;
		}
	}
	
	@SuppressLint("DefaultLocale")
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent mainIntent = new Intent().setClass(getActivity(), PlayOptionsDialogActivity.class);
		mainIntent.putExtra("position", position);
		mainIntent.putExtra("videotype", "Naso");
		mainIntent.putExtra("language", text.toLowerCase().trim());
		startActivity(mainIntent);
	}
	
	@Override
	public void refreshUI() {
		// TODO Auto-generated method stub
		setListAdapterByLanguage();
	}
}
