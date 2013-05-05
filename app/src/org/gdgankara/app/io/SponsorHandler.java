package org.gdgankara.app.io;

import java.util.ArrayList;

import org.gdgankara.app.model.Sponsor;
import org.gdgankara.app.utils.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class SponsorHandler extends BaseHandler {
	public static final String TAG = SponsorHandler.class.getSimpleName();

	private static final String CACHE_FILE_SPONSOR = "SponsorsJSON";
	private static final String BASE_URL = "http://add-2013.appspot.com/api/sponsors/";

	private Context context;
	private String lang;

	public SponsorHandler(Context context) {
		super(context);
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Sponsor> getSponsorList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<Sponsor> sponsorList = new ArrayList<Sponsor>();
		try {
			sponsorList = (ArrayList<Sponsor>) readCacheFile(getCacheFileName(lang));
			if (sponsorList == null) {
				jsonObject = doGet(BASE_URL + lang);
				sponsorList = parseJSONObject(jsonObject, "sponsors");
				writeListToFile(sponsorList, getCacheFileName(lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return sponsorList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Sponsor> updateSponsorList(String lang) {
		this.lang = lang;
		JSONObject jsonObject;
		ArrayList<Sponsor> sponsorList = new ArrayList<Sponsor>();
		try {
			jsonObject = doGet(BASE_URL + lang);
			boolean isVersionUpdated = Util.isVersionUpdated(context,
					jsonObject);
			if (isVersionUpdated) {
				sponsorList = parseJSONObject(jsonObject, "sponsors");
				writeListToFile(sponsorList, getCacheFileName(lang));
			} else {
				sponsorList = (ArrayList<Sponsor>) readCacheFile(getCacheFileName(lang));
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return sponsorList;
	}

	@Override
	public ArrayList<Sponsor> parseJSONObject(JSONObject jsonObject,
			String objectName) throws JSONException {
		JSONArray sponsorArray;
		ArrayList<Sponsor> sponsorList = new ArrayList<Sponsor>();
		try {
			sponsorArray = jsonObject.getJSONArray(objectName);
			int length = sponsorArray.length();
			Sponsor sponsor;
			for (int i = 0; i < length; i++) {
				JSONObject sponsorObject = sponsorArray.getJSONObject(i);
				sponsor = new Sponsor();
//				sponsor.setDescription(sponsorObject.getString("description"));
				sponsor.setId(sponsorObject.getLong("id"));
				sponsor.setLogo(sponsorObject.getString("image"));
//				sponsor.setName(sponsorObject.getString("name"));
				sponsor.setLink(sponsorObject.getString("link"));

				if (sponsorObject.getString("lang").equals(Sponsor.LANG_EN)) {
//					sponsor.setLang(Sponsor.LANG_EN);
				}else {
//					sponsor.setLang(Sponsor.LANG_TR);
				}
				sponsorList.add(sponsor);
			}
		} catch (Exception e) {
			Log.e(TAG, "Error: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return sponsorList;
	}

	private String getCacheFileName(String lang) {
		return CACHE_FILE_SPONSOR + "_" + lang;
	}

}
