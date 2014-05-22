package com.graystonemobile.betanaija.family;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.graystonemobile.betanaija.R;
import com.graystonemobile.data.BetaDBAdapter;
import com.graystonemobile.data.FileStorage;

public class FamilyListAdapter extends BaseAdapter {
	
	private Context mctx;
	String language;
	private LayoutInflater LInflater;
	String[] vidids;
	String[] titles;
	String[] durations;
	Bitmap[] imgs;
	private int mcount;
	private FileStorage fl;

	public FamilyListAdapter(Context ctx,String Language){
		mctx = ctx;
		language = Language;
		LInflater = LayoutInflater.from(mctx);
		fl = new FileStorage(mctx);
		pullData();
	}
	
	protected void  pullData() {
		BetaDBAdapter mdbHelper = new BetaDBAdapter(mctx);
		mdbHelper.open();
		Cursor c = mdbHelper.fetchAllFamilyBySeasonAndLanguage("1", language.trim());
		c.moveToFirst();
		mcount = c.getCount();
		vidids = new String[mcount];
		titles = new String[mcount];
		durations = new String[mcount];
		imgs = new Bitmap[mcount];
		mdbHelper.close();
		for (int i = 0; i < mcount; i++) {
			c.moveToPosition(i);
			vidids[i] = c.getString(c.getColumnIndex("videoid"));
			titles[i] = c.getString(c.getColumnIndex("videoname"));
			durations[i] = c.getString(c.getColumnIndex("lenght"));
			imgs[i] = fl.getImage("family"+vidids[i].trim());
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mcount;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LInflater.inflate(R.layout.fragment_family_list_item, null);
			holder = new ViewHolder();
			holder.tImage = (ImageView) convertView.findViewById(R.id.FamilyListItemImageView);
			holder.tiletv = (TextView) convertView.findViewById(R.id.FamilyListItemNameTextView);
			holder.durationtv = (TextView) convertView.findViewById(R.id.FamilyListItemLengthTextView);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tiletv.setText("Title: "+titles[position]);
		holder.durationtv.setText("Duration: "+durations[position]+" secs");
		holder.tImage.setImageBitmap(imgs[position]);
		return convertView;
	}
	
	private class ViewHolder{
		TextView tiletv;
		TextView durationtv;
		ImageView tImage;
	}
}
