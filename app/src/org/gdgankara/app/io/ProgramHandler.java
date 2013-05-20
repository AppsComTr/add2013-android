package org.gdgankara.app.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Sponsor;
import org.gdgankara.app.model.Tag;
import org.gdgankara.app.services.ImageCacheService;
import org.gdgankara.app.utils.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProgramHandler extends BaseHandler {
	private static final String TAG = ProgramHandler.class.getSimpleName();

	private static final String CACHE_FILE = "cache_";

	private static final String BASE_URL_PROGRAM = "http://add-2013.appspot.com/api/program/";

	protected Context context;
	protected String lang;
	protected ArrayList<Announcement> announcementList;
	protected ArrayList<Sponsor> sponsorList;
	protected ArrayList<Session> sessionList;
	protected ArrayList<Speaker> speakerList;
	protected ArrayList<String> tagList;
	private ArrayList<Integer> tag_counter;
	private Map<String,Integer> hashmap;

	public ProgramHandler(Context context) {
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
			sessionList = (ArrayList<Session>) readCacheFile(getCacheFileName(
					Session.KIND, lang));
			if (sessionList == null) {
				tagList = new ArrayList<String>();
				tag_counter=new ArrayList<Integer>();
				hashmap=new HashMap<String, Integer>();
				jsonObject = doGet(BASE_URL_PROGRAM + lang);
				sessionList = parseJSONObjectToSessionList(jsonObject,
						"sessions");
				writeListToFile(sessionList,
						getCacheFileName(Session.KIND, lang));
			}else {
				tagList = (ArrayList<String>) readCacheFile(getCacheFileName(Tag.KIND, "en"));
			}

			speakerList = (ArrayList<Speaker>) readCacheFile(getCacheFileName(
					Speaker.KIND, lang));
			if (speakerList == null) {
				if (jsonObject == null) {
					jsonObject = doGet(BASE_URL_PROGRAM + lang);
				}
				speakerList = parseJSONObjectToSpeakerList(jsonObject,
						"speakers");
				setSpeakerList(speakerList);
				// startImageCacheService(ImageCacheService.CACHE_SPEAKER_IMAGES);
				writeListToFile(speakerList,getCacheFileName(Speaker.KIND, lang));
			}

			sponsorList = (ArrayList<Sponsor>) readCacheFile(getCacheFileName(
					Sponsor.KIND, "en"));
			if (sponsorList == null) {
				if (jsonObject == null) {
					jsonObject = doGet(BASE_URL_PROGRAM + lang);
				}
				sponsorList = parseJSONObjectToSponsorList(jsonObject,
						"sponsors");
				writeListToFile(sponsorList,
						getCacheFileName(Sponsor.KIND, lang));
			}

			announcementList = (ArrayList<Announcement>) readCacheFile(getCacheFileName(
					Announcement.KIND, "en"));
			if (announcementList == null) {
				if (jsonObject == null) {
					jsonObject = doGet(BASE_URL_PROGRAM + lang);
				}
				announcementList = parseJSONObjectToAnnouncementList(
						jsonObject, "announcements");
				writeListToFile(announcementList,
						getCacheFileName(Announcement.KIND, "en"));
			}
			
			setAnnouncementList(announcementList);
			setSessionList(sessionList);
			setSpeakerList(speakerList);
			setSponsorList(sponsorList);
			setTagList(tagList);
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Session> updateSessionList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<Session> sessionsList = new ArrayList<Session>();
		try {
			jsonObject = doGet(BASE_URL_PROGRAM + lang);
			boolean isVersionUpdated = Util.isVersionUpdated(context,
					jsonObject);
			if (isVersionUpdated) {
				sessionsList = parseJSONObjectToSessionList(jsonObject,
						"sessions");
				writeListToFile(sessionsList,
						getCacheFileName(Session.KIND, lang));
			} else {
				sessionsList = (ArrayList<Session>) readCacheFile(getCacheFileName(
						Session.KIND, lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e);
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
			jsonObject = doGet(BASE_URL_PROGRAM + lang);
			boolean isVersionUpdated = Util.isVersionUpdated(context,
					jsonObject);
			if (isVersionUpdated) {
				speakerList = parseJSONObjectToSpeakerList(jsonObject,
						"speakers");
				startImageCacheService(ImageCacheService.CACHE_SPEAKER_IMAGES);
				writeListToFile(speakerList,
						getCacheFileName(Speaker.KIND, lang));
			} else {
				speakerList = (ArrayList<Speaker>) readCacheFile(getCacheFileName(
						Speaker.KIND, lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e);
			e.printStackTrace();
		}
		setSpeakerList(speakerList);
		return speakerList;
	}

	public void updateSpeakerListCacheFile(String lang) {
		try {
			writeListToFile(Util.SpeakerList,
					getCacheFileName(Speaker.KIND, lang));
		} catch (IOException e) {
			Log.e(TAG, "Error: " + e);
			e.printStackTrace();
		}
	}

	private ArrayList<Announcement> parseJSONObjectToAnnouncementList(
			JSONObject jsonObject, String objectName) {
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
				announcement.setDescription(announcementObject
						.getString("description"));
				announcement.setImage(announcementObject.getString("image"));
				announcement.setLink(announcementObject.getString("link"));
				announcement.setSession(announcementObject
						.getBoolean("session"));
				announcement.setTitle(announcementObject.getString("title"));

				if (announcement.isSession()) {
					announcement.setSessionId(announcementObject
							.getLong("sessionId"));
				}

				if (announcementObject.getString("lang").equals(
						Announcement.LANG_EN)) {
					announcement.setLang(Announcement.LANG_EN);
				} else {
					announcement.setLang(Announcement.LANG_TR);
				}

				announcementList.add(announcement);
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e);
			e.printStackTrace();
		}
		return announcementList;
	}

	private ArrayList<Sponsor> parseJSONObjectToSponsorList(
			JSONObject jsonObject, String objectName) {
		JSONArray sponsorArray;
		ArrayList<Sponsor> sponsorList = new ArrayList<Sponsor>();
		try {
			sponsorArray = jsonObject.getJSONArray(objectName);
			int length = sponsorArray.length();
			Sponsor sponsor;
			for (int i = 0; i < length; i++) {
				JSONObject sponsorObject = sponsorArray.getJSONObject(i);
				sponsor = new Sponsor();
				sponsor.setId(sponsorObject.getLong("id"));
				sponsor.setLogo(sponsorObject.getString("image"));
				sponsor.setCategory(sponsorObject.getString("category"));
				sponsor.setLink(sponsorObject.getString("link"));

				sponsorList.add(sponsor);
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e);
			e.printStackTrace();
		}
		return sponsorList;
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
				speaker.setBiography(isObjectNull(speakerObject
						.getString("bio")));
				speaker.setLanguage(isObjectNull(speakerObject
						.getString("lang")));
				speaker.setName(isObjectNull(speakerObject.getString("name")));
				speaker.setPhoto(isObjectNull(speakerObject.getString("photo")));

				JSONArray sessionIDArray;
				List<Long> sessionIDList = new ArrayList<Long>();
				if (!speakerObject.isNull("sessionIDList")) {
					try {
						sessionIDList.add(speakerObject
								.getLong("sessionIDList"));
					} catch (JSONException e) {
//						e.printStackTrace();
						Log.e(TAG, e.toString());
						sessionIDArray = speakerObject
								.getJSONArray("sessionIDList");
						for (int k = 0; k < sessionIDArray.length(); k++) {
							sessionIDList.add(sessionIDArray.getLong(k));
						}
					}

					speaker.setSessionIDList(sessionIDList);
					speakerList.add(speaker);
				} else {
					speaker.setSessionIDList(null);
					speakerList.add(speaker);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e);
			e.printStackTrace();
		}
		System.out.println("size:" + speakerList.size());
		return speakerList;
	}

	private ArrayList<Session> parseJSONObjectToSessionList(
			JSONObject jsonObject, String objectName) {
		JSONArray sessionArray;
		ArrayList<Session> sessionsList = new ArrayList<Session>();
		String[] tags;
		int temp_index;
		int tag_size=0;
		try {
			sessionArray = jsonObject.getJSONArray(objectName);
			int length = sessionArray.length();
			Session session;

			for (int i = 0; i < length; i++) {
				JSONObject sessionObject = (JSONObject) sessionArray.get(i);
				session = new Session();
				session.setId(sessionObject.getLong("id"));
				session.setBreak(sessionObject.getBoolean("break"));

				session.setDate(isObjectNull(sessionObject.getString("day")));
				session.setDescription(isObjectNull(sessionObject
						.getString("description")));
				session.setEnd_hour(isObjectNull(sessionObject
						.getString("endHour")));
				session.setStart_hour(isObjectNull(sessionObject
						.getString("startHour")));
				session.setHall(isObjectNull(sessionObject.getString("hall")));
				session.setTitle(isObjectNull(sessionObject.getString("title")));
				session.setFavorite(false);

				if (!session.isBreak()) {
					JSONArray speakerIDArray;
					List<Long> speakerIDList = new ArrayList<Long>();
					try {
						if (!sessionObject.isNull("speakerIDList")) {
							speakerIDList.add(sessionObject
									.getLong("speakerIDList"));
						}
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

					if (!sessionObject.isNull("tags")) {
						session.setTags(isObjectNull(sessionObject
								.getString("tags")));
						 tags= session.getTags().split(",");
						for (String string : tags) {
							if (string != "" && string != null) {
								try{
									temp_index=hashmap.get(string);
									tag_counter.set(temp_index, tag_counter.get(temp_index)+1);
								}
								catch(NullPointerException e){ //Oyle bir tag yok
									hashmap.put(string,tag_size);
									tagList.add(string);
									tag_counter.add(1);
									tag_size++;
								}
								
							}
						}
					}

				}

				if (sessionObject.getString("lang").equals(Session.LANG_EN)) {
					session.setLanguage(Session.LANG_EN);
				} else {
					session.setLanguage(Session.LANG_TR);
				}

				if (session.getDate().contains(
						String.valueOf(Session.DAY_FRIDAY))) {
					session.setDay(Session.DAY_FRIDAY);
				} else {
					session.setDay(Session.DAY_SATURDAY);
				}

				sessionsList.add(session);
			}
		} catch (JSONException e) {
			Log.e(TAG, "Error: " + e);
			e.printStackTrace();
		}
		sortTagsByWeight(tag_size,(Integer[])tag_counter.toArray());
		return sessionsList;
	}

	private void sortTagsByWeight(int size,Integer[] array) {
		if (array ==null || array.length==0){
		      return;
		}
		quicksort(0, size - 1,array);
		
	}

	private void quicksort(int low, int high, Integer[] array) {
		
		int i = low, j = high;
	    int pivot = array[low + (high-low)/2];

	    while (i <= j) {

	      while (array[i] < pivot) {
	        i++;
	      }
	      while (array[j] > pivot) {
	        j--;
	      }
	      if (i <= j) {
	        exchange(i, j,array);
	        i++;
	        j--;
	      }
	    }
	    // Recursion
	    if (low < j)
	      quicksort(low, j,array);
	    if (i < high)
	      quicksort(i, high,array);
	  }

	  private void exchange(int i, int j,Integer[] array) {
	    int temp = array[i];
	    array[i] = array[j];
	    array[j] = temp;
	    Collections.swap(tagList, i, j);
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

	private String isObjectNull(String value) {
		if (value == null || value == "" || value.equals(null)
				|| value.equals("")) {
			return null;
		} else {
			return value;
		}
	}

	private String getCacheFileName(String kind, String lang) {
		return CACHE_FILE + kind + "_" + lang;
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

	public ArrayList<Announcement> getAnnouncementList() {
		return announcementList;
	}

	public void setAnnouncementList(ArrayList<Announcement> announcementList) {
		this.announcementList = announcementList;
		Util.AnnouncementList = announcementList;
	}

	public ArrayList<Sponsor> getSponsorList() {
		return sponsorList;
	}

	public void setSponsorList(ArrayList<Sponsor> sponsorList) {
		this.sponsorList = sponsorList;
		Util.SponsorList = sponsorList;
	}

	public ArrayList<String> getTagList() {
		return tagList;
	}

	public void setTagList(ArrayList<String> tagList) {
		this.tagList = tagList;
		Util.TagList = tagList;
		try {
			writeListToFile(tagList,getCacheFileName(Tag.KIND, "en"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<?> parseJSONObject(JSONObject jsonObject, String objectName)
			throws JSONException {
		return null;
	}
}
