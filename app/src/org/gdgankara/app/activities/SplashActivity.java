package org.gdgankara.app.activities;

import org.gdgankara.app.R;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class SplashActivity extends Activity {
	private static final String TAG = SpeakerListActivity.class.getSimpleName();

	private Context context = this;
	boolean isInternetAvailable = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		isInternetAvailable = Util.isInternetAvailable(context);
		
		try {
			new getListsAsync().execute();
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	class getListsAsync extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			if (isInternetAvailable) {
				Util.prepareStaticLists(context);
			} else if (!isInternetAvailable) {
				Log.d(TAG,"Internet yok, cacheden alınıyor");
				Util.prepareStaticListsFromCache(context);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (!isInternetAvailable && Util.SessionList.size() == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Internet bağlantısı olmadığı için program güncellenemiyor");
				builder.setNeutralButton(R.string.button_ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						});
				builder.create().show();
			} else {
				startActivity(new Intent(SplashActivity.this,
						MainActivity.class));
				finish();
			}
			super.onPostExecute(result);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
