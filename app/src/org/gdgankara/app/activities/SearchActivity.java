package org.gdgankara.app.activities;

import java.util.ArrayList;
import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.SessionListAdapter;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.utils.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity implements TextWatcher {

	private AutoCompleteTextView searchText;
	private ArrayList<String> tags;
	private ArrayList<Session> total_session_list, filtered_session_list;
	private ImageView searchButton;
	private String search;
	private ListView search_listview;
	private SessionListAdapter searchlist_adapter;
	private int pressed_back_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		searchText = (AutoCompleteTextView) findViewById(R.id.searchText);
		searchButton = (ImageView) findViewById(R.id.search_button_2);

		tags = Util.TagList;

		setActivityTheme(Util.device_height);

		searchText.addTextChangedListener(this);
		searchText.setThreshold(0);
		searchText.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, tags));

		total_session_list = Util.SessionList;
		filtered_session_list = new ArrayList<Session>();
		setUpView();
		pressed_back_button=0;
		childItemsActive();
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				search = searchText.getText().toString();
				sessionListFilter();
				searchlist_adapter.clear();
				for (int i = 0; i < filtered_session_list.size(); i++) {
					searchlist_adapter.add(filtered_session_list.get(i));
				}

				searchlist_adapter.notifyDataSetChanged();

			}
		});
		
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(pressed_back_button==1){
			searchlist_adapter=new SessionListAdapter(this, filtered_session_list, Util.device_height);
			search_listview.setAdapter(searchlist_adapter);
		}
		
	}
	
	private void childItemsActive() {
		search_listview.setOnItemClickListener(new OnItemClickListener() {

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

	private void setUpView() {

		search_listview = (ListView) findViewById(R.id.searchListView);
		searchlist_adapter = new SessionListAdapter(this,
				filtered_session_list, Util.device_height);
		search_listview.setAdapter(searchlist_adapter);
	}


	private void sessionListFilter() {
		int i, j;
		String[] tags;
		filtered_session_list = new ArrayList<Session>();
		int size = total_session_list.size();
		int size2;
		for (i = 0; i < size; i++) {
			tags = total_session_list.get(i).getTags().split(",");
			size2 = tags.length;
			for (j = 0; j < size2; j++) {
				if (tags[j].equals(search)) {
					filtered_session_list.add(total_session_list.get(i));
				}
			}
		}
	}

	private void setActivityTheme(int height) {

		if (height <= 320) {
			setTheme(R.style.tagList_low);
		} else if (height <= 480) {
			setTheme(R.style.tagList_Medium);
		} else if (height <= 800) {
			setTheme(R.style.tagList_High);
		} else {
			setTheme(R.style.tagList_XHigh);
		}

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
