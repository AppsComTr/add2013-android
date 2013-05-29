package org.gdgankara.app.activities;

import org.gdgankara.app.adapeters.ProgramFragmentAdapter;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;
import org.gdgankara.app.R;
import com.viewpagerindicator.TitlePageIndicator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.ImageView;

public class ProgramActivity extends FragmentActivity{ 
	
	private ViewPager viewpager;
	private TitlePageIndicator title_indicator;
	private int height;
	private TabListener tabListener;
	private ProgramFragmentAdapter program_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		height=Util.device_height;
		setActivityTheme();
		setContentView(R.layout.program);
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
		
		program_adapter = new ProgramFragmentAdapter(getSupportFragmentManager());

		viewpager = (ViewPager)findViewById(R.id.program_pager);
		viewpager.setAdapter(program_adapter);

		title_indicator = (TitlePageIndicator)findViewById(R.id.title_indicator);
		title_indicator.setViewPager(viewpager);
		
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
