package com.graystonemobile.betanaija.manifesto;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.graystonemobile.betanaija.R;

public class ManifestoFragment extends Fragment {
	Button sign;
	private String PREFS_NAME = "ManifestoPrefs" ;
	String signed = "signed";
	WebView web;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_manifesto, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeViews();
	}

	private void initializeViews() {
		web = (WebView) getActivity().findViewById(R.id.ManifestoWebView);
		web.setEnabled(false);
		web.loadUrl("file:///android_asset/manifesto.htm");
		sign = (Button) getActivity().findViewById(R.id.manifestoSignButton);
		sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), ManifestoSignActivity.class));
			}
		});
		if (isAlreadySigned()) {
			sign.setEnabled(false);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		initializeViews();
	}
	
	public boolean isAlreadySigned(){
		SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
	    String ex = settings.getString(signed, "no");
	    if (ex.equalsIgnoreCase("yes")) {
	    	return true;
		} else {
			return false;
		}
		
	}
	
}
