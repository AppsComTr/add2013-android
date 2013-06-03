package org.gdgankara.app.activities;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;

public class BaseActivity extends Activity {

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}

}
