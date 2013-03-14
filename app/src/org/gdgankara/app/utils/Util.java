package org.gdgankara.app.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Util {
	public static final String TAG = Util.class.getSimpleName();

	/**
	 * Shared Preferences'ta tutulan versiyon numarasını verilen numara ile
	 * karşılaştırır.
	 * 
	 * @param context
	 * @param number
	 * @return Boolean
	 * @throws JSONException 
	 */
	public static Boolean isVersionUpdated(Context context,  JSONObject jsonObject) throws JSONException {
		long number = jsonObject.getJSONObject("version")
				.getLong("number");
		Boolean state = false;
		SharedPreferences settings = context.getSharedPreferences(TAG, 0);
		long mNumber = settings.getLong("version", 0);
		if (mNumber == number) {
			state = false;
		} else if (mNumber != number || mNumber == 0) {
			Editor editor = settings.edit();
			editor.putLong("version", number);
			editor.commit();
			state = true;
		}
		return state;
	}

	/**
	 * Verilen InputStream'i String'e çevirir.
	 * 
	 * @param inputStream
	 * @return String
	 */
	public static String convertInputStreamToString(InputStream inputStream) {
		String result = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream), 8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			inputStream.close();
			result = stringBuilder.toString();

		} catch (Exception e) {
			Log.w(TAG, e.toString());
			e.printStackTrace();
		}
		return result;
	}

}
