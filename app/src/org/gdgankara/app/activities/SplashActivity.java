package org.gdgankara.app.activities;

import org.gdgankara.app.R;
import org.gdgankara.app.R.layout;
import org.gdgankara.app.R.menu;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;

public class SplashActivity extends Activity {

	private static final int SPLASH_DISPLAY_TIME = 1000; /* 1 seconds */

	private ProgressBar pdSpin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Thread th = new Thread() {
			public void run() {

					try {
						android.os.SystemClock.sleep(SPLASH_DISPLAY_TIME);
					} catch (Exception e) {
						e.printStackTrace();
					}
					startActivity(new Intent(SplashActivity.this,
							MainActivity.class));
					finish();
			}
		};

		th.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
