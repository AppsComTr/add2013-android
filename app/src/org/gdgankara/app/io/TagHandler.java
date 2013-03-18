package org.gdgankara.app.io;

import java.util.ArrayList;

import org.gdgankara.app.utils.Util;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class TagHandler extends BaseHandler {
	public static final String TAG = TagHandler.class.getSimpleName();

	private static final String CACHE_FILE_TAG = "TagsJSON";
	private static final String BASE_URL = "http://add-2013.appspot.com/api/tags/";

	private Context context;
	private String lang;

	public TagHandler(Context context) {
		super(context);
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getTagList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<String> tagList = new ArrayList<String>();
		try {
			tagList = (ArrayList<String>) readCacheFile(getCacheFileName(lang));
			if (tagList == null) {
				jsonObject = doGet(BASE_URL + lang);
				tagList = parseJSONObject(jsonObject, "tag");
				writeListToFile(tagList, getCacheFileName(lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return tagList;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> updateTagList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<String> tagList = new ArrayList<String>();
		try {
			jsonObject = doGet(BASE_URL + lang);
			boolean isVersionUpdated = Util.isVersionUpdated(context, jsonObject);
			if (isVersionUpdated) {
				tagList = parseJSONObject(jsonObject, "tag");
				writeListToFile(tagList, getCacheFileName(lang));
			} else {
				tagList = (ArrayList<String>) readCacheFile(getCacheFileName(lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return tagList;
	}

	@Override
	public ArrayList<String> parseJSONObject(JSONObject jsonObject, String objectName)
			throws JSONException {
		JSONObject tagObject = jsonObject.getJSONObject(objectName);
		String[] tags = tagObject.getString("tags").split(",");
		ArrayList<String> tagList = new ArrayList<String>();
		for (String string : tags) {
			tagList.add(string);
		}
		return tagList;
	}

	private String getCacheFileName(String lang){
		return CACHE_FILE_TAG + "_" + lang; 
	}
}
