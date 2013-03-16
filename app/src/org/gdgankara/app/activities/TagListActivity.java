package org.gdgankara.app.activities;

import java.util.ArrayList;
import java.util.Locale;
import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.TagListAdapter;
import org.gdgankara.app.io.TagHandler;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

public class TagListActivity extends Activity{
	
	private ArrayList<String> tag_list;
	private ListView tag_listview;
	private ArrayAdapter<String> taglist_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityTheme(getDimensionsOfScreen());
		setContentView(R.layout.taglist);
		tag_list=Util.TagList;
		setUpView();
		childItemsActive();
		
	}

	private void setUpView(){
		tag_listview=(ListView)findViewById(R.id.taglist);
		taglist_adapter=new TagListAdapter(this, tag_list);
		tag_listview.setAdapter(taglist_adapter);
	}
	
	private void childItemsActive() {
		tag_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				startSessionList(arg1);
			}
			
		});
	}
	
	private void startSessionList(View view){
		Intent intent=new Intent(this,SessionListActivity.class);
		Bundle b=new Bundle();
		String tag_name=(String)((TextView)view.findViewById(R.id.tag_name)).getText();
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
