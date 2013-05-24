package org.gdgankara.app.activities;

import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.utils.Util;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AnnouncementPageActivity extends Activity{
	
	private Announcement announcement;
	private ArrayList<Announcement> announcement_list;
	private TextView text;
	private Long announcement_id;
	private ImageView imageView;
	private TabListener tabListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityTheme(Util.device_height);
		setContentView(R.layout.announcementpage);
		announcement_id=this.getIntent().getExtras().getLong("id");
		announcement_list=Util.AnnouncementList;
		findAnnouncement();
		setUpView();
		tabAktif();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		tabListener.checkQRState();
	}
	
	private void setUpView() {
	
		text=(TextView)findViewById(R.id.announcementpage_title);
		text.setText(announcement.getTitle());
		
		text=(TextView)findViewById(R.id.announcementpage_description);
		text.setText(announcement.getDescription());
		
		imageView=(ImageView)findViewById(R.id.news_image);
		UrlImageViewHelper.setUrlDrawable(imageView, announcement.getImage(),R.drawable.loading);
		
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
	}

	private void findAnnouncement() {
		for(Announcement anno:announcement_list){
			if(anno.getId()==announcement_id){
				announcement=anno;
				break;
			}
		}
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
