package com.graystonemobile.betanaija;

import java.io.File;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.graystonemobile.data.BetaDBAdapter;

public class PlayOptionsDialogActivity extends Activity {
	//
	TextView tv;
	ImageButton playButton;
	ProgressBar bar;
	DownloadManager mgr=null;
	long lastDownload=-1L;
	long max=0;
	long sofar=0;
	BroadcastReceiver onComplete=new BroadcastReceiver() {
		public void onReceive(Context ctxt, Intent intent) {
			//findViewById(R.id.start).setEnabled(true);
			initializeButton();
			//onPlayClicked(new View(ctxt));
		}
	};

	BroadcastReceiver onNotificationClick=new BroadcastReceiver() {
		public void onReceive(Context ctxt, Intent intent) {
			Toast.makeText(ctxt, "Downloading Video!", Toast.LENGTH_LONG).show();
		}
	};
	//
	private int pos;
	private String videotype;
	String youtubeId;
	String videoPath;
	String fileName;
	String videoId;
	String vidPath;
	private String language;
	private Cursor c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_options);
		Bundle b = getIntent().getExtras();
		videotype = b.getString("videotype");
		pos = b.getInt("position");
		language = b.getString("language");
		getFromDB();
		mgr=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
		playButton = (ImageButton) findViewById(R.id.playOptionPlayButton);
		initializeButton();
	}
	
	
	public void initializeButton(){
		if (isVideoThere()) {
			playButton.setImageResource(R.drawable.icon_play);
		} else {
			playButton.setImageResource(R.drawable.downloads_icon);
		}
		//if file is available use play icon on button
		//if file is not available use download icon on button
	}
	
	public void onYoutubeClicked(View v){
		//get the youtube id
		//throw intent
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+youtubeId));
		startActivity(i);
		finish();
	}
	
	public void onPlayClicked(View v){
		//if file exist
		Log.d("play", "play");
		if (isVideoThere()) {
			//play the video
			Intent intentToPlayVideo = new Intent(Intent.ACTION_VIEW);
			intentToPlayVideo.setDataAndType(Uri.parse(videoPath), "video/*");
			startActivity(intentToPlayVideo);
		} else {
			//call method or class for downloading it
			Download();
			// after download, try to play the video
		}
		
	}
	
	public void getFromDB(){
		BetaDBAdapter mdbHelper = new BetaDBAdapter(this);
		mdbHelper.open();
		if (videotype.equalsIgnoreCase("family")) {
			c = mdbHelper.fetchAllFamilyBySeasonAndLanguage("1", language);
		} else if(videotype.equalsIgnoreCase("Naso")) {
			c = mdbHelper.fetchAllNaSoBySeasonAndLanguage("1", language);
		}else {
			c = mdbHelper.fetchAllShortFilmBySeasonAndLanguage("1", language);
		}
		
		c.moveToPosition(pos);
		youtubeId = c.getString(c.getColumnIndex("youtubeid"));
		videoPath = c.getString(c.getColumnIndex("path"));
		videoId = c.getString(c.getColumnIndex("videoid"));
		fileName = "vid_"+videoId.trim();
		Log.d("PlayOptions", videoPath);
		mdbHelper.close();
	}
	
	public boolean isVideoThere(){
		File path = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_DOWNLOADS);
	    File file = new File(path, fileName);
	    vidPath = file.getPath();
	    path.mkdirs();
	    if (file.exists()) {
			return true;
		}
	    return false;
	}
	
	// /////////this part is for handling download issues
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(onComplete,
				new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		registerReceiver(onNotificationClick,
				new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
		onYoutubeClicked(new View(this));
	}
	@Override
	public void onDestroy() {
		super.onDestroy();

		unregisterReceiver(onComplete);
		unregisterReceiver(onNotificationClick);
	}
	public void Download(){
		Uri uri=Uri.parse(videoPath);

		Environment
		.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
		.mkdirs();

		lastDownload=
				mgr.enqueue(new DownloadManager.Request(uri)
				.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
						DownloadManager.Request.NETWORK_MOBILE)
						.setAllowedOverRoaming(false)
						.setTitle("Beta Naija")
						.setDescription("Something useful. No, really.")
						.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
								fileName));

	}
	public void queryStatus(View v) {
		Cursor c=mgr.query(new DownloadManager.Query().setFilterById(lastDownload));

		if (c==null) {
			Toast.makeText(this, "Download not found!", Toast.LENGTH_LONG).show();
		}
		else {
			c.moveToFirst();

			Log.d(getClass().getName(), "COLUMN_ID: "+
					c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
			Log.d(getClass().getName(), "COLUMN_BYTES_DOWNLOADED_SO_FAR: "+
					c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
			Log.d(getClass().getName(), "COLUMN_LAST_MODIFIED_TIMESTAMP: "+
					c.getLong(c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)));
			Log.d(getClass().getName(), "COLUMN_LOCAL_URI: "+
					c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
			Log.d(getClass().getName(), "COLUMN_STATUS: "+
					c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)));
			Log.d(getClass().getName(), "COLUMN_REASON: "+
					c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON)));

			Toast.makeText(this, statusMessage(c), Toast.LENGTH_LONG).show();
		}
	}
	public void viewLog(View v) {
		startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
	}

	private String statusMessage(Cursor c) {
		String msg="???";

		switch(c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
		case DownloadManager.STATUS_FAILED:
			msg="Download failed!";
			break;

		case DownloadManager.STATUS_PAUSED:
			msg="Download paused!";
			break;

		case DownloadManager.STATUS_PENDING:
			msg="Download pending!";
			break;

		case DownloadManager.STATUS_RUNNING:
			msg="Download in progress!";
			break;

		case DownloadManager.STATUS_SUCCESSFUL:
			msg="Download complete!";
			break;

		default:
			msg="Download is nowhere in sight";
			break;
		}

		return(msg);
	}
}
