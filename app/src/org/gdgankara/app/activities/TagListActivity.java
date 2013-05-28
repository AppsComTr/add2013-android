package org.gdgankara.app.activities;

import java.util.ArrayList;
import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.TagListAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class TagListActivity extends Activity{
	
	private ArrayList<String> tag_list;
	private ListView tag_listview;
	private TagListAdapter taglist_adapter;
	private TabListener tabListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityTheme(Util.device_height);
		setContentView(R.layout.taglist);
		tag_list=Util.TagList;
		setUpView();
		childItemsActive();
		tabAktif();
	}
	
	

	private void setUpView(){
		tag_listview=(ListView)findViewById(R.id.taglist);
		taglist_adapter=new TagListAdapter(this, tag_list);
		tag_listview.setAdapter(taglist_adapter);
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
	}
	
	private void childItemsActive() {
		tag_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				startSessionList(arg2);
			}
			
		});
	}
	
	private void startSessionList(int index){
		Intent intent=new Intent(this,SessionListActivity.class);
		Bundle b=new Bundle();
		String tag_name=tag_list.get(index);
		b.putString("tag", tag_name);
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
