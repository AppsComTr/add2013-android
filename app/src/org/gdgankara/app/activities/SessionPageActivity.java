package org.gdgankara.app.activities;

import java.util.ArrayList;
import java.util.List;

import org.gdgankara.app.R;
import org.gdgankara.app.customview.EllipsizingTextView;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.utils.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class SessionPageActivity extends BaseActivity implements OnClickListener{
	
	private ArrayList<Session> total_session_list;
	private ArrayList<Speaker> total_speaker_list,filtered_speaker_list;
	private View[] speaker_viewlist;
	private LinearLayout speakerlist_layout;
	private View view;
	private LayoutInflater inflater;
	private TextView text;
	private Long session_id;
	private Session session;
	private int height,lang,features_text_size,size,pressed_back_button;
	private ImageView favorite_star;
	private TabListener tabListener;
	private ImageView speaker_image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		overridePendingTransition(0, 0);
		height=Util.device_height;
		setActivityTheme();
		setFeaturesTextSize();
		setContentView(R.layout.sessionpage);
		session_id=this.getIntent().getExtras().getLong("id");
		lang=Util.getDefaultLanguage().equals("tr")?1:0;
		getLists();
		findSession();
		findSpeakers();
		setUpView();
		tabAktif();
		pressed_back_button=0;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(pressed_back_button==1){
			if(session.isFavorite()){
				((ImageView)findViewById(R.id.favorite_session)).setImageDrawable(getResources().getDrawable(R.drawable.favorite_button_active));
			}
			else{
				((ImageView)findViewById(R.id.favorite_session)).setImageDrawable(getResources().getDrawable(R.drawable.favorite_button_passive));
			}
		}
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
	}
	
	
	private void findSpeakers() {
		filtered_speaker_list=new ArrayList<Speaker>();
		List<Long> speakerIdList=session.getSpeakerIDList();
		for(Long id:speakerIdList){
			for(Speaker speaker:total_speaker_list){
				if(id==speaker.getId()){
					filtered_speaker_list.add(speaker);
					break;
				}
			}
		}
		
	}

	private void setUpView() {
		String temp;
		
		//Oturum ba�ligi
		text=(TextView)findViewById(R.id.session_title);
		text.setText(session.getTitle());
		setSessionTitleTextSize(text);
		
		//Oturum yer zaman bilgisi
		text=(TextView)findViewById(R.id.session_features);
		temp=session.getDay()==14?getResources().getString(R.string.friday):getResources().getString(R.string.saturday);
		temp+=" "+session.getStart_hour()+" - "+session.getEnd_hour()+" | ";
		temp+=lang==1?session.getHall()+getResources().getString(R.string.hall):getResources().getString(R.string.hall)+ session.getHall();
		text.setText(temp);
		text.setTextSize(features_text_size);
		
		//Oturum ozeti
		text=(TextView)findViewById(R.id.session_description);
		text.setText(session.getDescription());
		
		//Ba�l�klar�n dili
		text=(TextView)findViewById(R.id.speaker_title);
		text.setText(getResources().getString(R.string.speakers));
		
		//favori y�ld�z� butonu
		favorite_star=(ImageView)findViewById(R.id.favorite_session);
		if(session.isFavorite()){
			favorite_star.setImageDrawable(getResources().getDrawable(R.drawable.favorite_button_active));
		}
		favorite_star.setOnClickListener(this);
		
		//Oturum konusmacilari
		speakerlist_layout=(LinearLayout)findViewById(R.id.sessionpage_speakerlist);
		addSpeakersToLayout();
		
		
	}

	private void addSpeakersToLayout() {
		String temp;
		inflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		size=filtered_speaker_list.size();
		speaker_viewlist=new View[size];
		for(int i=0;i<size;i++){
			view=inflater.inflate(R.layout.child_of_speakerlist, null, false);
			text=(TextView)view.findViewById(R.id.speaker_name);
			text.setText(filtered_speaker_list.get(i).getName());
			text=(EllipsizingTextView)view.findViewById(R.id.speaker_bio);
			temp=filtered_speaker_list.get(i).getBiography();
			text.setText(temp==null?"":temp);
			text.setMaxLines(3);
			text.setTextSize(features_text_size);
			speaker_image=(ImageView)view.findViewById(R.id.speaker_image);
			UrlImageViewHelper.setUrlDrawable(speaker_image, filtered_speaker_list.get(i).getPhoto(),R.drawable.loading);
			speakerlist_layout.addView(view);
			speaker_viewlist[i]=view;
			view.setOnClickListener(this);
			if(i<size-1){
				speakerlist_layout.addView(inflater.inflate(R.layout.general_list_divider, null,false));
			}
		}
	}

	private void setFeaturesTextSize() {
		if(height<=320){
			features_text_size=10;
		}
		else if(height<=480){
			features_text_size=11;
		}
		else if(height<=800){
			features_text_size=12;
		}
		else{
			features_text_size=13;
		}
	}

	private void setSessionTitleTextSize(TextView text) {		
			if(height<=320){
				text.setTextSize(15);
			}
			else if(height<=480){
				text.setTextSize(16);
			}
			else if(height<=800){
				text.setTextSize(17);
			}
			else{
				text.setTextSize(18);
			}
		
	}

	private void findSession(){
		for(Session temp:total_session_list){
			if(temp.getId()==session_id){
				session=temp;
				break;
			}
		}
	}

	private void getLists(){
		total_session_list=Util.SessionList;
		total_speaker_list=Util.SpeakerList;
	}
	
	private void setActivityTheme(){
		
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
	

	@Override
	public void onClick(View v) {

		if(v.getId()==R.id.favorite_session){
			if(session.isFavorite()){
				session.setFavorite(false);
				favorite_star.setImageDrawable(getResources().getDrawable(R.drawable.favorite_button_passive));
				Util.removeSessionFavorites(this,session_id);
			}
			else{
				session.setFavorite(true);
				favorite_star.setImageDrawable(getResources().getDrawable(R.drawable.favorite_button_active));
				Util.addSessionFavorites(this,session_id);
			}
		}
		
		else{
		
			int i;
			for(i=0;i<size;i++){
				if(speaker_viewlist[i]==v){
					Intent intent=new Intent(this,SpeakerPageActivity.class);
					Bundle b=new Bundle();
					Long id=filtered_speaker_list.get(i).getId();
					b.putLong("id", id);
					intent.putExtras(b);
					pressed_back_button=1;
					this.startActivity(intent);
					break;
				}
			}
		}
		
	}
	
}
