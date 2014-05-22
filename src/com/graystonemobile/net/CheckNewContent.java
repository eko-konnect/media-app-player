 package com.graystonemobile.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.graystonemobile.data.BetaDBAdapter;
import com.graystonemobile.data.FileStorage;

public class CheckNewContent {
	Context mctx;
	String PREFS_NAME = "PREFS";
	String Family_Last_Time = "9jaFamily";
	String NaSo_Last_Time = "NaSoEBE";
	String ShortFilms_Last_Time = "ShortFilms";
	String SeriesUrl = "http://ibelieveinabetternigeria.org/ibelieveapp/service/series_timestamp";
	String VideoUrl = "http://ibelieveinabetternigeria.org/ibelieveapp/service/video_timestamp";
	private String TAG = "CheckNewContent";
	private String TAG_DATA = "data";
	private String lasttime = " ";
	String series = "series";
	String shortfilms = "shortfilms";
	@SuppressWarnings("unused")
	private long insertSucess;
	private FileStorage fileHelper;
	
	public CheckNewContent(Context ctx){
		mctx = ctx;
		fileHelper = new FileStorage(mctx);
	}
	
	public Bitmap decode64(String bit){
		byte[] decodedString = Base64.decode(bit, Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		return decodedByte;
	}
	
	public void saveLastFamilyTime(String time){
		SharedPreferences settings = mctx.getSharedPreferences(PREFS_NAME , 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(Family_Last_Time, time.trim());
	    editor.commit();
	}
	
	public String getLastFamilyTime(){
		SharedPreferences settings = mctx.getSharedPreferences(PREFS_NAME, 0);
	    String ex = settings.getString(Family_Last_Time , "0");
	    return ex;
	}
	
	public void saveLastNasoTime(String time){
		SharedPreferences settings = mctx.getSharedPreferences(PREFS_NAME , 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(NaSo_Last_Time, time.trim());
	    editor.commit();
	}
	
	public String getLastNasoTime(){
		SharedPreferences settings = mctx.getSharedPreferences(PREFS_NAME, 0);
	    String ex = settings.getString(NaSo_Last_Time , "0");
	    return ex;
	}
	
	public void saveLastShortTime(String time){
		SharedPreferences settings = mctx.getSharedPreferences(PREFS_NAME , 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(ShortFilms_Last_Time, time.trim());
	    editor.commit();
	}
	
	public String getLastShortTime(){
		SharedPreferences settings = mctx.getSharedPreferences(PREFS_NAME, 0);
	    String ex = settings.getString(ShortFilms_Last_Time , "0");
	    return ex;
	}
	
	public boolean checkTimestamp(String what){
		if (what.equalsIgnoreCase(series)) {
			return downloadAndCheckTimeStamp(SeriesUrl);
		} else {
			return downloadAndCheckTimeStamp(VideoUrl);
		}
	}
	
	public void getit(){
		//check timestamp for series
		boolean s = checkTimestamp(series);
		//if series is not equal download series videos
		if (!s) {
			downloadSeriesFamily();
			downloadSeriesNaso();
			downloadShortFilms();
			saveLastFamilyTime(lasttime);
		}
		//save last series time
		Intent intent = new Intent();
		intent.setAction("com.graystonemobile.android.refreshbroadcast");
		mctx.sendBroadcast(intent); 
	}
	
	public boolean downloadAndCheckTimeStamp(String url){
		JSONArray ar_time = null;
		boolean t = true;
		/// starts downloading the brief timestamp
	    Log.d(TAG  , "TIMEstamp check Started");
		String period = null;
		try {
			JSONParser jp = new JSONParser();
			JSONObject json = jp.getJSONfromUrlGet(url.trim());
			Log.d(TAG, "json from url 1");
			//json = jp.getJSONfromUrl(url_ad_time);
			//Log.d(TAG, "json from url 2");
			if (json != null) {
				ar_time  = json.getJSONArray(TAG_DATA );
				Log.d(TAG, "timestamp json is not null");
			} else {
				Log.d(TAG, "timestamp json is null");
			}
		} catch (Exception e) {
			Log.d(TAG, "event time json exception");
			e.printStackTrace();
		}
		if (ar_time == null) {
			Log.d(TAG, "no response from timestamp server");
		} else {
			for (int i = 0; i < ar_time.length(); i++) {
				try {
					JSONObject c = ar_time.getJSONObject(i);
					period = c.getString("maxdate");
					
					Log.d("time", period);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//return true if equal
				if (period.trim().equalsIgnoreCase(getLastFamilyTime().trim())) {
					t = true;
					Log.d("time", "same");
				}else{
					t = false;
					Log.d("time", "not same");
					lasttime  = period.trim();
				}
				//return false if not
				return t;
	}
	
	public void downloadSeriesFamily(){
		BetaDBAdapter mdbHelper = new BetaDBAdapter(mctx);
		mdbHelper.open();
		//FileStorage fileHelper = new FileStorage(mctx);
		
		String url = "http://ibelieveinabetternigeria.org/ibelieveapp/service/series_videos";
		JSONArray ar = null;
		Log.d(TAG , "Series Family Started");
	    Log.d(TAG , url);
		String videoid,title,seriesid,series,description,thumbnail,language,type,lenght,season,youtube,path,share = " ";
		try {
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("series", "1"));
	        JSONParser jp = new JSONParser();
	        JSONObject json = jp.getJSONfromUrl(url, nameValuePairs);
			//
			Log.d(TAG, "json from url series videos");
			
			if (json != null) {
				ar  = json.getJSONArray(TAG_DATA );
				Log.d(TAG, "family  json is not null");
			} else {
				Log.d(TAG, "family json is null");
			}
		} catch (Exception e) {
			Log.d(TAG, "family json exception");
			e.printStackTrace();
		}
		if (ar == null) {
			Log.d(TAG, "no response from family server");
		}else {
			mdbHelper.deleteAllFamilyVideos();
			for (int i = 0; i < ar.length(); i++) {
				try {
					JSONObject c = ar.getJSONObject(i);
					videoid = c.getString("video_id");
					title = c.getString("title");
					seriesid = c.getString("series_id");
					series = c.getString("series");
					description = c.getString("description");
					thumbnail = c.getString("thumbnail");
					language = c.getString("language");
					type = c.getString("video_type");
					lenght = c.getString("length");
					season = c.getString("season");
					youtube = c.getString("youtube");
					path = c.getString("video_path");
					share = c.getString("share");
					
					Log.d("title", title);
					Bitmap bmp = decode64(thumbnail.trim());					
					Log.d(TAG, String.valueOf(bmp.getHeight()));
					
					insertSucess  = mdbHelper.InsertNewFamilyVideo(videoid, title, seriesid, series, description, thumbnail, language, type, lenght, season, youtube, path, share);
					fileHelper.saveImage(bmp, "family"+videoid.trim());
				} catch (Exception e) {
					Log.d(TAG, "Looping is bad");
					e.printStackTrace();
				}
			}
		}
		mdbHelper.close();
	}
	public void downloadSeriesNaso(){
		BetaDBAdapter mdbHelper = new BetaDBAdapter(mctx);
		mdbHelper.open();
		//FileStorage fileHelper = new FileStorage(mctx);
		
		String url = "http://ibelieveinabetternigeria.org/ibelieveapp/service/series_videos";
		JSONArray ar = null;
		Log.d(TAG , "Series Naso Started");
	    Log.d(TAG , url);
		String videoid,title,seriesid,series,description,thumbnail,language,type,lenght,season,youtube,path,share = " ";
		try {
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("series", "2"));
	        JSONParser jp = new JSONParser();
	        JSONObject json = jp.getJSONfromUrl(url, nameValuePairs);
			//
			Log.d(TAG, "json from url series videos");
			
			if (json != null) {
				ar  = json.getJSONArray(TAG_DATA );
				Log.d(TAG, "naso  json is not null");
			} else {
				Log.d(TAG, "naso json is null");
			}
		} catch (Exception e) {
			Log.d(TAG, "naso json exception");
			e.printStackTrace();
		}
		if (ar == null) {
			Log.d(TAG, "no response from naso server");
		}else {
			mdbHelper.deleteAllNaSoVideos();
			for (int i = 0; i < ar.length(); i++) {
				try {
					JSONObject c = ar.getJSONObject(i);
					videoid = c.getString("video_id");
					title = c.getString("title");
					seriesid = c.getString("series_id");
					series = c.getString("series");
					description = c.getString("description");
					thumbnail = c.getString("thumbnail");
					language = c.getString("language");
					type = c.getString("video_type");
					lenght = c.getString("length");
					season = c.getString("season");
					youtube = c.getString("youtube");
					path = c.getString("video_path");
					share = c.getString("share");
					
					Log.d("title", title);
					Bitmap bmp = decode64(thumbnail.trim());					
					Log.d(TAG, String.valueOf(bmp.getHeight()));
					
					insertSucess  = mdbHelper.InsertNewNaSoVideo(videoid, title, seriesid, series, description, thumbnail, language, type, lenght, season, youtube, path, share);
					fileHelper.saveImage(bmp, "Naso"+videoid.trim());
				} catch (Exception e) {
					Log.d(TAG, "Looping is bad");
					e.printStackTrace();
				}
			}
		}
		mdbHelper.close();
	}
	public void downloadShortFilms(){
		BetaDBAdapter mdbHelper = new BetaDBAdapter(mctx);
		mdbHelper.open();
		//FileStorage fileHelper = new FileStorage(mctx);
		
		String url = "http://ibelieveinabetternigeria.org/ibelieveapp/service/series_videos";
		JSONArray ar = null;
		Log.d(TAG , "Short film Started");
	    Log.d(TAG , url);
		String videoid,title,seriesid,series,description,thumbnail,language,type,lenght,season,youtube,path,share = " ";
		try {
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("series", "3"));
	        JSONParser jp = new JSONParser();
	        JSONObject json = jp.getJSONfromUrl(url, nameValuePairs);
			//
			Log.d(TAG, "json from url series videos");
			
			if (json != null) {
				ar  = json.getJSONArray(TAG_DATA );
				Log.d(TAG, "short film  json is not null");
			} else {
				Log.d(TAG, "short film json is null");
			}
		} catch (Exception e) {
			Log.d(TAG, "short film json exception");
			e.printStackTrace();
		}
		if (ar == null) {
			Log.d(TAG, "no response from short film server");
		}else {
			mdbHelper.deleteAllShortFilmsVideos();
			for (int i = 0; i < ar.length(); i++) {
				try {
					JSONObject c = ar.getJSONObject(i);
					videoid = c.getString("video_id");
					title = c.getString("title");
					seriesid = c.getString("series_id");
					series = c.getString("series");
					description = c.getString("description");
					thumbnail = c.getString("thumbnail");
					language = c.getString("language");
					type = c.getString("video_type");
					lenght = c.getString("length");
					season = c.getString("season");
					youtube = c.getString("youtube");
					path = c.getString("video_path");
					share = c.getString("share");
					
					Log.d("title", title);
					Bitmap bmp = decode64(thumbnail.trim());					
					Log.d(TAG, String.valueOf(bmp.getHeight()));
					
					insertSucess  = mdbHelper.InsertShortFilmVideo(videoid, title, seriesid, series, description, thumbnail, language, type, lenght, season, youtube, path, share);
					fileHelper.saveImage(bmp, "SF"+videoid.trim());
				} catch (Exception e) {
					Log.d(TAG, "Looping is bad");
					e.printStackTrace();
				}
			}
		}
		mdbHelper.close();
	}
}
