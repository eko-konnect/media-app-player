package com.graystonemobile.betanaija.manifesto;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.graystonemobile.betanaija.R;
import com.graystonemobile.net.Connectivity;
import com.graystonemobile.net.JSONParser;

public class ManifestoSignActivity extends Activity {
	String status,message = " ";
	private String PREFS_NAME = "ManifestoPrefs" ;
	String signed = "signed";
	EditText name;
	EditText email;
	String sname = "";
	String semail = "";
	private String TAG = "ManifestoSignActivity" ;
	private String TAG_DATA = "data";
	
	final Handler mHandler = new Handler();
	final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_manifesto_sign);
		name = (EditText) findViewById(R.id.ActivityManifestoFullNameEdittext);
		email = (EditText) findViewById(R.id.ActivityManifestoEmailEdittext);
	}

	public void setSigned(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(signed, "yes");
		editor.commit();
	}

	public void onCanel(View v){
		finish();
	}

	public void onBeleive(View v){
		if (extractText()) {
			toogleButton(true);
			Submit();
		}
	}

	private boolean extractText(){
		if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {
			Context context = this;
			CharSequence text = "All Fields are Required!";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			return false;
		} else {
			sname = name.getText().toString().trim();
			semail = email.getText().toString().trim();
			return true;
		}
	}

	public void submitSigning(){
		String url = "http://ibelieveinabetternigeria.org/ibelieveapp/service/save_manifesto";
		JSONArray ar = null;
		Log.d(TAG  , "Manifesto sign Started");
		Log.d(TAG , url);
		
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("fullname", sname));
			nameValuePairs.add(new BasicNameValuePair("email", semail));
			JSONParser jp = new JSONParser();
			JSONObject json = jp.getJSONfromUrl(url, nameValuePairs);

			Log.d(TAG, "json from url Manifesto sign");

			if (json != null) {
				ar  = json.getJSONArray(TAG_DATA  );
				Log.d(TAG, "family  json is not null");
			} else {
				Log.d(TAG, "family json is null");
			}
		} catch (Exception e) {
			Log.d(TAG, "manifesto json exception");
			e.printStackTrace();		
		}
		if (ar == null) {
			Log.d(TAG, "no response from Manifesto server");
		}else {
			try {
				for (int i = 0; i < ar.length(); i++) {
					JSONObject c = ar.getJSONObject(i);
					status = c.getString("status");
					message = c.getString("message");
					Log.d("status", status);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void checkStatus(){
		if (status.trim().equalsIgnoreCase("Success")) {
			setSigned();
			Context context = this;
			CharSequence text = "Successfull!";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			finish();
		} else {
			Context context = this;
			CharSequence text = "Failed!! Check Connection";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			finish();
		}
	}
	
	public void Submit(){
    	Thread t = new Thread(){
    		public void run(){
    			startSubmit();
    			mHandler.post(mUpdateResults);
    		}
    	};
    	t.start();
    }
	
	public void startSubmit(){
    	//check for internet connection
    	Connectivity cn = new Connectivity(this);
    	if (cn.isConnected()) {
			//toogleButton(true);
			submitSigning();
			//checkStatus();
		} else {
			Context context = getApplicationContext();
			CharSequence text = "No Connection! Check and Try Again";
			Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
			toast.show();
		}
    	
    }
	
	public void updateResultsInUi(){
		Log.d("nope", "got here");
		toogleButton(false);
		checkStatus();
	}
	
	public void toogleButton(boolean b){
		if (b == true) {
			setProgressBarIndeterminateVisibility(true);
		} 
		else {
			setProgressBarIndeterminateVisibility(false);
		}
	}
}
