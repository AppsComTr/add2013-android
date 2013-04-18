package org.gdgankara.app.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.services.ImageCacheService;
import org.gdgankara.app.utils.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SessionsHandler extends BaseHandler {
	private static final String TAG = SessionsHandler.class.getSimpleName();

	private static final String CACHE_FILE_SESSION = "SessionsJSON";
	private static final String CACHE_FILE_SPEAKER = "SpeakersJSON";

	private static final String BASE_URL_SESSION = "http://add-2013.appspot.com/api/sessions/";
	private static final String BASE_URL_SPEAKER = "http://add-2013.appspot.com/api/speakers/";

	protected Context context;
	protected String lang;
	protected ArrayList<Session> sessionList;
	protected ArrayList<Speaker> speakerList;

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
	public void initializeLists(String lang) {
		this.lang = lang;
		JSONObject jsonObject = null;
		try {
			sessionList = (ArrayList<Session>) readCacheFile(getSessionCacheFileName(lang));
			if (sessionList == null) {
				jsonObject = doGet(BASE_URL_SESSION + lang);
				sessionList = parseJSONObjectToSessionList(jsonObject,
						"sessions");
				writeListToFile(sessionList, getSessionCacheFileName(lang));
			}

			speakerList = (ArrayList<Speaker>) readCacheFile(getSpeakerCacheFileName(lang));
			if (speakerList == null) {
				if (jsonObject == null) {
					jsonObject = doGet(BASE_URL_SPEAKER + lang);
				}
				speakerList = parseJSONObjectToSpeakerList(jsonObject,
						"speakers");
				setSpeakerList(speakerList);
				startImageCacheService(ImageCacheService.CACHE_SPEAKER_IMAGES);
				writeListToFile(speakerList, getSpeakerCacheFileName(lang));
			}

			setSessionList(sessionList);
			setSpeakerList(speakerList);
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Session> updateSessionList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<Session> sessionsList = new ArrayList<Session>();
		try {
			jsonObject = doGet(BASE_URL_SESSION + lang);
			boolean isVersionUpdated = Util.isVersionUpdated(context,
					jsonObject);
			if (isVersionUpdated) {
				sessionsList = parseJSONObjectToSessionList(jsonObject,
						"sessions");
				writeListToFile(sessionsList, getSessionCacheFileName(lang));
			} else {
				sessionsList = (ArrayList<Session>) readCacheFile(getSessionCacheFileName(lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		setSessionList(sessionsList);
		return sessionsList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Speaker> updateSpeakerList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<Speaker> speakerList = new ArrayList<Speaker>();
		try {
			jsonObject = doGet(BASE_URL_SPEAKER + lang);
			boolean isVersionUpdated = Util.isVersionUpdated(context,
					jsonObject);
			if (isVersionUpdated) {
				speakerList = parseJSONObjectToSpeakerList(jsonObject,
						"speakers");
				startImageCacheService(ImageCacheService.CACHE_SPEAKER_IMAGES);
				writeListToFile(speakerList, getSpeakerCacheFileName(lang));
			} else {
				speakerList = (ArrayList<Speaker>) readCacheFile(getSpeakerCacheFileName(lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		setSpeakerList(speakerList);
		return speakerList;
	}

	public void updateSpeakerListCacheFile(String lang) {
		try {
			writeListToFile(Util.SpeakerList, getSpeakerCacheFileName(lang));
		} catch (IOException e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	private ArrayList<Speaker> parseJSONObjectToSpeakerList(
			JSONObject jsonObject, String objectName) {
		JSONArray speakerArray;
		ArrayList<Speaker> speakerList = new ArrayList<Speaker>();
		try {
			speakerArray = jsonObject.getJSONArray(objectName);
			int length = speakerArray.length();
			Speaker speaker;

			for (int i = 0; i < length; i++) {
				JSONObject speakerObject = (JSONObject) speakerArray.get(i);
				speaker = new Speaker();
				speaker.setId(speakerObject.getLong("id"));
				speaker.setBiography(speakerObject.getString("bio"));
				speaker.setBlog(speakerObject.getString("blog"));
				speaker.setFacebook(speakerObject.getString("facebook"));
				speaker.setGplus(speakerObject.getString("gplus"));
				speaker.setLanguage(speakerObject.getString("lang"));
				speaker.setName(speakerObject.getString("name"));
				speaker.setPhoto(speakerObject.getString("photo"));
				speaker.setTwitter(speakerObject.getString("twitter"));
				speaker.setTitle(speakerObject.getString("title"));

				JSONArray sessionIDArray;
				List<Long> sessionIDList = new ArrayList<Long>();
				try {
					sessionIDList.add(speakerObject.getLong("sessionIDList"));
				} catch (JSONException e) {
					sessionIDArray = speakerObject
							.getJSONArray("sessionIDList");
					for (int k = 0; k < sessionIDArray.length(); k++) {
						sessionIDList.add(sessionIDArray.getLong(k));
					}
				}

				speaker.setSessionIDList(sessionIDList);
				speakerList.add(speaker);
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return speakerList;
	}

	public ArrayList<Session> parseJSONObjectToSessionList(
			JSONObject jsonObject, String objectName) {
		JSONArray sessionArray;
		ArrayList<Session> sessionsList = new ArrayList<Session>();
		try {
			sessionArray = jsonObject.getJSONArray(objectName);
			int length = sessionArray.length();
			Session session;

			for (int i = 0; i < length; i++) {
				JSONObject sessionObject = (JSONObject) sessionArray.get(i);
				session = new Session();
				session.setId(sessionObject.getLong("id"));
				session.setBreak(sessionObject.getBoolean("break"));
				session.setDate(sessionObject.getString("day"));
				session.setDescription(sessionObject.getString("description"));
				session.setEnd_hour(sessionObject.getString("endHour"));
				session.setStart_hour(sessionObject.getString("startHour"));
				session.setHall(sessionObject.getString("hall"));
				session.setTitle(sessionObject.getString("title"));
				session.setFavorite(false);

				if (!session.isBreak()) {
					session.setTags(sessionObject.getString("tags"));

					// TODO REFACTOR
					JSONArray speakerIDArray;
					List<Long> speakerIDList = new ArrayList<Long>();
					try {
						speakerIDList.add(sessionObject
								.getLong("speakerIDList"));
					} catch (JSONException e) {
						// e.printStackTrace();
						speakerIDArray = sessionObject
								.getJSONArray("speakerIDList");
						if (speakerIDArray.get(0) != null) {
							for (int k = 0; k < speakerIDArray.length(); k++) {
								speakerIDList.add(speakerIDArray.getLong(k));
							}
						} else {
							for (int k = 0; k < speakerIDArray.length(); k++) {
								speakerIDList.add((long) 0);
							}
						}
					}

					session.setSpeakerIDList(speakerIDList);
				} else {
					session.setTags("");
				}

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

				sessionsList.add(session);
			}
		} catch (JSONException e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return sessionsList;
	}

	private void startImageCacheService(String type) {
		Intent imageCacheIntent = new Intent(context, ImageCacheService.class);
		imageCacheIntent.setAction(ImageCacheService.CACHE_STARTED);
		imageCacheIntent.putExtra(ImageCacheService.CACHE_TYPE, type);
		context.startService(imageCacheIntent);
	}
	
	private void updateFavoriteSessions(ArrayList<Session> sessionList) {
		if (Util.FavoritesList != null) {
			for (Session session : sessionList) {
				for (Long favoriteSessionId : Util.FavoritesList) {
					if (session.getId() == favoriteSessionId) {
						session.setFavorite(true);
					}
				}
			}
		}

	}

	private String getSessionCacheFileName(String lang) {
		return CACHE_FILE_SESSION + "_" + lang;
	}

	private String getSpeakerCacheFileName(String lang) {
		return CACHE_FILE_SPEAKER + "_" + lang;
	}

	public ArrayList<Session> getSessionList() {
		return sessionList;
	}

	public void setSessionList(ArrayList<Session> sessionList) {
		updateFavoriteSessions(sessionList);
		this.sessionList = sessionList;
		Util.SessionList = sessionList;
	}

	public ArrayList<Speaker> getSpeakerList() {
		return speakerList;
	}

	public void setSpeakerList(ArrayList<Speaker> speakerList) {
		this.speakerList = speakerList;
		Util.SpeakerList = speakerList;
	}

	@Override
	public ArrayList<?> parseJSONObject(JSONObject jsonObject, String objectName)
			throws JSONException {
		return null;
	}
}
