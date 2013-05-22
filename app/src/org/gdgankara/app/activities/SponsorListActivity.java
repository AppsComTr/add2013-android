package org.gdgankara.app.activities;

import java.util.ArrayList;
import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.SponsorListAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Sponsor;
import org.gdgankara.app.utils.Util;
import org.gdgankara.app.utils.Util2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class SponsorListActivity extends Activity{

	private ArrayList<Sponsor> sponsor_list;
	private ListView sponsor_listview;
	private SponsorListAdapter sponsorlist_adapter;
	private TabListener tabListener;
	private ArrayList<View> view_list;
	private LayoutInflater inflater ;
	private TextView text;
	private View view;
	private ImageView imageview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityTheme(Util.device_height);
		setContentView(R.layout.sponsorlist);
		sponsor_list=Util2.SponsorList;
		viewListHazirla();
		setUpView();
		tabAktif();
	}
	
	private void viewListHazirla() {
		
		view_list=new ArrayList<View>();
		inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String group_name="";
		int group_size=0;
		int size=sponsor_list.size();
		for(int i=0;i<size;i++){
			if(!group_name.equals(sponsor_list.get(i).getCategory())){ //Yeni grup var
				group_size++;
				view=inflater.inflate(R.layout.group_of_sponsorlist, null, false);
				group_name=sponsor_list.get(i).getCategory();
				text=(TextView)view.findViewById(R.id.group_name_sponsorlist);
				text.setText(group_name);
				imageview=(ImageView)view.findViewById(R.id.group_image_sponsorlist);
				if(group_name.equals("Platin Sponsor")){
					imageview.setImageDrawable(getResources().getDrawable(R.drawable.platin_sponsor));
				}
				if(group_name.equals("Gümüþ Sponsor")){
					imageview.setImageDrawable(getResources().getDrawable(R.drawable.silver_sponsor));			
				}
				if(group_name.equals("Banka ve Finans Sponsoru")){
					imageview.setImageDrawable(getResources().getDrawable(R.drawable.finance_sponsor));
				}
				if(group_name.equals("Sponsorlar")){
					imageview.setImageDrawable(getResources().getDrawable(R.drawable.communication_sponsor));
				}
				
				
				if(group_size==1){
					text.setTextSize(22);
				}
				else if(group_size==2){
					text.setTextSize(20);
				}
				else if(group_size==3){
					text.setTextSize(18);
				}
				else if(group_size==4){
					text.setTextSize(16);
				}
				else{
					text.setTextSize(15);
				}
				view_list.add(view);
				
			}
			
			view=inflater.inflate(R.layout.child_of_sponsorlist, null, false);
			imageview=(ImageView)view.findViewById(R.id.child_image_sponsorlist);
			goToLink(imageview,sponsor_list.get(i).getLink());
			view_list.add(view);
		}
	}

	private void goToLink(ImageView imageview2, final String temp) {
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( temp ) );
			    startActivity( browse );
			}
		});
	}

	private void setUpView(){
		sponsor_listview=(ListView)findViewById(R.id.sponsorlist);
		sponsorlist_adapter=new SponsorListAdapter(view_list);
		sponsor_listview.setAdapter(sponsorlist_adapter);
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		
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
