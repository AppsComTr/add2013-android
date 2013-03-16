package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.SessionListAdapter;
import org.gdgankara.app.adapeters.SpeakerListAdapter;
import org.gdgankara.app.model.Speaker;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SpeakerListActivity extends Activity{

	private int height;
	private ListView speaker_listview;
	private ArrayList<Speaker> speaker_list;
	private SpeakerListAdapter speakerlist_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=getDimensionsOfScreen();
		setActivityTheme(height);
		setContentView(R.layout.speakerlist);
		getSpeakerList();
		setUpView();
	}
	
	private void setUpView() {
		
		speaker_listview=(ListView)findViewById(R.id.sessionlist);
		speakerlist_adapter=new SpeakerListAdapter(this, speaker_list, height);
		speaker_listview.setAdapter(speakerlist_adapter);
	}
	
	private void getSpeakerList() {
		
	}
	
	private int getDimensionsOfScreen(){
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;

	}
	
	private void setActivityTheme(int height){
		
		
		if(height<=320){
			setTheme(R.style.tagList_low);
		}
		else if(height<=480){
			setTheme(R.style.tagList_Medium);
		}
		else if(height<=800){
			setTheme(R.style.tagList_High);
		}
		else{
			setTheme(R.style.tagList_XHigh);
		}
		
	}
}
