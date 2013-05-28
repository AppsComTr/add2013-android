package org.gdgankara.app.activities;

import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class EmployeeIdActivity extends Activity{
	
	
	private int height;
	private TabListener tabListener;
	private String message;
	private String[] temp;
	private TextView text;
	private ImageView imageview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=Util.device_height;
		setActivityTheme();
		setContentView(R.layout.idpage);
		message=this.getIntent().getExtras().getString("SCAN_RESULT");
		temp=message.split("<#>");
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
		text=(TextView)findViewById(R.id.idpage_name);
		text.setText(temp[0]+" "+temp[1]);
		text=(TextView)findViewById(R.id.idpage_email);
		text.setText(temp[2]);
		text=(TextView)findViewById(R.id.idpage_website);
		text.setText(Html.fromHtml("<a href=\""+temp[3]+"\">" +temp[3]+ "</a>"));
		text.setMovementMethod(LinkMovementMethod.getInstance());
		text=(TextView)findViewById(R.id.idpage_telephone);
		text.setText(temp[4]);
		text=(TextView)findViewById(R.id.idpage_company);
		text.setText(temp[5]);
		text=(TextView)findViewById(R.id.idpage_interested);
		text.setText(temp[6]);
		imageview=(ImageView)findViewById(R.id.add_contact_button);
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					addContact();
			}
		});
		if(Util.getDefaultLanguage().equals("tr")){
			imageview.setImageDrawable(getResources().getDrawable(R.drawable.kisi_ekle));
		}
		else{
			imageview.setImageDrawable(getResources().getDrawable(R.drawable.add_contact));
		}
	}
	
	private void addContact() {
		Intent addContactIntent = new Intent(Intent.ACTION_INSERT);
		startActivity(addContactIntent);
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
