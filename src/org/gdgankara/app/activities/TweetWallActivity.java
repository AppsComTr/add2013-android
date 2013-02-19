package org.gdgankara.app.activities;

import org.gdgankara.app.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TweetWallActivity extends Activity {

	WebView tweetWallViewer;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_wall);
		
		tweetWallViewer = (WebView) findViewById(R.id.webEngine);
		
		tweetWallViewer.setWebViewClient(new WebViewClient());
		tweetWallViewer.getSettings().setJavaScriptEnabled(true);
		tweetWallViewer.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		tweetWallViewer.loadUrl("http://mertsimsek.net/twall.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tweet_wall, menu);
		return true;
	}

}
