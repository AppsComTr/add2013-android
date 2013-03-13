package org.gdgankara.app.io;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class TagHandler extends BaseHandler {
	public static final String TAG = TagHandler.class.getSimpleName();

	private static final String CACHE_FILE = "TagsJSON";
	private static final String BASE_URL = "http://10.0.2.2:8888/api/tags/";
	
	private Context context;
	private String lang;
	
	public TagHandler(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public ArrayList<?> parseJSONObject(JSONObject jsonObject)
			throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getCacheFileName() {
		return CACHE_FILE + "_" + lang;
	}
}
