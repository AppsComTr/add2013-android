package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.TagListAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SponsorListActivity extends Activity{

	private ArrayList<String> sponsor_list;
	private ListView sponsor_listview;
	private TagListAdapter sponsorlist_adapter;
	private TabListener tabListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityTheme(getDimensionsOfScreen());
		setContentView(R.layout.sponsorlist);
		//tag_list=Util.TagList; Tam burada sponsor listesini iste
		setUpView();
		childItemsActive();
		tabAktif();
	}
	
	private void setUpView(){
		sponsor_listview=(ListView)findViewById(R.id.sponsorlist);
		sponsorlist_adapter=new TagListAdapter(this, sponsor_list);
		sponsor_listview.setAdapter(sponsorlist_adapter);
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		
	}
	
	private void childItemsActive() {
		sponsor_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				startSponsorPage(arg2);
			}
			
		});
	}
	
	private void startSponsorPage(int index){
		Intent intent=new Intent(this,SponsorPageActivity.class);
		Bundle b=new Bundle();
		String tag_name=sponsor_list.get(index);
		b.putString("tag", tag_name);
		intent.putExtras(b);
		this.startActivity(intent);
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
