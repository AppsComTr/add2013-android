package org.gdgankara.app.activities;

import java.util.ArrayList;
import java.util.Locale;

import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.SessionListAdapter;
import org.gdgankara.app.io.SessionsHandler;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SessionListActivity extends Activity{
	
	private ArrayList<Session> total_session_list,filtered_session_list;
	private ListView session_listview;
	private SessionListAdapter sessionlist_adapter;
	private String aranan_tag;
	private int height,pressed_back_button;
	private TabListener tabListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=getDimensionsOfScreen();
		setActivityTheme(height);
		setContentView(R.layout.sessionlist);
		aranan_tag=this.getIntent().getExtras().getString("tag");
		total_session_list=Util.SessionList;
		sessionListFilter();
		setUpView();
		childItemsActive();
		tabAktif();
		pressed_back_button=0;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(pressed_back_button==1){
			sessionlist_adapter=new SessionListAdapter(this, filtered_session_list, height);
			session_listview.setAdapter(sessionlist_adapter);
		}
		
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		
	}

	private void setUpView() {
		
		session_listview=(ListView)findViewById(R.id.sessionlist);
		sessionlist_adapter=new SessionListAdapter(this, filtered_session_list, height);
		session_listview.setAdapter(sessionlist_adapter);
	}
	
	private void childItemsActive() {
		session_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				startSessionPage(arg2);
			}
			
		});
	}
	
	private void startSessionPage(int index){
		Intent intent=new Intent(this,SessionPageActivity.class);
		Bundle b=new Bundle();
		Long id=filtered_session_list.get(index).getId();
		b.putLong("id", id);
		intent.putExtras(b);
		pressed_back_button=1;
		this.startActivity(intent);
	}

	private int getDimensionsOfScreen(){
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;

	}
	
	private void sessionListFilter() {
		int i,j;
		String[] tags;
		filtered_session_list=new ArrayList<Session>();
		int size=total_session_list.size();
		int size2;
		for(i=0;i<size;i++){
			tags=total_session_list.get(i).getTags().split(",");
			size2=tags.length;
			for(j=0;j<size2;j++){
				if(tags[j].equals(aranan_tag)){
					filtered_session_list.add(total_session_list.get(i));
				}
			}
		}
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
