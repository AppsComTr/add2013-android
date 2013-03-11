package org.gdgankara.app.tasks;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.gdgankara.app.utils.Util;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Verilen adrese GET isteği yaparak JSONObject döner.
 * 
 * @author bkyn
 */
public class GetJSONObjectTask extends AsyncTask<String, Integer, JSONObject> {
	private final String TAG = GetJSONObjectTask.class.getSimpleName();

	@Override
	protected JSONObject doInBackground(String... params) {
		JSONObject jsonResponse = null;
		final String requestUrl = params[0];
		final AndroidHttpClient client = AndroidHttpClient
				.newInstance("Android");
		HttpGet getRequest = new HttpGet(requestUrl);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w(TAG, "Error " + statusCode + " while retrieving from "
						+ requestUrl);
				return null;
			}
			jsonResponse = new JSONObject(
					Util.convertInputStreamToString(response.getEntity()
							.getContent()));
		} catch (IOException e) {
			Log.w(TAG, e.toString());
		} catch (IllegalStateException e) {
			Log.w(TAG, e.toString());
		} catch (JSONException e) {
			Log.w(TAG, e.toString());
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return jsonResponse;
	}

}
