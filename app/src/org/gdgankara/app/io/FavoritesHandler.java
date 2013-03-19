package org.gdgankara.app.io;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class FavoritesHandler extends BaseHandler {
	private static final String TAG = FavoritesHandler.class.getSimpleName();
	private static final String CACHE_FILE_FAVORITES = "FavoritesList";

	public FavoritesHandler(Context context) {
		super(context);
		this.context = context;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Long> getFavoritesList(String lang){
		ArrayList<Long> favoritesList = new ArrayList<Long>();
		try {
			favoritesList = (ArrayList<Long>) readCacheFile(getCacheFileName(lang));
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		if (favoritesList == null) {
			favoritesList = new ArrayList<Long>();
		}
		return favoritesList;
	}
	
	public void updateFavoritesList(ArrayList<Long> favoritesList, String lang){
		try {
			writeListToFile(favoritesList, getCacheFileName(lang));
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	private String getCacheFileName(String lang){
		return CACHE_FILE_FAVORITES + "_" + lang; 
	}

	@Override
	public ArrayList<?> parseJSONObject(JSONObject jsonObject, String objectName)
			throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
}
