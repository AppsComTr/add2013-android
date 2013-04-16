package org.gdgankara.app.io;

import java.util.ArrayList;

import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.utils.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class AnnouncementHandler extends BaseHandler {
	public static final String TAG = AnnouncementHandler.class.getSimpleName();

	private static final String CACHE_FILE_ANNOUNCEMENT = "AnnouncementsJSON";
	private static final String BASE_URL = "http://add-2013.appspot.com/api/announcements/";

	private Context context;
	private String lang;

	public AnnouncementHandler(Context context) {
		super(context);
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Announcement> getAnnouncementList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<Announcement> announcementList = new ArrayList<Announcement>();
		try {
			announcementList = (ArrayList<Announcement>) readCacheFile(getCacheFileName(lang));
			if (announcementList == null) {
				jsonObject = doGet(BASE_URL + lang);
				announcementList = parseJSONObject(jsonObject, "announcements");
				writeListToFile(announcementList, getCacheFileName(lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return announcementList;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Announcement> updateAnnouncementList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<Announcement> announcementList = new ArrayList<Announcement>();
		try {
			jsonObject = doGet(BASE_URL + lang);
			boolean isVersionUpdated = Util.isVersionUpdated(context,
					jsonObject);
			if (isVersionUpdated) {
				announcementList = parseJSONObject(jsonObject, "announcements");
				writeListToFile(announcementList, getCacheFileName(lang));
			} else {
				announcementList = (ArrayList<Announcement>) readCacheFile(getCacheFileName(lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return announcementList;
	}


	@Override
	public ArrayList<Announcement> parseJSONObject(JSONObject jsonObject, String objectName)
			throws JSONException {
		JSONArray announcementArray;
		ArrayList<Announcement> announcementList = new ArrayList<Announcement>();
		try {
			announcementArray = jsonObject.getJSONArray(objectName);
			int length = announcementArray.length();
			Announcement announcement;
			for (int i = 0; i < length; i++) {
				JSONObject announcementObject = announcementArray
						.getJSONObject(i);
				announcement = new Announcement();
				announcement.setId(announcementObject.getLong("id"));
				announcement.setDescription(announcementObject.getString("description"));
				announcement.setImage(announcementObject.getString("image"));
				announcement.setLink(announcementObject.getString("link"));
				announcement.setSession(announcementObject.getBoolean("session"));
				announcement.setTitle(announcementObject.getString("title"));
				
				if (announcement.isSession()) {
					announcement.setSessionId(announcementObject.getLong("sessionId"));
				}
				
				if (announcementObject.getString("lang").equals(Announcement.LANG_EN)) {
					announcement.setLang(Announcement.LANG_EN);
				}else {
					announcement.setLang(Announcement.LANG_TR);
				}
				
				announcementList.add(announcement);
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return announcementList;
	}

	private String getCacheFileName(String lang){
		return CACHE_FILE_ANNOUNCEMENT + "_" + lang; 
	}

}
