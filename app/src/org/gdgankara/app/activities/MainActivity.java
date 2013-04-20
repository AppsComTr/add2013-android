package org.gdgankara.app.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.utils.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnClickListener{

	private Button tweetWallButton,programButton,haritaButton,oturumButton,sponsorButton,favoriButton,konusmaciButton;
	private ViewFlipper newsFlipper;
	private ImageView araButton,tempImg;
	private String filepath;
	private ArrayList<String> imagePaths;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Util.prepareStaticLists(this);
		Util.setDeviceHeight(getDimensionsOfScreen());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setUpButtons();
		buttonsActive();
		
		
		
		Thread thread = new Thread()
		{
		    @Override
		    public void run() {
		        try {
		            while(true) {
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
	private void setUpButtons(){
		tweetWallButton = (Button) findViewById(R.id.tweetWall);
		favoriButton = (Button) findViewById(R.id.favori_button);
		haritaButton=(Button) findViewById(R.id.harita_button);
		sponsorButton=(Button) findViewById(R.id.sponsor_button);
		oturumButton=(Button) findViewById(R.id.oturum_button);
		programButton=(Button) findViewById(R.id.program_button);
		araButton = (ImageView)findViewById(R.id.search_button);
		konusmaciButton=(Button)findViewById(R.id.speakers_button);
		newsFlipper = (ViewFlipper)findViewById(R.id.highlights);
	}
	
	private void setHighligths()
	{
		
	}
	
	private void refreshView(){
//		imagePaths = new ArrayList<String>();
//		imagePaths.add(getImagesFromWeb("https://twitter.com/images/resources/twitter-bird-light-bgs.png","downloadedFile.png"));
//		Log.i("image path", imagePaths.get(0));
	}
	private void buttonsActive(){
		tweetWallButton.setOnClickListener(this);
		favoriButton.setOnClickListener(this);
		haritaButton.setOnClickListener(this);
		sponsorButton.setOnClickListener(this);
		oturumButton.setOnClickListener(this);
		programButton.setOnClickListener(this);
		konusmaciButton.setOnClickListener(this);
		araButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		Intent i=null;
		
		switch(arg0.getId()){
		
		case R.id.tweetWall:
			i = new Intent(MainActivity.this , TweetWallActivity.class);
			startActivity(i);
			break;
			
		case R.id.program_button:
			break;
			
		case R.id.harita_button:
			i = new Intent(MainActivity.this, MyMapActivity.class);
			startActivity(i);
			break;
			
		case R.id.sponsor_button:
			i = new Intent(MainActivity.this,SponsorListActivity.class);
			startActivity(i);
			break;
			
		case R.id.oturum_button:
			i = new Intent(MainActivity.this ,TagListActivity.class);
			startActivity(i);
			break;
			
		case R.id.favori_button:
			i = new Intent(MainActivity.this , FavoriteListActivity.class);
			startActivity(i);
			break;
			
		case R.id.speakers_button:
			i = new Intent(MainActivity.this ,SpeakerListActivity.class);
			startActivity(i);
			break;
		case R.id.search_button:
			i = new Intent(MainActivity.this , SearchActivity.class);
			startActivity(i);
			break;
		
		}
		
		
//		startActivity(i);
		
	}
	
	private String getImagesFromWeb(String urlString,String filename){
		try
		{   
		  URL url = new URL(urlString);
		  HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		  urlConnection.setRequestMethod("GET");
		  urlConnection.setDoOutput(true);                   
		  urlConnection.connect();                  
		  File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
		  Log.i("Yol",SDCardRoot.getAbsolutePath());
		  Log.i("Local filename:",""+filename);
		  File file = new File(SDCardRoot,filename);
		  if(file.createNewFile())
		  {
		    file.createNewFile();
		  }                 
		  FileOutputStream fileOutput = new FileOutputStream(file);
		  InputStream inputStream = urlConnection.getInputStream();
		  int totalSize = urlConnection.getContentLength();
		  int downloadedSize = 0;   
		  byte[] buffer = new byte[1024];
		  int bufferLength = 0;
		  while ( (bufferLength = inputStream.read(buffer)) > 0 ) 
		  {                 
		    fileOutput.write(buffer, 0, bufferLength);                  
		    downloadedSize += bufferLength;                 
		    Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
		  }             
		  fileOutput.close();
		  if(downloadedSize==totalSize) filepath=file.getPath();    
		} 
		catch (MalformedURLException e) 
		{
		  e.printStackTrace();
		} 
		catch (IOException e)
		{
		  filepath=null;
		  e.printStackTrace();
		}
		Log.i("filepath:"," "+filepath) ;
		return filepath;
	}
	
	private int getDimensionsOfScreen() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;

	}

}
