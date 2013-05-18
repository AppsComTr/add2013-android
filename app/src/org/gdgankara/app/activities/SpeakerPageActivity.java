package org.gdgankara.app.activities;

import java.util.ArrayList;
import java.util.List;
import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.utils.Util;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpeakerPageActivity extends Activity implements OnClickListener{

	private Long speaker_id;
	private ArrayList<Speaker> total_speaker_list;
	private ArrayList<Session> total_session_list,filtered_session_list;
	private View[] session_viewlist;
	private TextView text;
	private LinearLayout sessions_layout;
	private View view;
	private LayoutInflater inflater;
	private Speaker speaker;
	private int height,lang,features_text_size,size,pressed_back_button;
	private TabListener tabListener;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=Util.device_height;
		setActivityTheme();
		setFeaturesTextSize();
		setContentView(R.layout.speakerpage);
		speaker_id=this.getIntent().getExtras().getLong("id");
		lang=Util.getDefaultLanguage().equals("tr")?1:0;
		getLists();
		findSpeaker();
		findSessions();
		setUpView();
		tabAktif();
		pressed_back_button=0;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(pressed_back_button==1){
			for(int i=0;i<size;i++){
				if(filtered_session_list.get(i).isFavorite()){
					session_viewlist[i].findViewById(R.id.isfavorite).setVisibility(View.VISIBLE);
				}
				else{
					session_viewlist[i].findViewById(R.id.isfavorite).setVisibility(View.GONE);
				}
			}
		}
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		
	}
	
	private void setUpView() {
		
		
		//Konusmaci adi
		text=(TextView)findViewById(R.id.speakerpage_name);
		text.setText(speaker.getName());
		setSpeakerNameTextSize(text);
		
		//Konusmaci unvan
		text=(TextView)findViewById(R.id.speakerpage_job);
//		text.setText(speaker.getTitle());
		text.setTextSize(features_text_size);
		
		//Konusmaci biografi
		text=(TextView)findViewById(R.id.speakerpage_bio);
		text.setText(speaker.getBiography());
		
		//Oturumlari basligi
		text=(TextView)findViewById(R.id.speakerpage_sessiontitle);
		text.setText(lang==1?"Oturumlar":"Sessions");
		
		//Oturumlari ekle
		sessions_layout=(LinearLayout)findViewById(R.id.speakerpage_sessionlayout);
		addSessionsToLayout();
		
		//Profil basligi
		text=(TextView)findViewById(R.id.speakerpage_profiletitle);
		text.setText(lang==1?"Baðlantýlar":"Contact");
		
		//Baðlantýlar
		/*String temp = "---";
		text=(TextView)findViewById(R.id.speakerpage_personalsite_link);
		temp=speaker.getBlog();
		if(!temp.equals("null")){
			text.setText(Html.fromHtml("<a href=\""+temp+"\">" +temp+ "</a>"));
			text.setMovementMethod(LinkMovementMethod.getInstance());
		}
		else{
			text.setText("---");
		}
		
		
		text=(TextView)findViewById(R.id.speakerpage_gplussite_link);
		temp=speaker.getGplus();
		if(!temp.equals("null")){
			text.setText(Html.fromHtml("<a href=\""+temp+"\">" +temp+ "</a>"));
			text.setMovementMethod(LinkMovementMethod.getInstance());
		}
		else{
			text.setText("---");
		}
		
		text=(TextView)findViewById(R.id.speakerpage_facebooksite_link);
		temp=speaker.getFacebook();
		if(!temp.equals("null")){
			text.setText(Html.fromHtml("<a href=\""+temp+"\">" +temp+ "</a>"));
			text.setMovementMethod(LinkMovementMethod.getInstance());
		}
		else{
			text.setText("---");
		}
		
		text=(TextView)findViewById(R.id.speakerpage_twittersite_link);
		temp=speaker.getTwitter();
		if(!temp.equals("null")){
			text.setText(Html.fromHtml("<a href=\""+temp+"\">" +temp+ "</a>"));
			text.setMovementMethod(LinkMovementMethod.getInstance());
		}
		else{
			text.setText("---");
		}*/
		
	}
	
	private void addSessionsToLayout() {
		String temp;
		inflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		size=filtered_session_list.size();
		session_viewlist=new View[size];
		for(int i=0;i<size;i++){
			view=inflater.inflate(R.layout.child_of_sessionlist, null, false);
			text=(TextView)view.findViewById(R.id.session_name);
			text.setText(filtered_session_list.get(i).getTitle());
			text=(TextView)view.findViewById(R.id.session_features);
			temp=filtered_session_list.get(i).getDay()==14?lang==1?"Cuma":"Friday":lang==1?"Cumartesi":"Saturday";
			temp+=" "+filtered_session_list.get(i).getStart_hour()+" - "+filtered_session_list.get(i).getEnd_hour()+" | ";
			temp+=lang==1?filtered_session_list.get(i).getHall()+" Salonu":"Hall "+ filtered_session_list.get(i).getHall();
			text.setText(temp);
			text.setTextSize(features_text_size);
			if(filtered_session_list.get(i).isFavorite()){
				view.findViewById(R.id.isfavorite).setVisibility(View.VISIBLE);
			}
			sessions_layout.addView(view);
			session_viewlist[i]=view;
			view.setOnClickListener(this);
			if(i<size-1){
				sessions_layout.addView(inflater.inflate(R.layout.general_list_divider, null,false));
			}
		}
		
	}

	private void findSessions() {
		filtered_session_list=new ArrayList<Session>();
		List<Long> sessionIdList=speaker.getSessionIDList();
		for(Long id:sessionIdList){
			for(Session session:total_session_list){
				if(id==session.getId()){
					filtered_session_list.add(session);
					break;
				}
			}
		}
	}

	private void findSpeaker() {
		for(Speaker temp:total_speaker_list){
			if(temp.getId()==speaker_id){
				speaker=temp;
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
	
	
	private void setSpeakerNameTextSize(TextView text) {		

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
	
	private void setFeaturesTextSize() {

		if(height<=320){
			features_text_size=9;
		}
		else if(height<=480){
			features_text_size=10;
		}
		else if(height<=800){
			features_text_size=11;
		}
		else{
			features_text_size=12;
		}
	}

	@Override
	public void onClick(View v) {
		int i;
		for(i=0;i<size;i++){
			if(session_viewlist[i]==v){
				Intent intent=new Intent(this,SessionPageActivity.class);
				Bundle b=new Bundle();
				Long id=filtered_session_list.get(i).getId();
				b.putLong("id", id);
				intent.putExtras(b);
				pressed_back_button=1;
				this.startActivity(intent);
				break;
			}
		}
		
	}
	
}
