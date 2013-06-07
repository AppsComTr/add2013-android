package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.SessionListAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.utils.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FavoriteListActivity extends BaseActivity{
	
	private ArrayList<Session> total_session_list,favorite_session_list;
	private ListView session_listview;
	private SessionListAdapter sessionlist_adapter;
	private LinearLayout sessionlist_layout;
	private LayoutInflater inflater ;
	private View view;
	private int pressed_back_button;
	private org.gdgankara.app.listeners.TabListener tabListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		overridePendingTransition(0, 0);
		setActivityTheme(Util.device_height);
		setContentView(R.layout.sessionlist);
		total_session_list=Util.SessionList;
		findFavorites();
		setUpView();
		tabAktif();
		pressed_back_button=0;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(pressed_back_button==1){
			findFavorites();
			if(favorite_session_list.size()!=0){
				sessionlist_adapter=new SessionListAdapter(this, favorite_session_list, Util.device_height);
				session_listview.setAdapter(sessionlist_adapter);
			}
			else{
				inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				session_listview.setVisibility(View.GONE);
				view=inflater.inflate(R.layout.blank_list, null, false);
				((TextView)view.findViewById(R.id.blanklist_text)).setText(getResources().getString(R.string.no_fav_session));
				sessionlist_layout.addView(view);
			}
		}
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
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
		Long id=favorite_session_list.get(index).getId();
		b.putLong("id", id);
		intent.putExtras(b);
		pressed_back_button=1;
		this.startActivity(intent);
	}

	private void setUpView() {
		session_listview=(ListView)findViewById(R.id.sessionlist);
		sessionlist_layout=(LinearLayout)findViewById(R.id.sessionlist_layout);
		if(favorite_session_list.size()==0){		
			inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			session_listview.setVisibility(View.GONE);
			view=inflater.inflate(R.layout.blank_list, null, false);
			((TextView)view.findViewById(R.id.blanklist_text)).setText(getResources().getString(R.string.no_fav_session));
			sessionlist_layout.addView(view);
		}
		else{
			sessionlist_adapter=new SessionListAdapter(this, favorite_session_list, Util.device_height);
			session_listview.setAdapter(sessionlist_adapter);
			childItemsActive();
		}
		
	}

	private void findFavorites() {
		favorite_session_list=new ArrayList<Session>();
			for(Session session:total_session_list){
				if(session.isFavorite()){
					favorite_session_list.add(session);
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
