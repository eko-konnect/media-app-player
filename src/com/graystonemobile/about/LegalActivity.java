package com.graystonemobile.about;

import com.graystonemobile.betanaija.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class LegalActivity extends Activity {
	WebView web;
	private ProgressDialog progDailog;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_legal);
		progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
        progDailog.setCancelable(false);
		web = (WebView) findViewById(R.id.legalWebview);
		web.setEnabled(false);
		//web.getSettings().setJavaScriptEnabled(true);     
	       //web.getSettings().setLoadWithOverviewMode(true);
	       //web.getSettings().setUseWideViewPort(true);        
	        web.setWebViewClient(new WebViewClient(){

	            @Override
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                progDailog.show();
	                view.loadUrl(url);

	                return true;                
	            }
	            @Override
	            public void onPageFinished(WebView view, final String url) {
	                progDailog.dismiss();
	            }
	        });
	        web.loadUrl("file:///android_asset/policy.htm");
	}
}
