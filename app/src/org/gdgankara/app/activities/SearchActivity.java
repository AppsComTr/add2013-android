package org.gdgankara.app.activities;

import java.util.ArrayList;
import java.util.List;

import org.gdgankara.app.R;
import org.gdgankara.app.R.layout;
import org.gdgankara.app.R.menu;

import android.os.Bundle;
import android.app.Activity;
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
	private ArrayList<String> tags, searchResult;
	private ImageView searchButton;
	private org.gdgankara.app.io.TagHandler tagHand;
	private String search;
	private ListView searchList;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		searchText = (AutoCompleteTextView) findViewById(R.id.searchText);
		searchButton = (ImageView) findViewById(R.id.searchActivityButton);
		searchList = (ListView) findViewById(R.id.searchResultList);
		
		tagHand = new org.gdgankara.app.io.TagHandler(this);
		searchResult = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,searchResult);
		
		tags = tagHand.getTagList("tr");
		
				
		searchText.addTextChangedListener(this);
		searchText.setThreshold(1);
		searchText.setAdapter(new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, tags));
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				search = searchText.getText().toString();
				if(search !=  "" && search != null){
					adapter.add(search + " listeye eklendi");
					searchList.setAdapter(adapter);
				}
			}
		});
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
