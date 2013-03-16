package org.gdgankara.app.activities;

import java.util.ArrayList;
import java.util.List;

import org.gdgankara.app.R;
import org.gdgankara.app.R.layout;
import org.gdgankara.app.R.menu;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

public class SearchActivity extends Activity implements TextWatcher {

	private AutoCompleteTextView searchText;
	private ArrayList<String> tags;
	private ImageView searchButton;
	private org.gdgankara.app.io.TagHandler tagHand;
	private String search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		searchText = (AutoCompleteTextView) findViewById(R.id.searchText);
		searchButton = (ImageView) findViewById(R.id.searchActivityButton);
		
		tagHand = new org.gdgankara.app.io.TagHandler(this);
		tags = tagHand.getTagList("tr");
		
				
		searchText.addTextChangedListener(this);
		searchText.setThreshold(1);
		searchText.setAdapter(new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, tags));
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				search = searchText.getText().toString();
				if(search !=  "" && search != null){
					startSessionList();
				}
				
			}
		});
	}

	private void startSessionList(){
		Intent intent=new Intent(this,SessionListActivity.class);
		Bundle b=new Bundle();
		b.putString("tag", search);
		intent.putExtras(b);
		startActivity(intent);
		finish();
	}
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

}
