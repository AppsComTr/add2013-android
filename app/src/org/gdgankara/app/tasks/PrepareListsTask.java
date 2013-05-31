package org.gdgankara.app.tasks;

import org.gdgankara.app.R;
import org.gdgankara.app.activities.MainActivity;
import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class PrepareListsTask extends AsyncTask<Void, Void, Void> {
	private static final String TAG = PrepareListsTask.class.getSimpleName();

	Context context;
	ProgressDialog progressDialog;
	boolean isInternetAvailable;

	public PrepareListsTask(Context context, ProgressDialog progressDialog) {
		this.context = context;
		this.isInternetAvailable = Util.isInternetAvailable(context);
		this.progressDialog = progressDialog;
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (isInternetAvailable) {
			Util.prepareStaticLists(context);
		} else if (!isInternetAvailable) {
			Log.d(TAG, "Internet yok, cacheden alınıyor");
			Util.prepareStaticListsFromCache(context);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (!isInternetAvailable && Util.SessionList.size() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(context.getResources().getString(
					R.string.internet_fail));
			builder.setNeutralButton(R.string.button_ok,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							((Activity) context).finish();
						}

					});
			builder.create().show();
		} else {
			Intent intent = new Intent(context, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
			((Activity) context).finish();
		}
		if (progressDialog != null) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		}
		super.onPostExecute(result);
	}

}
