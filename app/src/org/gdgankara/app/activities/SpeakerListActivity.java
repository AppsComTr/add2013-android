package org.gdgankara.app.activities;

import java.util.ArrayList;
import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.SpeakerListAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.utils.Util;
import org.gdgankara.app.utils.Util2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SpeakerListActivity extends Activity{

	private ListView speaker_listview;
	private ArrayList<Speaker> speaker_list;
	private SpeakerListAdapter speakerlist_adapter;
	private TabListener tabListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityTheme(Util.device_height);
		setContentView(R.layout.speakerlist);
		getSpeakerList();
		setUpView();
		childItemsActive();
		tabAktif();
	}
	
	private void setUpView() {
		
		speaker_listview=(ListView)findViewById(R.id.speakerlist);
		speakerlist_adapter=new SpeakerListAdapter(this, speaker_list, Util.device_height);
		speaker_listview.setAdapter(speakerlist_adapter);
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		
	}
	
	private void getSpeakerList() {
		speaker_list=Util2.SpeakerList;
	}
	
	
	private void childItemsActive() {
		speaker_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				startSpeakerPage(arg2);
			}		
			
		});
	}
	
	private void startSpeakerPage(int index) {
		Intent intent=new Intent(this,SpeakerPageActivity.class);
		Bundle b=new Bundle();
		Long id=speaker_list.get(index).getId();
		b.putLong("id", id);
		intent.putExtras(b);
		this.startActivity(intent);
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
