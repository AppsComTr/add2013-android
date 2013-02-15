package org.gdgankara.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class TweetWallActivity extends Activity {

	private String totalWall = "";
	private TextView wallView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_wall);

		wallView = (TextView) findViewById(R.id.wall);
		wallView.setLinksClickable(true);
		totalWall += TweetWallPrepare.getTweets("#AndroidDevDays");

		wallView.setText(totalWall);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tweet_wall, menu);
		return true;
	}

}
