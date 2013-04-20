package org.gdgankara.app.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.model.Sponsor;
import org.gdgankara.app.utils.Util;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SponsorPageActivity  extends Activity{

	private Sponsor sponsor;
	private ArrayList<Sponsor> total_sponsor_list;
	private TextView text;
	private Long sponsor_id;
	private TabListener tabListener;
	private int height,lang;
	private LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=Util.device_height;
		setActivityTheme();
		setContentView(R.layout.sponsorpage);
		sponsor_id=this.getIntent().getExtras().getLong("id");
		lang=Util.getDefaultLanguage().equals("tr")?1:0;
		getSponsorList();
		findSponsor();
		setUpView();
		tabAktif();
		
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		
	}
	
	private void setUpView() {
		
		
		/*Sponsor logosu : view deðiþkeni ile ulaþ ve logoyu set et */
		
		/*Sponsor adý*/
		text=(TextView)findViewById(R.id.sponsorpage_name);
		text.setText(sponsor.getName());
		setSponsorNameTextSize(text);
		
		/*Sponsor Özet*/
		text=(TextView)findViewById(R.id.sponsor_description);
		text.setText(sponsor.getDescription());
		
		/*Baþlýk Dili*/
		text=(TextView)findViewById(R.id.sponsor_link_title);
		text.setText(lang==1?"Ayrýntýlý bilgi için":"Fore more details");
		
		/*Sponsor web sitesi*/
		text=(TextView)findViewById(R.id.sponsorpage_link);
		text.setText(Html.fromHtml("<a href=\""+sponsor.getLink()+"\">" +sponsor.getLink()+ "</a>"));
		text.setMovementMethod(LinkMovementMethod.getInstance());
		
	}

	
	private void findSponsor(){
		for(Sponsor temp:total_sponsor_list){
			if(temp.getId()==sponsor_id){
				sponsor=temp;
				break;
			}
		}
	}
	
	private void getSponsorList(){
		total_sponsor_list=Util.SponsorList;
	}
	
	
	private void setSponsorNameTextSize(TextView text) {		
		
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
