package com.graystonemobile.betanaija;

import com.graystonemobile.about.AboutActivity;
import com.graystonemobile.betanaija.NaSoEBe.NaSoEBeFragment;
import com.graystonemobile.betanaija.ShortFilm.ShortFilmFragment;
import com.graystonemobile.betanaija.family.FamilyFragment;
import com.graystonemobile.betanaija.manifesto.ManifestoFragment;
import com.graystonemobile.net.CheckNewContent;
import com.graystonemobile.net.Connectivity;
import com.graystonemoble.betanaija.home.DrawerListAdapter;
import com.graystonemoble.betanaija.home.HomeFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class HomeActivity extends Activity {
	String TAG = "MainActivity";

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;    
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	final Handler mHandler = new Handler();
	final Runnable mUpdateResults = new Runnable() {
        public void run() {
            updateResultsInUi();
            sendMessage();
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_home);
		setProgressBarIndeterminateVisibility(false);
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// Set the adapter for the list view
		mDrawerList.setAdapter(new DrawerListAdapter(getApplicationContext()));
     // Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        //sets the alphabetical hymn list by default
        if(findViewById(R.id.content_frame) != null) {
        	if (savedInstanceState != null) {
                return;
            }
        	
        	HomeFragment firstFragment = new HomeFragment();
        	firstFragment.setArguments(getIntent().getExtras());
        	
        	getFragmentManager().beginTransaction()
            .add(R.id.content_frame, firstFragment).commit();
        }
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...
		switch (item.getItemId()) {
		case R.id.action_refrsh:
            doRefresh();
            return true;
        case R.id.action_settings:
            openSettings();
            return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	private void openSettings() {
		Intent mainIntent = new Intent().setClass(getApplicationContext(), AboutActivity.class);
		startActivity(mainIntent);
	}

	private void doRefresh() {
		// TODO Auto-generated method stub
		refreshContent();
	}
	
	/** Swaps fragments in the main content view */
	private void selectItem(int position){
		switch (position) {
		case 0:
			FamilyFragment ffrag = new FamilyFragment();
			FragmentManager fragmentManagera = getFragmentManager();
		    fragmentManagera.beginTransaction()
		                   .replace(R.id.content_frame, ffrag).addToBackStack(null)
		                   .commit();
			break;
		
		case 1:
			NaSoEBeFragment pfrag = new NaSoEBeFragment();
			FragmentManager fragmentManagerp = getFragmentManager();
		    fragmentManagerp.beginTransaction()
		                   .replace(R.id.content_frame,pfrag).addToBackStack(null)
		                   .commit();
			break;
		case 2:
			ShortFilmFragment nfrag = new ShortFilmFragment();
			FragmentManager fragmentManagern = getFragmentManager();
		    fragmentManagern.beginTransaction()
		                   .replace(R.id.content_frame, nfrag).addToBackStack(null)
		                   .commit();
			break;
		case 3:
			ManifestoFragment efrag = new ManifestoFragment();
			FragmentManager fragmentManagere = getFragmentManager();
		    fragmentManagere.beginTransaction()
		                   .replace(R.id.content_frame, efrag).addToBackStack(null)
		                   .commit();
			break;
		
		default:
			break;
		}
		mDrawerList.setItemChecked(position, true);
	    //setTitle(mDrawerOptions[position]);
	    mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(@SuppressWarnings("rawtypes") AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}
	
    @Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getActionBar().setTitle(mTitle);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    public void updateResultsInUi(){
		//errortv.setText("Incorrect Details or Non-Existent Account.");
		Log.d("nope", "got here");
		toogleButton(false);
	}
    
    public void refreshContent(){
    	//check for internet connection
    	Connectivity cn = new Connectivity(this);
    	if (cn.isConnected()) {
			toogleButton(true);
			downloadContent();
		} else {
			Context context = getApplicationContext();
			CharSequence text = "No Connection! Check and Try Again";
			Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
			toast.show();
		}
    	//if internet
    	//put veil or dialog with message
    	//do the download
    	//remove veil or dialog
    	//remove indeterminate progress bar.
    	//tell fragment to reset listadapter
    	
    }
    
    public void downloadContent(){
    	final CheckNewContent c = new CheckNewContent(this);
    	Thread t = new Thread(){
    		public void run(){
    			c.getit();
    			mHandler.post(mUpdateResults);
    		}
    	};
    	t.start();
    }
    private void sendMessage() {
    	  Intent intent = new Intent("my-event");
    	  // add data
    	  intent.putExtra("message", "data");
    	  LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    	} 
    public void toogleButton(boolean b){
		if (b == true) {
			setProgressBarIndeterminateVisibility(true);
			//ok.setText("Logging in..");
			//ok.setEnabled(false);
		} 
		else {
			setProgressBarIndeterminateVisibility(false);
			//ok.setText("Login");
			//ok.setEnabled(true);
		}
	}
    
    public void onFamilyClicked(View v){
		selectItem(0);
	}
    public void onNasoClicked(View v){
		selectItem(1);
	}
    public void onShortFilmClicked(View v){
		selectItem(2);
	}
}
