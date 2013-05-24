package org.gdgankara.app.activities;

import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class EmployeeIdActivity extends Activity{
	
	
	private int height;
	private TabListener tabListener;
	private String message;
	private String[] temp;
	private TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=Util.device_height;
		setActivityTheme();
		setContentView(R.layout.speakerpage);
		message=this.getIntent().getExtras().getString("SCAN_RESULT");
		temp=message.split("|");
		setUpViews();
		tabAktif();
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
	}
	
	private void setUpViews() {
		text=(TextView)findViewById(R.id.name_idpage);
		text.setText(temp[0]);
		text=(TextView)findViewById(R.id.email_idpage);
		text.setText(temp[1]);
		text=(TextView)findViewById(R.id.tel_idpage);
		text.setText(temp[2]);
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

}
