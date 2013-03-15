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

	private static final String CACHE_FILE = "AnnouncementJSON";
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
			jsonObject = doGet(BASE_URL + lang);
			boolean isVersionUpdated = Util.isVersionUpdated(context,
					jsonObject);
			if (isVersionUpdated) {
				announcementList = parseJSONObject(jsonObject);
				writeListToFile(announcementList);
			} else {
				announcementList = (ArrayList<Announcement>) readCacheFile();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getLocalizedMessage());
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return announcementList;
	}

	@Override
	public ArrayList<Announcement> parseJSONObject(JSONObject jsonObject)
			throws JSONException {
		JSONArray announcementArray;
		ArrayList<Announcement> announcementList = new ArrayList<Announcement>();
		try {
			announcementArray = jsonObject.getJSONArray("announcements");
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
				
				if (announcementObject.getString("lang").equals(Announcement.LANG_EN)) {
					announcement.setLang(Announcement.LANG_EN);
				}else {
					announcement.setLang(Announcement.LANG_TR);
				}
				
				announcementList.add(announcement);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getLocalizedMessage());
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return announcementList;
	}

	@Override
	public String getCacheFileName() {
		return CACHE_FILE + "_" + lang;
	}

}
