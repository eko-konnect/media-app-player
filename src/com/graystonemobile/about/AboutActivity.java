package com.graystonemobile.about;

import com.graystonemobile.betanaija.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}
	
	public void onLegalClicked(View v){
		startActivity(new Intent(this, LegalActivity.class));
	}
	
	public void onFeedbackClicked(View v){
		String[] to = {"owen@datasphir.com"};
		String[] bcc = {"mail@graystone.com.ng"};
		sendEmail(to, bcc, "Better Nigeria Feedback", "");
	}

	public void onKelvinClicked(View v){
		String[] to = {"kelvox@gmail.com"};
		String[] bcc = {""};
		sendEmail(to, bcc, "Better Nigeria:", "");
	}
	
	public void onTobiClicked(View v){
		String[] to = {"teebazzy@gmail.com"};
		String[] bcc = {"owen@datasphir.com","kelvox@gmail.com"};
		sendEmail(to, bcc, "Better Nigeria:", "");		
	}
	
	public void onPaulaClicked(View v){
		String[] to = {"onotseike@gmail.com"};
		String[] bcc = {"owen@datasphir.com","kelvox@gmail.com"};
		sendEmail(to, bcc, "Better Nigeria:", "");		
	}
	
	private void sendEmail(String[] emails, String[] bccs, String subject, String message) {
		 Intent ei = new Intent(Intent.ACTION_SEND);
		 ei.setData(Uri.parse("mail to: "));
		 String[] to = emails;
		 String[] bcc = bccs;
		 ei.putExtra(Intent.EXTRA_EMAIL, to);
		 ei.putExtra(Intent.EXTRA_BCC, bcc);
		 ei.putExtra(Intent.EXTRA_SUBJECT, subject);
		 ei.putExtra(Intent.EXTRA_TEXT, message);
		 ei.setType("message/rfc822");
		 startActivity(Intent.createChooser(ei, "EMAIL"));
	 }
	
	public void onEkoClicked(View v){
		String url = "http://www.eko-konnect.org.ng";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
	
	public void onMudiClicked(View v){
		String url = "http://ibelieveinabetternigeria.org";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
	
	public void onFordClicked(View v){
		//do nothing for ford
	}
}
