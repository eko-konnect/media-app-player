package com.graystonemoble.betanaija.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.graystonemobile.betanaija.R;



public class DrawerListAdapter extends BaseAdapter {
	private String[] mDrawerOptions = {"9ja Family Chronicles","Na So E Be","Short Films","Manifesto"};
	//private int[] mDrawerIcons = {R.drawable.mix_white,R.drawable.playlist_white,R.drawable.news_white,R.drawable.event_white,R.drawable.vault_white,R.drawable.settings_white};
	private Context mctx;
	private LayoutInflater LInflater;
	
	public DrawerListAdapter(Context ctx){
		mctx = ctx;
		LInflater = LayoutInflater.from(mctx);
	}
	
	@Override
	public int getCount() {
		return mDrawerOptions.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LInflater.inflate(R.layout.side_drawer_list_item, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.SideDrawerTextView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(mDrawerOptions[position]);
		return convertView;
	}
	
	private static class ViewHolder {
		TextView tv;
		 }
}
