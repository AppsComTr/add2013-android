package org.gdgankara.app.activities;

import java.util.ArrayList;
import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.map.MapActivity;
import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.utils.Util;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class MainActivity extends BaseActivity implements OnClickListener {

	private ImageView tweetWallButton, programButton, oturumButton,
			sponsorButton, favoriButton, konusmaciButton;
	private ViewFlipper newsFlipper;
	private ImageView araButton, tempImg;
	private TabListener tabListener;
	private ArrayList<Announcement> AnnouncementList;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Util.prepareStaticLists(this);
		setDeviceDimensions();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setUpButtons();
		buttonsActive();
		setUpFlippingImages();
		tabAktif();
		newsFlipper.startFlipping();

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						sleep(1000);
						refreshView();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		thread.start();

	}
	
	private void setUpFlippingImages() {
		AnnouncementList=Util.AnnouncementList;
		int size=AnnouncementList.size();
		for(int i=0;i<size;i++){
			tempImg = new ImageView(this);
			UrlImageViewHelper.setUrlDrawable(tempImg, AnnouncementList.get(i).getImage(),R.drawable.loading);
			newsFlipper.addView(tempImg);
		}
		
	}



	private void setUpButtons() {
		tweetWallButton = (ImageView) findViewById(R.id.tweetWall);
		favoriButton = (ImageView) findViewById(R.id.favori_button);
		sponsorButton = (ImageView) findViewById(R.id.sponsor_button);
		oturumButton = (ImageView) findViewById(R.id.oturum_button);
		programButton = (ImageView) findViewById(R.id.program_button);
		araButton = (ImageView) findViewById(R.id.search_button);
		konusmaciButton = (ImageView) findViewById(R.id.speakers_button);
		newsFlipper = (ViewFlipper) findViewById(R.id.highlights);
		if(Util.getDefaultLanguage().equals("tr")){
			tweetWallButton.setImageDrawable(getResources().getDrawable(R.drawable.tweetwall_tr_selector));
			favoriButton.setImageDrawable(getResources().getDrawable(R.drawable.favori_tr_selector));
			sponsorButton.setImageDrawable(getResources().getDrawable(R.drawable.sponsor_selector));
			oturumButton.setImageDrawable(getResources().getDrawable(R.drawable.oturum_tr_selector));
			programButton.setImageDrawable(getResources().getDrawable(R.drawable.program_tr_selector));
			konusmaciButton.setImageDrawable(getResources().getDrawable(R.drawable.konusmacilar_tr_selector));
		}
		else{
			tweetWallButton.setImageDrawable(getResources().getDrawable(R.drawable.tweetwall_en_selector));
			favoriButton.setImageDrawable(getResources().getDrawable(R.drawable.favori_en_selector));
			sponsorButton.setImageDrawable(getResources().getDrawable(R.drawable.sponsor_selector));
			oturumButton.setImageDrawable(getResources().getDrawable(R.drawable.oturum_en_selector));
			programButton.setImageDrawable(getResources().getDrawable(R.drawable.program_en_selector));
			konusmaciButton.setImageDrawable(getResources().getDrawable(R.drawable.konusmacilar_en_selector));
		}
	}
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		((ImageView)findViewById(R.id.update_button)).setOnClickListener(tabListener);
		((ImageView)findViewById(R.id.qr_decoder_button)).setOnClickListener(tabListener);	
		
	}


	private void refreshView() {
		// imagePaths = new ArrayList<String>();
		// imagePaths.add(getImagesFromWeb("https://twitter.com/images/resources/twitter-bird-light-bgs.png","downloadedFile.png"));
		// Log.i("image path", imagePaths.get(0));
	}

	private void buttonsActive() {
		tweetWallButton.setOnClickListener(this);
		favoriButton.setOnClickListener(this);
		sponsorButton.setOnClickListener(this);
		oturumButton.setOnClickListener(this);
		programButton.setOnClickListener(this);
		konusmaciButton.setOnClickListener(this);
		araButton.setOnClickListener(this);
		newsFlipper.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startAnnouncementList(newsFlipper.getDisplayedChild());
			}

		});
	}
	
	private void startAnnouncementList(int index){
		Intent intent=new Intent(this,AnnouncementListActivity.class);
		Bundle b=new Bundle();
		b.putInt("index", index);
		intent.putExtras(b);
		this.startActivity(intent);
	}



	@Override
	public void onClick(View arg0) {
		Intent i = null;

		switch (arg0.getId()) {

		case R.id.tweetWall:
			i = new Intent(MainActivity.this, TweetWallActivity.class);
			try{
				startActivity(i);
			}
			catch(Exception e){
				Toast.makeText(this,getResources().getString(R.string.any_exception_in_twitter), Toast.LENGTH_SHORT).show();
			}
			return;

		case R.id.program_button:
			i = new Intent(MainActivity.this, ProgramActivity.class);
			break;

		case R.id.sponsor_button:
			i = new Intent(MainActivity.this, SponsorListActivity.class);
			break;

		case R.id.oturum_button:
			i = new Intent(MainActivity.this, TagListActivity.class);
			break;

		case R.id.favori_button:
			i = new Intent(MainActivity.this, FavoriteListActivity.class);
			break;

		case R.id.speakers_button:
			i = new Intent(MainActivity.this, SpeakerListActivity.class);
			break;
		case R.id.search_button:
			i = new Intent(MainActivity.this, SearchActivity.class);
			break;

		}
		if(i!=null){
			startActivity(i);
		}

	}

	private void setDeviceDimensions() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Util.setDeviceHeight(metrics.heightPixels);
		Util.setDeviceWidth(metrics.widthPixels);
	}

}
