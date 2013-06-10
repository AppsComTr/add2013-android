package org.gdgankara.app.activities;

import org.gdgankara.app.R;
import org.gdgankara.app.tasks.PrepareListsTask;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class SplashActivity extends BaseActivity {
	private static final String TAG = SpeakerListActivity.class.getSimpleName();
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		try {
			PrepareListsTask prepareListsTask = new PrepareListsTask(context, null);
			prepareListsTask.execute();
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}



}
