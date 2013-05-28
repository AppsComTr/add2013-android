package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.ParticipantListAdapter;
import org.gdgankara.app.adapeters.SessionListAdapter;
import org.gdgankara.app.adapeters.SpeakerListAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ParticipantListActivity extends Activity{
	
	private ArrayList<String> ParticipantList;
	private ListView participant_listview;
	private ParticipantListAdapter participantlist_adapter;
	private TabListener tabListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityTheme(Util.device_height);
		setContentView(R.layout.speakerlist);
		ParticipantList=Util.ParticipantList;
		setUpView();
		childItemsActive();
		tabAktif();
	}
	
	private void setUpView() {
		
		participant_listview=(ListView)findViewById(R.id.participantlist);
		participantlist_adapter=new ParticipantListAdapter(this, ParticipantList, Util.device_height);
		participant_listview.setAdapter(participantlist_adapter);
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
	}
	
	private void childItemsActive() {
		participant_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				startParticipantPage(arg2);
			}		
			
		});
	}
	
	private void startParticipantPage(int index){
		Intent intent=new Intent(this,ParticipantIdActivity.class);
		Bundle b=new Bundle();
		String result=ParticipantList.get(index);
		b.putString("SCAN_RESULT", result);
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
