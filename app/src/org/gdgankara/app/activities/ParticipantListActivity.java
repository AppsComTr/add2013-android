package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.adapeters.ParticipantListAdapter;
import org.gdgankara.app.adapeters.SessionListAdapter;
import org.gdgankara.app.adapeters.SpeakerListAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ParticipantListActivity extends Activity{
	
	private ArrayList<String> ParticipantList;
	private ListView participant_listview;
	private ParticipantListAdapter participantlist_adapter;
	private TabListener tabListener;
	private ImageView imageview;
	private TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityTheme(Util.device_height);
		setContentView(R.layout.participantlist);
		ParticipantList=Util.ParticipantList;
		setUpView();
		childItemsActive();
		tabAktif();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(Util.qr_state==0){
			Toast.makeText(this,R.string.invalid_qr_code,Toast.LENGTH_SHORT).show();
			Util.qr_state=1;
		}
		if(ParticipantList.size()>0){
			text.setVisibility(View.INVISIBLE);
			participantlist_adapter.notifyDataSetChanged();
		}
	}
	
	private void setUpView() {
		
		participant_listview=(ListView)findViewById(R.id.participantlist);
		participantlist_adapter=new ParticipantListAdapter(this, ParticipantList, Util.device_height);
		participant_listview.setAdapter(participantlist_adapter);
		
		imageview=(ImageView)findViewById(R.id.participantlist_scan_button);
		if(Util.getDefaultLanguage().equals("tr")){
			imageview.setImageDrawable(getResources().getDrawable(R.drawable.qrscantr));
		}
		else{
			imageview.setImageDrawable(getResources().getDrawable(R.drawable.qrscanen));
		}
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startQRDecoder();
			}

		});
		
		text=(TextView)findViewById(R.id.participantlist_decodeddata_ready);
		if(ParticipantList.size()==0){
			text.setText(R.string.ready_decoded_qr);
		}
	}
	
	private void startQRDecoder(){
		Intent i = new Intent(this, DecoderActivity.class);
		i.putExtra("SCAN_MODE", "QR_CODE_MODE");
		i.putExtra("return-data", true);
		startActivity(i);
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
	}
	
	private void childItemsActive() {
		participant_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				startParticipantPage(arg2);
			}		
			
		});
	}
	
	private void startParticipantPage(int index){
		Intent intent=new Intent(this,ParticipantIdActivity.class);
		Bundle b=new Bundle();
		String result=ParticipantList.get(index);
		b.putString("SCAN_RESULT", result);
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
