package com.graystonemobile.betanaija;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class SplashActivity extends Activity {
	long SplashDelay = 2000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		TimerTask task = new TimerTask() {			
			@Override
			public void run() {
				finish();
				Intent mainIntent = new Intent().setClass(getApplicationContext(), HomeActivity.class);
				startActivity(mainIntent); 
			}
		};
		Timer timer = new Timer();
    	timer.schedule(task, SplashDelay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	@Override
	public void onBackPressed() {
		return;
	}

}
