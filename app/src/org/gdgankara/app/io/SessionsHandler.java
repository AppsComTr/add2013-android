package org.gdgankara.app.io;

import java.util.ArrayList;
import java.util.List;

import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.utils.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class SessionsHandler extends BaseHandler {
	public static final String TAG = SessionsHandler.class.getSimpleName();

	private static final String CACHE_FILE = "SessionsJSON";
	private static final String BASE_URL = "http://10.0.2.2:8888/api/sessions/";

	protected Context context;
	protected String lang;

	public SessionsHandler(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * API iletişim kurarak verilerde değişiklik olup olmadığını kontrol eder.
	 * Değişiklik var ise parse ederek verileri dosyada saklar. Değişiklik yok
	 * ise dosyadan okur.
	 * 
	 * @param lang
	 *            Session.LANG_TR yada Session.LANG_EN değeri alır
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Session> getSessionsList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<Session> sessionsList = new ArrayList<Session>();
		try {
			jsonObject = doGet(BASE_URL + lang);
			boolean isVersionUpdated = Util.isVersionUpdated(context, jsonObject);
			if (isVersionUpdated) {
				sessionsList = parseJSONObject(jsonObject);
				writeListToFile(sessionsList);
			} else {
				sessionsList = (ArrayList<Session>) readCacheFile();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getLocalizedMessage());
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
		return sessionsList;
	}

	@Override
	public ArrayList<Session> parseJSONObject(JSONObject jsonObject) {
		JSONArray sessionArray;
		ArrayList<Session> sessionsList = new ArrayList<Session>();
		try {
			sessionArray = jsonObject.getJSONArray("sessions");
			int length = sessionArray.length();
			Session session;

			for (int i = 0; i < length; i++) {
				JSONObject sessionObject = (JSONObject) sessionArray.get(i);
				session = new Session();
				session.setId(sessionObject.getInt("id"));
				session.setBreak(sessionObject.getBoolean("break"));
				session.setDate(sessionObject.getString("day"));
				session.setDescription(sessionObject.getString("description"));
				session.setEnd_hour(sessionObject.getString("endHour"));
				session.setStart_hour(sessionObject.getString("startHour"));
				session.setHall(sessionObject.getString("hall"));
				session.setTitle(sessionObject.getString("title"));
				session.setTags(sessionObject.getString("tags"));

				if (sessionObject.getString("lang").equals(Session.LANG_EN)) {
					session.setLanguage(Session.LANG_EN);
				} else {
					session.setLanguage(Session.LANG_TR);
				}

				if (session.getDate().startsWith(
						String.valueOf(Session.DAY_FRIDAY))) {
					session.setDay(Session.DAY_FRIDAY);
				} else {
					session.setDay(Session.DAY_SATURDAY);
				}

				session.setSpeaker1(jsonObjectToSpeaker("speaker1",
						sessionObject));
				session.setSpeaker2(jsonObjectToSpeaker("speaker2",
						sessionObject));
				session.setSpeaker3(jsonObjectToSpeaker("speaker3",
						sessionObject));

				sessionsList.add(session);
			}
		} catch (JSONException e) {
			System.out.println("Error: " + e.getLocalizedMessage());
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}

		return sessionsList;
	}

	@Override
	public String getCacheFileName() {
		return CACHE_FILE + "_" + lang;
	}

	private Speaker jsonObjectToSpeaker(String speakerKey,
			JSONObject sessionObject) throws JSONException {
		if (!sessionObject.isNull(speakerKey)) {
			JSONObject jsonSpeaker = sessionObject.getJSONObject(speakerKey);
			Speaker speaker = new Speaker();
			speaker.setId(jsonSpeaker.getInt("id"));
			speaker.setBiography(jsonSpeaker.getString("bio"));
			speaker.setBlog(jsonSpeaker.getString("blog"));
			speaker.setFacebook(jsonSpeaker.getString("facebook"));
			speaker.setGplus(jsonSpeaker.getString("gplus"));
			speaker.setName(jsonSpeaker.getString("name"));
			speaker.setPhoto(jsonSpeaker.getString("photo"));
			speaker.setTwitter(jsonSpeaker.getString("twitter"));
			JSONArray sessionIDArray = jsonSpeaker
					.getJSONArray("sessionIDList");
			List<Long> sessionIDList = new ArrayList<Long>();
			for (int i = 0; i < sessionIDArray.length(); i++) {
				sessionIDList.add(sessionIDArray.getLong(i));
			}
			speaker.setSessionIDList(sessionIDList);
			return speaker;
		} else {
			return null;
		}
	}
}
