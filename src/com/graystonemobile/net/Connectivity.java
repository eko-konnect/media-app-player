package com.graystonemobile.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connectivity {
	Context mctx;
	
	public Connectivity(Context ctx){
		mctx = ctx;
	}
	
	public boolean isConnected() {
		final ConnectivityManager conMgr =  (ConnectivityManager)mctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isConnectionWifi(){
		ConnectivityManager cm = (ConnectivityManager)mctx. getSystemService(Context.CONNECTIVITY_SERVICE);
	    if( ! cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected() ) {
	        return true; 
	    }
		else {
			return false;
		}
	}
	
	public boolean isConnection3G(){
		ConnectivityManager cm = (ConnectivityManager)mctx. getSystemService(Context.CONNECTIVITY_SERVICE);
	    if( ! cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() ) {
	        return true; 
	    }
		else {
			return false;
		}
	}
}
