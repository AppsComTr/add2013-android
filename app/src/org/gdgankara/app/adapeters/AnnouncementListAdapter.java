package org.gdgankara.app.adapeters;

import java.util.List;

import org.gdgankara.app.R;
import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.Session;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnnouncementListAdapter extends ArrayAdapter<Announcement>{
	
	private List<Announcement> announcementlist;
	private LayoutInflater inflater ;
	private TextView text;
	private int textSize;
	private View view;
	private ImageView imageView;
	private Announcement announcement;
	private RelativeLayout layout;
	private int pressed;
	private RelativeLayout.LayoutParams params;
	
	public AnnouncementListAdapter(Context context, List<Announcement> objects,int height,int width,int pressed) {
		super(context,R.layout.child_of_announcementlist,objects);
		announcementlist=objects;
		this.pressed=pressed;
		params=new RelativeLayout.LayoutParams((int)((width*3)/7),(int)((height*6)/49));
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setFeaturesTextSize(height);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		announcement=announcementlist.get(position);
		view=inflater.inflate(R.layout.child_of_announcementlist, null, false);
		text=(TextView)view.findViewById(R.id.announcementlist_title);
		text.setText(announcement.getTitle());
		text=(TextView)view.findViewById(R.id.announcementlist_desc);
		text.setText(announcement.getDescription());
		text.setTextSize(textSize);
		if(position==pressed){
			layout=(RelativeLayout)view.findViewById(R.id.announcementchild_layout);
			layout.setBackgroundColor(Color.parseColor("#9eceb7"));
		}
		imageView=(ImageView)view.findViewById(R.id.announcementlist_announcement_image);
		imageView.setLayoutParams(params);
		return view;
	}
	
	private void setFeaturesTextSize(int height){
		
		if(height<=320){
			textSize=10;
		}
		else if(height<=480){
			textSize=11;
		}
		else if(height<=800){
			textSize=12;
		}
		else{
			textSize=13;
		}
		
	}

}
